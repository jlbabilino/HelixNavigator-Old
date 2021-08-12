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
 * <p>
 * This class wraps a <code>null</code> value in a <code>JSONEntry</code>. You
 * may be thinking that this is ridiculous, like why not just use an actual
 * <code>null</code> instead. The reason is that since JSON object entries use
 * <code>Map</code>s, it's impossible to tell whether the key is missing in the
 * map or the key does exist and it's a <code>null</code> entry. There probably
 * is a way to make it throw an exception or check for the key's existence but I
 * think this is better because it retains more information that helps in both
 * parsing and reading of JSONs.
 * </p>
 * <p>
 * Regardless, this class is extremely simple and only contains a constructor.
 * </p>
 *
 * @author Justin Babilino
 */
public class JSONNull extends JSONEntry {

    /**
     * Constructs a <code>NullJSONEntry</code>
     */
    public JSONNull() {
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public JSONEntry.Type getType() {
        return JSONEntry.Type.NULL;
    }

    @Override
    public String getJSONText(int indentLevel, JSONFormat format) {
        return "null";
    }
}
