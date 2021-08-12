/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team2363.helixnavigator.document;

import java.util.regex.Pattern;

import com.team2363.helixnavigator.document.obstacle.HAbstractObstacle;
import com.team2363.helixnavigator.document.obstacle.HPolygonPoint;
import com.team2363.helixnavigator.document.obstacle.HPolygonObstacle;
import com.team2363.helixnavigator.document.waypoint.HAbstractWaypoint;
import com.team2363.lib.json.JSONSerializable;
import com.team2363.lib.json.SerializedJSONObjectElement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * This class models a helix path
 * 
 * @author Justin Babilino
 */
@JSONSerializable
public class HPath {

    public static final Pattern VALID_PATH_NAME = Pattern.compile("[a-z0-9 _\\-]+", Pattern.CASE_INSENSITIVE);
    public static final int MAX_PATH_NAME_LENGTH = 50;

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final ObservableList<HAbstractWaypoint> waypoints = FXCollections.<HAbstractWaypoint>observableArrayList();
    private final HSelectionModel<HAbstractWaypoint> waypointsSelectionModel;
    private final ObservableList<HAbstractObstacle> obstacles = FXCollections.<HAbstractObstacle>observableArrayList();
    private final HSelectionModel<HAbstractObstacle> obstaclesSelectionModel;
    private final ReadOnlyObjectWrapper<HSelectionModel<HPolygonPoint>> obstaclePointsSelectionModel = new ReadOnlyObjectWrapper<>(
            this, "obstaclePointsSelectionModel", null);
    private final DoubleProperty zoomScale = new SimpleDoubleProperty(this, "zoomScale", 1.0);
    private final DoubleProperty zoomXOffset = new SimpleDoubleProperty(this, "zoomXOffset", 0.0);
    private final DoubleProperty zoomYOffset = new SimpleDoubleProperty(this, "zoomYOffset", 0.0);

    public HPath() {
        waypointsSelectionModel = new HSelectionModel<>(waypoints);
        obstaclesSelectionModel = new HSelectionModel<>(obstacles);
        obstaclesSelectionModel.getSelectedItems().addListener((ListChangeListener.Change<? extends HAbstractObstacle> change) -> {
            if (obstaclesSelectionModel.getSelectedIndices().size() == 1 && obstaclesSelectionModel.getSelectedItems().get(0) instanceof HPolygonObstacle) {
                if (getObstaclePointsSelectionModel() != null) {
                    getObstaclePointsSelectionModel().clear();
                }
                setObstaclePointsSelectionModel(new HSelectionModel<HPolygonPoint>(((HPolygonObstacle) obstaclesSelectionModel.getSelectedItems().get(0)).getPoints()));
            }
        });
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final void setName(String value) {
        name.set(value);
    }

    @SerializedJSONObjectElement(key = "name")
    public final String getName() {
        return name.get();
    }

    @SerializedJSONObjectElement(key = "waypoints")
    public final ObservableList<HAbstractWaypoint> getWaypoints() {
        return waypoints;
    }

    public final HSelectionModel<HAbstractWaypoint> getWaypointsSelectionModel() {
        return waypointsSelectionModel;
    }

    @SerializedJSONObjectElement(key = "obstacles")
    public final ObservableList<HAbstractObstacle> getObstacles() {
        return obstacles;
    }

    public final HSelectionModel<HAbstractObstacle> getObstaclesSelectionModel() {
        return obstaclesSelectionModel;
    }

    private void setObstaclePointsSelectionModel(HSelectionModel<HPolygonPoint> value) {
        obstaclePointsSelectionModel.set(value);
    }

    public final HSelectionModel<HPolygonPoint> getObstaclePointsSelectionModel() {
        return obstaclePointsSelectionModel.get();
    }

    public final ReadOnlyObjectProperty<HSelectionModel<HPolygonPoint>> obstaclePointsSelectionModelProperty() {
        return obstaclePointsSelectionModel;
    }

    public final DoubleProperty zoomScaleProperty() {
        return zoomScale;
    }

    public final void setZoomScale(double value) {
        zoomScale.set(value);
    }

    public final double getZoomScale() {
        return zoomScale.get();
    }

    public final DoubleProperty zoomXOffsetProperty() {
        return zoomXOffset;
    }

    public final void setZoomXOffset(double value) {
        zoomXOffset.set(value);
    }

    public final double getZoomXOffset() {
        return zoomXOffset.get();
    }

    public final DoubleProperty zoomYOffsetProperty() {
        return zoomYOffset;
    }

    public final void setZoomYOffset(double value) {
        zoomYOffset.set(value);
    }

    public final double getZoomYOffset() {
        return zoomYOffset.get();
    }
}
