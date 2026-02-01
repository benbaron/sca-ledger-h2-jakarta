package org.nonprofitbookkeeping.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SchedulesPanel implements AppPanel
{
    private final BorderPane root = new BorderPane();

    public SchedulesPanel()
    {
        root.setPadding(new Insets(8));

        Label title = new Label("Outstanding / Schedules");
        title.getStyleClass().add("panel-title");

        Button add = new Button("+ Add");
        add.setOnAction(e -> onNew());

        HBox actions = new HBox(8, add);
        VBox header = new VBox(6, title, actions, new Separator());

        root.setTop(header);
        root.setCenter(new Label("TODO: Implement Outstanding / Schedules content."));
    }

    @Override public String title() { return "Outstanding / Schedules"; }
    @Override public Node root() { return root; }

    @Override public void onNew() {
        // placeholder
    }
}
