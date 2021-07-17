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
package com.team2363.lib.json;

/**
 * This <code>Exception</code> is thrown when a user attempts to retrieve a
 * <code>JSONEntry</code> that is not available in the JSON. For example, this
 * would be thrown if a user attempted to retrieve a
 * <code>StringJSONEntry</code> from an <code>ObjectJSONEntry</code> when a
 * <code>DoubleJSONEntry</code> was in the entry.
 *
 * @author Justin Babilino
 */
public class JSONEntryException extends Exception {

    /**
     * Constructs a <code>JSONEntryException</code> with a message to be
     * communicated to the user.
     *
     * @param message the <code>String</code> message communicated to the user.
     */
    public JSONEntryException(String message) {
        super(message);
    }
}
