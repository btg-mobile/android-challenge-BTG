package com.example.android_challenge_btg.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Genre {

    @DatabaseField(id = true, canBeNull = false)
    private long id;
    @DatabaseField(canBeNull = false)
    private String name;

    public Genre() {
    }

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
