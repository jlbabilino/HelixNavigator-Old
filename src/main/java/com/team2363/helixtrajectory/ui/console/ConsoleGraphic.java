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
package com.team2363.helixtrajectory.ui.console;

/**
 * This enum class allows for a selection of several graphics to display on the
 * console.
 * 
 * @author Justin Babilino
 */
public enum ConsoleGraphic {
    NONE(""),
    INFO("/icons/console/info.png"),
    WARNING("/icons/console/warning.png"),
    WORKING("/icons/console/working.png"),
    COMPLETE("/icons/console/complete.png"),
    ERROR("/icons/console/error.png");
    
    public String url;
    
    private ConsoleGraphic(String url) {
        this.url = url;
    }
}
