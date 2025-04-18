# Fitzgerald-Lab-4

I have chosen to implement an Observer Pattern as well a Strategy Pattern.

Advantages of Observer:
- eliminates repetitive update calls after filtering
- supports adding new UI components without modifying my original data model
- it will provide automatic updates to all UI components when the data changes

Advantages of Strategy:
- improves code testability
- makes it easy to add new analysis types without modifying existing code
- encapsulates different analysis algorithms

Implementation Plan:

Observer Pattern 
New Interfaces:
- DataSubject interface
-- methods: addObserver(), removeObserver(), notifyObserver()
- Purpose is to be implemented by a data model class

Class Modifications:
- Create a new CADataModel class that implements DataSubject, holds that data collection
and filtered data, and notifies observers when data changes
Modify UI components to implement DataObserver:
- ChartPanel, table view, statistics display, etc.
- Each will update its view when modified
Update CA_GUI class: 
- Use the model instead of directly managing data
- Connect observers to the subject

Strategy Pattern
New Interface:
- AnalysisStrategy interface
-- method analyze(List<CAEntry> data)
- Purpose to define a common interface for all analysis algorithms

New Classes:
ACYS: calculates average crop yield
ATS: calculates average temperature
APS: calculates average precipiatation
TES: calculates total extreme weather events
HYCS: finds the country with the highest yield
Class Modification:
- Update CAParser by adding a field to hold the current strategy, add methods to set
strategy and execute analysis, and refactor the existing methods to use the strategy

Integration
- 1st implement the strategy pattern for analysis methods
- Implement observer pattern for UI components
- Update the filtering mechanisms to use the observer patterns
- Test that it updates properly