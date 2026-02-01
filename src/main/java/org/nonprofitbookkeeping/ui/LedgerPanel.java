package org.nonprofitbookkeeping.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Ledger panel skeleton.
 *
 * - Txn grid (top)
 * - Split grid (bottom) starts with 2 rows, add more via + Add Line
 * - Double-left-click opens selected transaction (placeholder)
 * - Right-click selects and offers context actions (placeholder)
 */
public class LedgerPanel implements AppPanel
{
    private final SplitPane root = new SplitPane();
    private final TableView<Row> txnTable = new TableView<>();
    private final TableView<Row> splitTable = new TableView<>();

    public LedgerPanel()
    {
        buildTxnTable();
        buildSplitTable();

        VBox splitEditor = new VBox(6);
        splitEditor.setPadding(new Insets(8));
        Label lbl = new Label("Splits (minimal rows + Add)");
        lbl.getStyleClass().add("subheader");

        Button addLine = new Button("+ Add Line");
        Button removeLine = new Button("– Remove");
        ToolBar tb = new ToolBar(addLine, removeLine);

        addLine.setOnAction(e -> splitTable.getItems().add(new Row("", "", "", "")));
        removeLine.setOnAction(e -> {
            Row sel = splitTable.getSelectionModel().getSelectedItem();
            if (sel != null) splitTable.getItems().remove(sel);
        });

        VBox.setVgrow(splitTable, Priority.ALWAYS);
        splitEditor.getChildren().addAll(lbl, tb, splitTable);

        root.getItems().addAll(txnTable, splitEditor);
        root.setDividerPositions(0.62);

        txnTable.setRowFactory(tv -> {
            TableRow<Row> r = new TableRow<>();
            r.setOnMouseClicked(e -> {
                if (r.isEmpty()) return;

                if (e.getClickCount() == 2 && e.getButton() == javafx.scene.input.MouseButton.PRIMARY)
                {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Open transaction: " + r.getItem().a());
                    a.setHeaderText("Open Transaction");
                    a.showAndWait();
                }

                if (e.getButton() == javafx.scene.input.MouseButton.SECONDARY)
                {
                    ContextMenu cm = new ContextMenu();
                    MenuItem details = new MenuItem("Show Details (Inspector)");
                    details.setOnAction(ev -> {
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Inspector placeholder for: " + r.getItem().a());
                        a.setHeaderText("Inspector");
                        a.showAndWait();
                    });
                    cm.getItems().add(details);
                    r.setContextMenu(cm);
                }
            });
            return r;
        });

        txnTable.getItems().addAll(
            new Row("2026-01-05", "Payee A", "Memo A", "Cash/Bank"),
            new Row("2026-01-12", "Payee B", "Memo B", "Cash/Bank")
        );
    }

    private void buildTxnTable()
    {
        txnTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        txnTable.getColumns().add(col("Date", Row::a));
        txnTable.getColumns().add(col("Payee", Row::b));
        txnTable.getColumns().add(col("Memo", Row::c));
        txnTable.getColumns().add(col("Bank", Row::d));
    }

    private void buildSplitTable()
    {
        splitTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        splitTable.getColumns().add(col("Account", Row::a));
        splitTable.getColumns().add(col("Fund", Row::b));
        splitTable.getColumns().add(col("Amount", Row::c));
        splitTable.getColumns().add(col("Notes", Row::d));

        splitTable.getItems().addAll(new Row("", "", "", ""), new Row("", "", "", ""));
    }

    private TableColumn<Row, String> col(String name, java.util.function.Function<Row, String> getter)
    {
        TableColumn<Row, String> c = new TableColumn<>(name);
        c.setCellValueFactory(v -> new SimpleStringProperty(getter.apply(v.getValue())));
        return c;
    }

    @Override public String title() { return "Ledger"; }
    @Override public Node root() { return root; }

    public record Row(String a, String b, String c, String d) {}
}
