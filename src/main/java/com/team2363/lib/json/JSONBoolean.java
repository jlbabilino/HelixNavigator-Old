/*
 * Copyright (C) 2021 Justin Babilino
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
 * This class wraps a <code>boolean</code> value in a <code>JSONEntry</code>.
 *
 * @author Justin Babilino
 */
public class JSONBoolean extends JSONEntry {

    /**
     * The <code>boolean</code> value in the entry.
     */
    private final boolean value;

    /**
     * Constructs a <code>BooleanJSONEntry</code> with a <code>boolean</code> value
     * to be stored in the entry.
     *
     * @param value the <code>boolean</code> value to be stored in this entry
     */
    public JSONBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getBoolean() throws JSONEntryException {
        return value;
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public JSONEntry.Type getType() {
        return JSONEntry.Type.BOOLEAN;
    }

    @Override
    public String getJSONText(int indentLevel, JSONFormat format) {
        return Boolean.toString(value);
    }
}
