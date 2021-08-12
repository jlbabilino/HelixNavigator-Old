package com.team2363.helixnavigator.document;

import com.team2363.lib.json.JSONSerializable;
import com.team2363.lib.json.SerializedJSONObjectElement;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@JSONSerializable
public abstract class HAbstractPathElement {
    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false);

    protected HAbstractPathElement() {
    }

    public abstract void translateRelativeX(double x);

    public abstract void translateRelativeY(double y);

    public final BooleanProperty selectedProperty() {
        return selected;
    }

    public final void setSelected(boolean value) {
        selected.set(value);
    }

    @SerializedJSONObjectElement(key = "selected")
    public final boolean getSelected() {
        return selected.get();
    }
}
