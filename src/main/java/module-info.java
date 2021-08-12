module com.team2363.helixtrajectory {
    requires transitive javafx.controls;
    requires java.desktop;
    requires java.sql;
    
    exports com.team2363.helixnavigator.ui;
    exports com.team2363.helixnavigator;
    exports com.team2363.lib.base64;
    exports com.team2363.helixnavigator.ui.prompts;
    exports com.team2363.helixnavigator.ui.editor;
    exports com.team2363.helixnavigator.document.waypoint;
    exports com.team2363.helixnavigator.ui.document;
}