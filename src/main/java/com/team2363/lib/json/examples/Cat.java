package com.team2363.lib.json.examples;

import com.team2363.lib.json.DeserializedJSONObjectValue;
import com.team2363.lib.json.DeserializedJSONTarget;
import com.team2363.lib.json.SerializedJSONObjectValue;

public class Cat extends Animal {
    
    @DeserializedJSONTarget
    public Cat(@DeserializedJSONObjectValue(key = "age") int age) {
        super(age);
    }

    @Override
    @SerializedJSONObjectValue(key = "species")
    public String getSpecies() {
        return "Cat";
    }
}
