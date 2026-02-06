package edu.ucsd.spendingtracker.view;

import edu.ucsd.spendingtracker.view.charts.IChartProvider;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

public class SummaryView extends VBox {
    private Label totalLabel = new Label();
    private Button backButton = new Button("Back to Expenses");
    private ComboBox<IChartProvider> chartSelector = new ComboBox<>();
    private StackPane chartContainer = new StackPane();

    public SummaryView() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setStyle("-fx-background-color: #FFFFFF;");

        Label title = new Label("Spending Analysis");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        totalLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: #2E7D32;");

        // Configure the chart selector
        chartSelector.setPromptText("Select a chart type");
        chartSelector.setConverter(new StringConverter<IChartProvider>() {
            @Override
            public String toString(IChartProvider provider) {
                return provider != null ? provider.getDisplayName() : "";
            }

            @Override
            public IChartProvider fromString(String string) {
                return null; // Not needed for non-editable ComboBox
            }
        });

        // Configure the chart container
        chartContainer.setMinHeight(400);
        chartContainer.setStyle("-fx-border-color: #E0E0E0; -fx-border-width: 1;");

        this.getChildren().addAll(title, totalLabel, chartSelector, chartContainer, backButton);
    }

    public void setTotal(double total) {
        totalLabel.setText("$" + String.format("%.2f", total));
    }

    public Button getBackButton() {
        return backButton;
    }

    public ComboBox<IChartProvider> getChartSelector() {
        return chartSelector;
    }

    public void setChartDisplay(Node chartNode) {
        chartContainer.getChildren().clear();
        if (chartNode != null) {
            chartContainer.getChildren().add(chartNode);
        }
    }
}