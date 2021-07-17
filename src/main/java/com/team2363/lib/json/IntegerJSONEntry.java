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
 * This class wraps an <code>int</code> value in a <code>JSONEntry</code>.
 *
 * @author Justin Babilino
 */
public class IntegerJSONEntry extends JSONEntry {

    /**
     * The <code>int</code> value in the entry
     */
    private final int number;

    /**
     * Constructs an <code>IntegerJSONEntry</code> with an <code>int</code>
     * value to be stored in the entry.
     *
     * @param number the <code>int</code> value to be stored in this entry
     */
    public IntegerJSONEntry(int number) {
        this.number = number;
    }

    @Override
    public int getInt() throws JSONEntryException {
        return number;
    }

    @Override
    public double getDouble() throws JSONEntryException {
        return (double) number;
    }

    @Override
    public String getJSONText(int indentLevel, JSONFormat format) {
        return Integer.toString(number);
    }
}
