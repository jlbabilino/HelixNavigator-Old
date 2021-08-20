package com.team2363.lib.json.examples;

import java.util.List;

import com.team2363.lib.json.DeserializedJSONObjectValue;
import com.team2363.lib.json.DeserializedJSONTarget;
import com.team2363.lib.json.JSONSerializable;
import com.team2363.lib.json.SerializedJSONObjectValue;

@JSONSerializable
public class PetSitter<A extends Animal> {

    private final String name;
    private final int age;
    private List<A> petsSitting;

    @DeserializedJSONTarget
    public PetSitter(@DeserializedJSONObjectValue(key = "name") String name, @DeserializedJSONObjectValue(key = "age") int age) {
        this.name = name;
        this.age = age;
    }
    @DeserializedJSONTarget
    public void setPetsSitting(@DeserializedJSONObjectValue(key = "pets_sitting") List<A> petsSitting) {
        this.petsSitting = petsSitting;
    }
    @SerializedJSONObjectValue(key = "pets_sitting")
    public List<A> getPetsSitting() {
        return petsSitting;
    }

    @SerializedJSONObjectValue(key = "name")
    public String getName() {
        return name;
    }
    
    @SerializedJSONObjectValue(key = "age")
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name + ", " + age + ", " + petsSitting.toString();
    }
}
