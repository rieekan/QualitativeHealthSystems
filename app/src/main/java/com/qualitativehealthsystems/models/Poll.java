package com.qualitativehealthsystems.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Poll")
public class Poll extends Model {
    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    // This is a regular field
    @Column(name = "patient_id")
    public String patient_id;
    // This is a regular field
    @Column(name = "mood_id")
    public String mood_id;

    // Make sure to have a default constructor for every ActiveAndroid model
    public Poll(){
        super();
    }

    public Poll(int remoteId, String patient_id, String mood_id){
        super();
        this.remoteId = remoteId;
        this.patient_id = patient_id;
        this.mood_id = mood_id;
    }

    public Poll(String patient_id, String mood_id){
        super();
        this.patient_id = patient_id;
        this.mood_id = mood_id;
    }

    public static List<Poll> getAll() {
        // This is how you execute a query
        return new Select()
                .from(Poll.class)
//                .where("Category = ?", category.getId())
//                .orderBy("Name ASC")
                .execute();
    }

}
