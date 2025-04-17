// ChartPanel.java (UPDATED TO IMPLEMENT THE OBSERVER PATTERN)

package org.climatechange.agriculture;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartPanel extends JPanel implements DataObserver {
    private CADataModel dataModel;

    // initializing the chart panel with dataset model
    public ChartPanel(CADataModel dataModel) {
        this.dataModel = dataModel;
        dataModel.addObserver(this);
        setPreferredSize(new Dimension(400, 300));
        setBorder(BorderFactory.createTitledBorder("Crop Yield vs. Temperature"));
    }

    // observer pattern update method
    @Override
    public void update() {
        repaint();
    }

    // draws the chart
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<CAENTRY> data = dataModel.getFilteredData();

        if (data == null || data.isEmpty()) {
            g2.drawString("No data available", getWidth() / 2 - 40, getHeight() / 2);
            return;
        }

        // set up scaling factors for plotting
        double minTemp = data.stream().mapToDouble(CAENTRY::getAverageTemperature).min().orElse(0);
        double maxTemp = data.stream().mapToDouble(CAENTRY::getAverageTemperature).max().orElse(40);
        double minYield = data.stream().mapToDouble(CAENTRY::getCropYield).min().orElse(0);
        double maxYield = data.stream().mapToDouble(CAENTRY::getCropYield).max().orElse(10);

        double xScale = (getWidth() - 60) / (maxTemp - minTemp);
        double yScale = (getHeight() - 60) / (maxYield - minYield);

        // draw axes
        g2.drawLine(50, getHeight() - 30, 50, 30);
        g2.drawLine(50, getHeight() - 30, getWidth() - 30, getHeight() - 30);
        g2.drawString("Temperature (°C)", getWidth() / 2 - 30, getHeight() - 10);
        g2.drawString("Yield (tons/ha)", 5, getHeight() / 2);

        // plot data points
        g2.setColor(Color.BLUE);
        for (CAENTRY entry : data) {
            int x = (int) ((entry.getAverageTemperature() - minTemp) * xScale) + 50;
            int y = (int) ((maxYield - entry.getCropYield()) * yScale) + 30;
            g2.fillOval(x - 3, y - 3, 6, 6);
        }
    }
}