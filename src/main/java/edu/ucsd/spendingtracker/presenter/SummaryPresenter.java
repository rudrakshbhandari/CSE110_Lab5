package edu.ucsd.spendingtracker.presenter;

import edu.ucsd.spendingtracker.model.Category;
import edu.ucsd.spendingtracker.model.Expense;
import edu.ucsd.spendingtracker.model.Model;
import edu.ucsd.spendingtracker.view.SummaryView;
import edu.ucsd.spendingtracker.view.charts.IChartProvider;
import javafx.scene.Node;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SummaryPresenter extends AbstractPresenter<SummaryView> {
    private Runnable onBack;
    private List<IChartProvider> chartProviders;

    public SummaryPresenter(Model model, SummaryView view, List<IChartProvider> chartProviders) {
        super(model, view);
        this.chartProviders = chartProviders;

        // Populate the chart selector with available providers
        this.view.getChartSelector().getItems().addAll(chartProviders);
        
        // Set default chart selection
        if (!chartProviders.isEmpty()) {
            this.view.getChartSelector().setValue(this.chartProviders.get(0));
        }

        // Set up event listeners
        this.view.getBackButton().setOnAction(e -> {
            if (onBack != null)
                onBack.run();
        });

        // Refresh the chart when the user selects a different chart type
        this.view.getChartSelector().setOnAction(e -> refresh());
    }

    public void setOnBack(Runnable action) {
        this.onBack = action;
    }

    public void refresh() {
        // Update the total spending display
        view.setTotal(model.getTotalSpending());

        // Aggregate spending data by category
        Map<Category, Double> totals = new TreeMap<>();
        
        // Initialize all categories with $0
        for (Category category : Category.values()) {
            totals.put(category, 0.0);
        }
        
        // Sum up expenses for each category
        List<Expense> expenses = model.getExpenses();
        for (Expense expense : expenses) {
            Category category = expense.getCategory();
            double currentTotal = totals.get(category);
            totals.put(category, currentTotal + expense.getAmount());
        }

        // Get the selected chart provider and create the chart
        IChartProvider selectedProvider = view.getChartSelector().getValue();
        if (selectedProvider != null) {
            Node chartNode = selectedProvider.createChart(totals);
            view.setChartDisplay(chartNode);
        }
    }

    @Override
    public String getViewTitle() {
        return "Summary";
    }
}

