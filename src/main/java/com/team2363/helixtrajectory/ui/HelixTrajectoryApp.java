/*
 * Copyright (C) 2021 Triple Helix Robotics - FRC Team 2363
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
package com.team2363.helixtrajectory.ui;

import com.team2363.helixtrajectory.ui.console.Console;
import com.team2363.helixtrajectory.ui.console.ConsoleGraphic;
import com.team2363.helixtrajectory.ui.console.Log;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class launches the main window of the application. It also manages the
 * primary stage.
 *
 * @author Justin Babilino
 */
public class HelixTrajectoryApp extends Application {

    /**
     * Main stage of the application.
     */
    private Stage mainStage;

    /**
     * Main scene of the application.
     */
    private MainScene mainScene;

    /**
     * Main method that launches the program with arguments.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        mainStage.setTitle("Helix Trajectory!");
        mainStage.setHeight(600);
        mainStage.setWidth(800);
        mainStage.setOnCloseRequest(new WindowClosedDialog(mainStage));

        mainScene = new MainScene();
        mainStage.setScene(mainScene.getScene());
        Log.log("Helix Trajectory has successfully launched.");
        Console.out.set("Welcome to Helix Trajectory");
        Console.graphic.set(ConsoleGraphic.INFO);

        mainStage.show();
    }

    @Override
    public void stop() {
    }
}
