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
 * This class wraps a String value as a <code>JSONEntry</code>.
 * 
 * @author Justin Babilino
 */
public class JSONString extends JSONEntry {

    /**
     * The <code>String</code> value
     */
    private final String string;

    /**
     * Constructs a <code>StringJSONEntry</code> with a String value.
     * 
     * @param string the String value
     */
    public JSONString(String string) {
        if (string == null) {
            throw new IllegalArgumentException("null");
        }
        this.string = string;
    }

    @Override
    public String getString() throws JSONEntryException {
        return string;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public JSONEntry.Type getType() {
        return JSONEntry.Type.STRING;
    }

    @Override
    public String getJSONText(int indentLevel, JSONFormat format) {
        return "\"" + string + "\"";
    }
}