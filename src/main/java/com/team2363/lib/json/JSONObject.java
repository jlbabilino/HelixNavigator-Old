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

import java.util.Iterator;
import java.util.Map;

/**
 * This class represents a JSON object type. It contains a
 * <code>JSONEntry</code> that contains key-value pairs. It implements a
 * <code>Map</code> to achieve this.
 * 
 * @author Justin Babilino
 */
public class JSONObject extends JSONEntry {

    /**
     * The <code>Map</code> that stores key-value pairs for JSON objects
     */
    private final Map<String, JSONEntry> map;

    /**
     * Constructs a <code>NamespaceJSONEntry</code> with a <code>Map</code>
     * containing <code>JSONEntry</code> objects paired with string keys.
     *
     * @param map key-value pairs of <code>String</code> and <code>JSONEntry</code>
     */
    public JSONObject(Map<String, JSONEntry> map) {
        this.map = map;
    }

    @Override
    public JSONEntry getKeyedEntry(String key) throws JSONKeyNotFoundException {
        JSONEntry entry = map.get(key);
        if (entry != null) {
            return entry;
        }
        throw new JSONKeyNotFoundException("Keyed entry not found in object.");
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public JSONEntry.Type getType() {
        return JSONEntry.Type.OBJECT;
    }

    @Override
    public String getJSONText(int indentLevel, JSONFormat format) {
        if (map.isEmpty()) {
            return "{}"; // if nothing in map just spit this out
        }

        StringBuilder indentBlock = new StringBuilder();
        for (int i = 0; i < format.getIndentSpaces().spaces; i++) {
            indentBlock.append(" ");
        }

        StringBuilder shortIndentBlock = new StringBuilder(); // this block is for the indent level that the object
                                                              // itself is on
        for (int i = 0; i < indentLevel; i++) {
            shortIndentBlock.append(indentBlock);
        }
        String longIndentBlock = shortIndentBlock.toString() + indentBlock.toString(); // this block is for the indent
                                                                                       // level that the key-value pairs
                                                                                       // are on

        StringBuilder str = new StringBuilder();
        if (format.getObjectBeginOnNewline().value && indentLevel != 0) { // if this is the root entry (indent level is
                                                                          // 0) then don't enter down no matter what
            str.append(System.lineSeparator()).append(shortIndentBlock); // if the object should begin on a new line,
                                                                         // add a newline and short indent
        }

        str.append("{"); // add the initial open brace {

        String strBetweenItems;
        if (format.getObjectNewlinePerItem().value) {
            strBetweenItems = System.lineSeparator() + longIndentBlock; // if there should be newlines, insert them and
                                                                        // indent
            str.append(strBetweenItems);
        } else {
            strBetweenItems = " ";
        }

        Iterator<Map.Entry<String, JSONEntry>> setIterator = map.entrySet().iterator();

        Map.Entry<String, JSONEntry> firstEntry = setIterator.next();
        str.append("\"").append(firstEntry.getKey()).append("\": ") // add the first key
                .append(firstEntry.getValue().getJSONText(indentLevel + 1, format)); // add the first value

        setIterator.forEachRemaining(entry -> { // I use a lambda to avoid having to assign the next item over and over
                                                // again
            str.append(",").append(strBetweenItems) // add comma and newline
                    .append("\"").append(entry.getKey()).append("\": ") // add the key and colon :
                    .append(entry.getValue().getJSONText(indentLevel + 1, format)); // add the value
        });

        if (format.getObjectNewlinePerItem().value) {
            str.append(System.lineSeparator()).append(shortIndentBlock);
        }

        str.append("}"); // add final newline and close brace }

        return str.toString();

    }
}
