package edu.ucsd.spendingtracker.view.charts;

import edu.ucsd.spendingtracker.model.Category;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;

import java.util.Map;

/**
 * Concrete implementation of IChartProvider that creates a pie chart.
 * Each slice is colored according to its category and shows proportional spending.
 */
public class PieChartProvider implements IChartProvider {
    
    @Override
    public String getDisplayName() {
        return "Pie Chart";
    }

    @Override
    public Node createChart(Map<Category, Double> totals) {
        // Create a new pie chart
        PieChart chart = new PieChart();
        chart.setTitle("Spending by Category");
        
        // Populate the pie chart with data from the totals map
        for (Map.Entry<Category, Double> entry : totals.entrySet()) {
            Category category = entry.getKey();
            Double amount = entry.getValue();
            
            // Create a slice for each category
            PieChart.Data slice = new PieChart.Data(category.name(), amount);
            chart.getData().add(slice);
        }
        
        // Apply color styling to each slice based on category color
        for (int i = 0; i < chart.getData().size(); i++) {
            PieChart.Data slice = chart.getData().get(i);
            Category category = Category.valueOf(slice.getName());
            
            // Apply the category's color to the slice
            Node sliceNode = slice.getNode();
            if (sliceNode != null) {
                sliceNode.setStyle("-fx-pie-color: " + category.color + ";");
            }
        }
        
        // Hide the legend to reduce clutter
        chart.setLegendVisible(false);
        
        return chart;
    }
}
