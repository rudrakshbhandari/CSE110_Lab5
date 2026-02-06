package edu.ucsd.spendingtracker.view.charts;

import edu.ucsd.spendingtracker.model.Category;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Map;

/**
 * Concrete implementation of IChartProvider that creates a bar chart.
 * Each bar is colored according to its category.
 */
public class BarChartProvider implements IChartProvider {
    
    @Override
    public String getDisplayName() {
        return "Bar Chart";
    }

    @Override
    public Node createChart(Map<Category, Double> totals) {
        // Create axes for the bar chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount Spent ($)");
        
        // Initialize the bar chart with the axes
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Spending by Category");
        
        // Create a series to hold all the data points
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        
        // Populate the series with data from the totals map
        for (Map.Entry<Category, Double> entry : totals.entrySet()) {
            Category category = entry.getKey();
            Double amount = entry.getValue();
            
            XYChart.Data<String, Number> data = new XYChart.Data<>(category.name(), amount);
            series.getData().add(data);
        }
        
        // Add the series to the chart
        chart.getData().add(series);
        
        // Apply color styling to each bar based on category color
        int index = 0;
        for (Map.Entry<Category, Double> entry : totals.entrySet()) {
            Category category = entry.getKey();
            XYChart.Data<String, Number> data = series.getData().get(index);
            
            // Apply the category's color to the bar
            Node bar = data.getNode();
            if (bar != null) {
                bar.setStyle("-fx-bar-fill: " + category.color + ";");
            }
            index++;
        }
        
        // Hide the legend to reduce clutter
        chart.setLegendVisible(false);
        
        return chart;
    }
}
