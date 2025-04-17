// CA_GUI.java (UPDATED FOR OBSERVER & STRATEGY PATTERNS)

package org.climatechange.agriculture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;

public class CA_GUI extends JFrame {
    private CADataModel dataModel;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextArea detailsText, statsText;
    private ChartPanel chartPanel;
    private JComboBox<String> cropFilter, countryFilter, yearFilter;
    private CAParser parser;

    // initializing the GUI with the given dataset
    public CA_GUI(List<CAENTRY> data) {
        this.dataModel = new CADataModel(data);
        this.parser = new CAParser(data);

        setTitle("Climate Change & Agriculture");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createFilterPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        updateUI();
        setLocationRelativeTo(null);
    }

    // creates the filter panel with dropdowns and a reset button
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();

        // get distinct values for dropdown filters
        String[] cropTypes = dataModel.getAllData().stream()
                .map(CAENTRY::getCropType)
                .distinct()
                .sorted()
                .toArray(String[]::new);

        String[] countries = dataModel.getAllData().stream()
                .map(CAENTRY::getCountry)
                .distinct()
                .sorted()
                .toArray(String[]::new);

        String[] years = dataModel.getAllData().stream()
                .map(e -> String.valueOf(e.getYear()))
                .distinct()
                .sorted()
                .toArray(String[]::new);

        cropFilter = createFilter(panel, "Crop Type", "All", cropTypes);
        countryFilter = createFilter(panel, "Country", "All", countries);
        yearFilter = createFilter(panel, "Year", "All", years);

        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> resetFilters());
        panel.add(reset);

        return panel;
    }

    // create dropdown filters dynamically
    private JComboBox<String> createFilter(JPanel panel, String label, String defaultItem, String[] items) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(defaultItem);
        Arrays.stream(items).forEach(comboBox::addItem);

        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) applyFilters();
        });

        panel.add(new JLabel(label));
        panel.add(comboBox);
        return comboBox;
    }

    // Creates the main panel containing the table, chart, and stats
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        chartPanel = new ChartPanel(dataModel);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanel, createTablePanel());
        split.setResizeWeight(0.3);

        panel.add(split, BorderLayout.CENTER);
        panel.add(createStatsPanel(), BorderLayout.EAST);
        panel.add(createDetailsPanel(), BorderLayout.SOUTH);

        return panel;
    }

    // sets up the table panel to display the dataset
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Year", "Country", "Region", "Crop", "Temp", "Precip", "CO2", "Yield", "Events", "Irrigation"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        dataTable = new JTable(tableModel);
        dataTable.setRowSorter(new TableRowSorter<>(tableModel));
        dataTable.getSelectionModel().addListSelectionListener(e -> updateDetails());

        // add as observer with anonymous inner class
        dataModel.addObserver(new DataObserver() {
            @Override
            public void update() {
                updateTable();
            }
        });

        panel.add(new JScrollPane(dataTable));
        return panel;
    }

    // creates the statistics panel
    private JPanel createStatsPanel() {
        statsText = new JTextArea(10, 20);
        statsText.setEditable(false);

        // add as observer with anonymous inner class
        dataModel.addObserver(new DataObserver() {
            @Override
            public void update() {
                updateStats();
            }
        });

        return wrapPanel(statsText, "Statistics");
    }

    // creates the details panel
    private JPanel createDetailsPanel() {
        detailsText = new JTextArea(5, 40);
        detailsText.setEditable(false);
        return wrapPanel(detailsText, "Details");
    }

    // wraps text area inside a titled panel
    private JPanel wrapPanel(JTextArea textArea, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(textArea));
        return panel;
    }

    // filters the dataset based on the selected criteria
    private void applyFilters() {
        String cropType = (String) cropFilter.getSelectedItem();
        String country = (String) countryFilter.getSelectedItem();
        String year = (String) yearFilter.getSelectedItem();

        dataModel.applyFilters(cropType, country, year);
        // notfiy observers
    }

    // checks if an entry matches the selected filter
    private boolean filterMatch(String value, JComboBox<String> filter) {
        String selected = (String) filter.getSelectedItem();
        return selected.equals("All") || selected.equals(value);
    }

    // updates all components after filtering
    private void updateUI() {
        updateTable();
        updateStats();
        // chart panel updates through the observer
    }

    // updates the table with filtered data
    private void updateTable() {
        tableModel.setRowCount(0);
        dataModel.getFilteredData().forEach(e ->
                tableModel.addRow(new Object[]{
                        e.getYear(), e.getCountry(), e.getRegion(), e.getCropType(),
                        e.getAverageTemperature(), e.getTotalPrecipitation(),
                        e.getCo2Emissions(), e.getCropYield(),
                        e.getExtremeWeatherEvents(), e.getIrrigation()
                })
        );
    }

    // updates the details panel when a row is selected
    private void updateDetails() {
        int row = dataTable.getSelectedRow();
        if (row >= 0) {
            int modelRow = dataTable.convertRowIndexToModel(row);
            detailsText.setText(dataModel.getFilteredData().get(modelRow).toString());
        }
    }

    // calculates and updates statistics based on filtered data
    private void updateStats() {
        // use strategy pattern
        parser = new CAParser(dataModel.getFilteredData());

        statsText.setText(String.format("Entries: %d\nAvg Yield: %.2f\nAvg Temp: %.2f°C\n" +
                        "Avg Precip: %.2fmm\nExtreme Events: %d",
                dataModel.getFilteredData().size(),
                parser.calculateAverageCropYield(),
                parser.calculateAverageTemperature(),
                parser.calculateAveragePrecipitation(),
                parser.calculateTotalExtremeWeatherEvents()));
    }

    // resets all filters to default
    private void resetFilters() {
        cropFilter.setSelectedItem("All");
        countryFilter.setSelectedItem("All");
        yearFilter.setSelectedItem("All");
        dataModel.resetFilters();
    }

    // main method to start
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new CA_GUI(CA_FileReader.readDataFromFile(
                        "./csv_file/climate_change_impact_on_agriculture_2024.csv"))
                        .setVisible(true)
        );
    }
}