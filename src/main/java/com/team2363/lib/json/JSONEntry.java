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
 * <p>
 * This abstract class is the super class of every type of entry possible in a
 * JSON structure. Each entry class that contains sub-entries always uses this
 * type for those entries. In order to achieve full functionality from the
 * polymorphic nature of this system, all methods that retrieve information from
 * JSON entries must be inside this class and be overwritten by the entry
 * classes like the <code>IntegerJSONEntry</code> class.
 * </p>
 * <p>
 * Here's an example: This is an example JSON file that could be imported into
 * this system: <code>
 * <br>{
 * <br>&nbsp;&nbsp;&nbsp; "people": [{
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Justin",
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "age": 16
 * <br>&nbsp;&nbsp;&nbsp; }, {
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "name": "Joe",
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "age": 23
 * <br>&nbsp;&nbsp;&nbsp; }]
 * <br>}
 * </code>
 * </p>
 * <p>
 * Let's say that the root object of this has been imported into a variable
 * <code>entry</code>.
 * </p>
 * <p>
 * Retrieving the name of the first person:
 * <p>
 * <code>entry.getKeyedEntry("people").getArray()[0].getKeyedEntry("name").getString()</code>
 * </p>
 * </p>
 * <p>
 * Let's break this down:
 * </p>
 * <p>
 * <code>entry.getKeyedEntry("people")</code>
 * </p>
 * <p>
 * This part grabs the keyed entry called "people" in the main object. It
 * returns an <code>ArrayJSONEntry</code> runtime type through a
 * <code>JSONEntry</code> type. This is why the methods of
 * <code>NamedJSONEntry</code> must also be in <code>JSONEntry</code>. If the
 * incorrect method is called then the default method will be executed. It
 * throws a <code>JSONEntryException</code> to indicate that the wrong method
 * was called or the entry does not exist in that location.
 * </p>
 * <p>
 * <code>.getArray()[0]</code>
 * </p>
 * <p>
 * This part grabs the array of <code>JSONEntry</code>, and then the first item
 * located inside the named entry retrieved in the last part.
 * </p>
 * <p>
 * <code>.getKeyedEntry("name").getString()</code>
 * </p>
 * <p>
 * This gets the string located inside the keyed entry called "name." This
 * returns the string "Justin".
 * </p>
 *
 * @author Justin Babilino
 */
public abstract class JSONEntry {

    public static enum Type {
        OBJECT, ARRAY, BOOLEAN, NUMBER, STRING, NULL
    }

    /**
     * Attempts to retrieve a JSON object from this <code>JSONEntry</code>, but only
     * succeeds if the entry is of type <code>ObjectEntryJSON</code>.
     *
     * @param key the <code>String</code> key used to retrieve the value
     * @return the <code>JSONEntry</code> associated with the key provided
     * @throws JSONEntryException       if the requested entry is unavailable
     * @throws JSONKeyNotFoundException if the key does not exist in the object
     */
    public JSONEntry getKeyedEntry(String key) throws JSONEntryException, JSONKeyNotFoundException {
        throw new JSONEntryException("This JSON entry is not an object.");
    }

    /**
     * Checks if a string key is availible in this JSON object, but only succeeds if
     * the entry is of type <code>JSONObject</code>.
     * 
     * @param key the key to check for
     * @return <code>true</code> if and only if the key exists in this object
     * @throws JSONEntryException if this JSON entry is not an object
     */
    public boolean containsKey(String key) throws JSONEntryException {
        throw new JSONEntryException("This JSON entry is not an object.");

    }

    /**
     * Attempts to retrieve an array from this <code>JSONEntry</code>, but only
     * succeeds if the entry is of type <code>ArrayJSONEntry</code>.
     *
     * @return an array containing the entries inside the JSON array
     * @throws JSONEntryException if the requested entry is unavailable
     */
    public JSONEntry[] getArray() throws JSONEntryException {
        throw new JSONEntryException("This JSON entry is not an array.");
    }

    /**
     * Attempts to retrieve a <code>boolean</code> value from this
     * <code>JSONEntry</code>, but only succeeds if the entry is of type
     * <code>BooleanJSONEntry</code>.
     *
     * @return the <code>boolean</code> value wrapped in this entry
     * @throws JSONEntryException if the requested entry is unavailable
     */
    public boolean getBoolean() throws JSONEntryException {
        throw new JSONEntryException("This JSON entry is not a boolean.");
    }

    /**
     * Attempts to retrieve an <code>Number</code> value from this
     * <code>JSONEntry</code>, but only succeeds if the entry is of type
     * <code>NumberJSONEntry</code>.
     *
     * @return the <code>Number</code> value wrapped in this entry
     * @throws JSONEntryException if the requested entry is unavailable
     */
    public Number getNumber() throws JSONEntryException {
        throw new JSONEntryException("This JSON entry is not a number.");
    }

    /**
     * Attempts to retrieve a <code>String</code> value from this
     * <code>JSONEntry</code>, but only succeeds if the entry is of type
     * <code>StringJSONEntry</code>.
     *
     * @return the <code>String</code> value wrapped in this entry
     * @throws JSONEntryException if the requested entry is unavailable
     */
    public String getString() throws JSONEntryException {
        throw new JSONEntryException("This JSON entry is not a string.");
    }

    /**
     * Returns <code>true</code> if this entry is an object, <code>false</code>
     * otherwise.
     *
     * @return type check as <code>boolean</code>
     */
    public boolean isObject() {
        return false;
    }

    /**
     * Returns <code>true</code> if this entry is an array, <code>false</code>
     * otherwise.
     *
     * @return type check as <code>boolean</code>
     */
    public boolean isArray() {
        return false;
    }

    /**
     * Returns <code>true</code> if this entry is a boolean, <code>false</code>
     * otherwise.
     *
     * @return type check as <code>boolean</code>
     */
    public boolean isBoolean() {
        return this instanceof JSONBoolean;
    }

    /**
     * Returns <code>true</code> if this entry is a number, <code>false</code>
     * otherwise.
     *
     * @return type check as <code>boolean</code>
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * Returns <code>true</code> if this entry is a string, <code>false</code>
     * otherwise.
     *
     * @return type check as <code>boolean</code>
     */
    public boolean isString() {
        return false;
    }

    /**
     * Returns <code>true</code> if this entry is a null, <code>false</code>
     * otherwise.
     *
     * @return type check as <code>boolean</code>
     */
    public boolean isNull() {
        return false;
    }

    /**
     * Returns the type of entry this JSON entry is.
     * 
     * @return type of JSON entry
     */
    public abstract JSONEntry.Type getType();

    /**
     * Generates a <code>String</code> that represents this JSON entry as properly
     * spaced and indented JSON text that can be saved to a file.
     *
     * @param indentLevel the amount of tabs, usually four spaces to place before
     *                    the string as an indent.
     * @param format      formatting data used when generating text
     * @return a <code>String</code> representing this JSON entry.
     */
    public abstract String getJSONText(int indentLevel, JSONFormat format);

    /**
     * Returns a string representation of this <code>JSONEntry</code>, using the
     * default format options.
     *
     * @return this <code>JSONEntry</code> as a String
     */
    @Override
    public String toString() {
        return getJSONText(0, new JSONFormat());
    }
}