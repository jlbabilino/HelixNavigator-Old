package com.team2363.lib.json;

@JSONSerializable
public class Address {
    private int streetNumber;
    private String streetName;
    private String city;
    private String state;
    private int zipCode;
    private String country;

    public Address(int streetNumber, String streetName, String city, String state, int zipCode, String country) {
        this.setStreetNumber(streetNumber);
        this.setStreetName(streetName);
        this.setCity(city);
        this.setState(state);
        this.setZipCode(zipCode);
        this.setCountry(country);
    }

    public Address() {
        this(0, "", "", "", 0, "");
    }

    @SerializedJSONObjectElement(key = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @SerializedJSONObjectElement(key = "zip")
    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @SerializedJSONObjectElement(key = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @SerializedJSONObjectElement(key = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @SerializedJSONObjectElement(key = "street_name")
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @SerializedJSONObjectElement(key = "street_number")
    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }
}
