package com.team2363.helixnavigator.ui.metadata;

import com.team2363.helixnavigator.document.CurrentDocument;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MetadataPane extends VBox {

    private final Pane noDocumentPane = new StackPane(new Text("NO DOCUMENT LOADED"));
    private final Pane noPathPane = new StackPane(new Text("NO PATH LOADED"));


    private static enum Mode {
        NO_DOCUMENT, NO_PATH, MULTI_SELECTION, WAYPOINT_SELECTION, OBSTACLE_SELECTION
    }

    public MetadataPane() {
        setMode(Mode.NO_DOCUMENT);
        CurrentDocument.documentProperty().addListener((currentVal, oldVal, newVal) -> refreshDocument());

        setMinWidth(100);
    }
    private void setMode(Mode mode) {
        getChildren().clear();
        switch (mode) {
            case NO_DOCUMENT:
                getChildren().add(noDocumentPane);
                break;
            case NO_PATH:
                getChildren().add(noPathPane);
                break;
            case MULTI_SELECTION:
                break;
            case WAYPOINT_SELECTION:
                break;
            case OBSTACLE_SELECTION:
                break;  
            default:
                break;

        }
    }
    private void refreshDocument() {
        if (CurrentDocument.getDocument() == null) {
            setMode(Mode.NO_DOCUMENT);
        } else {
            setMode(Mode.NO_PATH);
            CurrentDocument.getDocument().selectedPathIndexProperty().addListener((currentVal, oldVal, newVal) -> {
                if (newVal.intValue() < 0) {
                    setMode(Mode.NO_PATH);
                } else {
                    
                }
            });
        }
    }
}