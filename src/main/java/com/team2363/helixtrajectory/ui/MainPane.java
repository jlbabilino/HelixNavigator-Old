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

import com.team2363.helixtrajectory.ui.console.ConsolePane;
import com.team2363.helixtrajectory.ui.toolbar.Toolbar;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This class contains the main <code>BorderPane</code>
 * of the scene.
 * 
 * @author Justin Babilino
 */
public class MainPane extends BorderPane {
    
    /**
     * The toolbar menu at the top of the scene.
     */
    private final MenuBar toolbar;
    
    /**
     * The console at the bottom of the scene.
     */
    private final Pane consolePane;
    
    /**
     * Creates the <code>BorderPane</code> with the
     * necessary UI elements.
     */
    public MainPane() {
        toolbar = new Toolbar();
        consolePane = new ConsolePane();
        setTop(toolbar);
        setBottom(consolePane);
    }
}
