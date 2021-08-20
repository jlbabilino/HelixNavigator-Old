package com.team2363.lib.json.examples;

import com.team2363.helixnavigator.document.waypoint.HHardWaypoint;
import com.team2363.helixnavigator.document.waypoint.HSoftWaypoint;
import com.team2363.lib.json.DeserializedAbstractDeterminer;
import com.team2363.lib.json.DeserializedJSONObjectValue;
import com.team2363.lib.json.DeserializedJSONTarget;
import com.team2363.lib.json.DeserializedType;
import com.team2363.lib.json.JSONDeserializerException;
import com.team2363.lib.json.JSONEntry;
import com.team2363.lib.json.JSONSerializable;
import com.team2363.lib.json.SerializedJSONObjectValue;

@JSONSerializable
public abstract class Animal {

    private final int age;
    
    public Animal(int age) {
        this.age = age;
    }

    public abstract String getSpecies();

    @SerializedJSONObjectValue(key = "age")
    public int getAge() {
        return age;
    }

    @DeserializedAbstractDeterminer
    public static DeserializedType determiner(JSONEntry jsonEntry) throws JSONDeserializerException {
        if (jsonEntry != null && jsonEntry.isObject() && jsonEntry.containsKey("species") && jsonEntry.getKeyedEntry("species").isString()) {
            if (jsonEntry.getKeyedEntry("species").getString().equals("Dog")) {
                return new DeserializedType(Dog.class);
            } else if (jsonEntry.getKeyedEntry("species").getString().equals("Cat")) {
                return new DeserializedType(Cat.class);
            } else {
                throw new JSONDeserializerException("the key \"type\" does not contain a valid Animal species name");
            }
        } else {
            throw new JSONDeserializerException("Unable to select appropriate Animal for deserialization ");
        }
        
    }

    public String toString() {
        return getSpecies();
    }
}
