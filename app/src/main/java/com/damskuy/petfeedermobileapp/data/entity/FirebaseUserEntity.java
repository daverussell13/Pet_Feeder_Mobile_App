package com.damskuy.petfeedermobileapp.data.entity;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class FirebaseUserEntity {

    private String name;

    public FirebaseUserEntity() {}

    public FirebaseUserEntity(String name) {
        this.name = name;
    }

    @PropertyName("name")
    public String getName() { return name; }

    @PropertyName("name")
    public void setName(String name) { this.name = name; }
}