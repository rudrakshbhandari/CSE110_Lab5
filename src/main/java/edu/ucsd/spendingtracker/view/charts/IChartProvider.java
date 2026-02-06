package edu.ucsd.spendingtracker.view.charts;

import edu.ucsd.spendingtracker.model.Category;
import javafx.scene.Node;

import java.util.Map;

/**
 * Interface defining the contract for chart providers.
 * Implements the Strategy Pattern to allow different chart types to be used interchangeably.
 */
public interface IChartProvider {
    /**
     * Creates a chart visualization from aggregated spending data.
     * 
     * @param totals A map of categories to their total spending amounts
     * @return A JavaFX Node representing the chart
     */
    Node createChart(Map<Category, Double> totals);

    /**
     * Returns the display name for this chart type.
     * 
     * @return The name to show in the UI
     */
    String getDisplayName();
}
