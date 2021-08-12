package com.team2363.helixnavigator.document.obstacle;

import com.team2363.helixnavigator.document.HAbstractPathElement;
import com.team2363.lib.json.SerializedJSONObjectElement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HPolygonPoint extends HAbstractPathElement {
    private final DoubleProperty x = new SimpleDoubleProperty(this, "x", 0.0);
    private final DoubleProperty y = new SimpleDoubleProperty(this, "y", 0.0);

    public HPolygonPoint() {
    }

    @Override
    public void translateRelativeX(double x) {
        setX(getX() + x);
    }

    @Override
    public void translateRelativeY(double y) {
        setY(getY() + y);
    }

    public final DoubleProperty xProperty() {
        return x;
    }

    public final void setX(double value) {
        x.set(value);
    }

    @SerializedJSONObjectElement(key = "x")
    public final double getX() {
        return x.get();
    }

    public final DoubleProperty yProperty() {
        return y;
    }

    public final void setY(double value) {
        y.set(value);
    }

    @SerializedJSONObjectElement(key = "y")
    public final double getY() {
        return y.get();
    }

    public String toString() {
        return "x: " + getX() + " y: " + getY();
    }
}