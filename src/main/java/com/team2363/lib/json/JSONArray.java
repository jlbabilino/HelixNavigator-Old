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
 * This <code>JSONEntry</code> holds an array of <code>JSONEntry</code> types.
 * It is equivalent to the square bracket [a, b, c,...] array structure in a
 * JSON. JSON arrays can hold strings, numbers, booleans, objects, and other
 * arrays. These arrays do not check for types, meaning that multiple types can
 * be in the same array simultaneously, unlike Java arrays which must have
 * values of the same types.
 *
 * @author Justin Babilino
 */
public class JSONArray extends JSONEntry {

    /**
     * The array of entries in the array
     */
    private final JSONEntry[] array;

    /**
     * Constructs an <code>ArrayJSONEntry</code> with an array of entries.
     *
     * @param array array of <code>JSONEntries</code>
     */
    public JSONArray(JSONEntry[] array) {
        this.array = array;
    }

    @Override
    public JSONEntry[] getArray() throws JSONEntryException {
        return array;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public JSONEntry.Type getType() {
        return JSONEntry.Type.ARRAY;
    }

    @Override
    public String getJSONText(int indentLevel, JSONFormat format) {
        if (array.length == 0) {
            return "[]"; // if nothing in array just spit this out
        }

        StringBuilder indentBlock = new StringBuilder();
        for (int i = 0; i < format.getIndentSpaces().spaces; i++) {
            indentBlock.append(" "); // build the indent block with the correct amount of spaces from the format
        }
        StringBuilder shortIndentBlock = new StringBuilder(); // this block is for the indent level that the array
                                                              // itself is on
        for (int i = 0; i < indentLevel; i++) {
            shortIndentBlock.append(indentBlock);
        }
        String longIndentBlock = shortIndentBlock.toString() + indentBlock.toString(); // this block is for the indent
                                                                                       // level that the array items are
                                                                                       // on

        StringBuilder str = new StringBuilder(); // the JSON text string we are building

        if (format.getArrayBeginOnNewline().value && indentLevel != 0) { // if this is the root entry (indent level is
                                                                         // 0) then don't enter down no matter what
            str.append(System.lineSeparator()).append(shortIndentBlock); // if the array should begin on a new line, add
                                                                         // a newline and short indent
        }
        str.append("["); // begin array

        String strBetweenItems; // this string goes after the comma and before the next item in arrays. It
                                // changes depending on the format
        if (format.getArrayNewlinePerItem().value) {
            strBetweenItems = System.lineSeparator() + longIndentBlock; // if there should be newlines between items,
                                                                        // modify strBetweenItms
            str.append(strBetweenItems);
        } else {
            strBetweenItems = " "; // we don't append anything here so there isn't a random space between the open
                                   // bracket and first item (like this: [ "test", 1])
        }

        str.append(array[0].getJSONText(indentLevel + 1, format)); // add the first item
        for (int i = 1; i < array.length; i++) {
            str.append(",").append(strBetweenItems).append(array[i].getJSONText(indentLevel + 1, format)); // add comma,
                                                                                                           // strBetweenItems,
                                                                                                           // and next
                                                                                                           // item
        }

        if (format.getArrayNewlinePerItem().value) {
            str.append(System.lineSeparator()).append(shortIndentBlock); // only place close bracket ] on newline if
                                                                         // each item has been on a new line
        }
        str.append("]"); // add final newline and close bracket ]

        return str.toString();
    }
}
