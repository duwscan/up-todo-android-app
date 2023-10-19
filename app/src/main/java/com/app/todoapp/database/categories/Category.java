package com.app.todoapp.database.categories;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Category {
    @PrimaryKey
    private int uid;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "icon")
    private String icon;

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
