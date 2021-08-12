package com.team2363.lib.json;

import java.util.ArrayList;
import java.util.List;

@JSONSerializable
public class Person {
    private String name;
    private int age;
    private Address address;
    private List<Person> friends = new ArrayList<>();

    public Person(String name, int age, Address address, List<Person> friends) {
        this.setName(name);
        this.setAge(age);
        this.setAddress(address);
        this.setFriends(friends);
    }

    public Person() {
        this("", 0, new Address(), new ArrayList<Person>());
    }

    @SerializedJSONObjectElement(key= "friends")
    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    @SerializedJSONObjectElement(key= "address")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @SerializedJSONObjectElement(key= "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @SerializedJSONObjectElement(key= "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
