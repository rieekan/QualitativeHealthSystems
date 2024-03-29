package com.qualitativehealthsystems.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Category")
public class Category extends Model {
    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true)
    public long remoteId;
    // This is a regular field
    @Column(name = "Name")
    public String name;

    @Column(name = "Test")
    public String test;

    // Make sure to have a default constructor for every ActiveAndroid model
    public Category(){
        super();
    }


    // Used to return items from another table based on the foreign key
    public List<Item> items() {
        return getMany(Item.class, "Category");
    }
}