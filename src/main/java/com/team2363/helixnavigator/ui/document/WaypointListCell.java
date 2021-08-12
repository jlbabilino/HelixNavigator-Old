package com.team2363.helixnavigator.ui.document;

import com.team2363.helixnavigator.document.waypoint.HAbstractWaypoint;
import com.team2363.helixnavigator.document.waypoint.HSoftWaypoint;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class WaypointListCell {
    public static final Callback<ListView<HAbstractWaypoint>, ListCell<HAbstractWaypoint>> waypointCellFactory = new Callback<ListView<HAbstractWaypoint>, ListCell<HAbstractWaypoint>>() {
        @Override
        public ListCell<HAbstractWaypoint> call(ListView<HAbstractWaypoint> listView) {
            return new ListCell<HAbstractWaypoint>() {
                @Override
                protected void updateItem(HAbstractWaypoint item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setText("");
                    } else {
                        String waypointTypeString = item instanceof HSoftWaypoint ? "Soft Waypoint" : "Hard Waypoint";
                        String name = item.getName();
                        setText(waypointTypeString + " " + name);
                    }
                }
            };
        }
    };
}