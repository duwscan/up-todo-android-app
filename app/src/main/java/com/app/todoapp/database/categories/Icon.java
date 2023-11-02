package com.app.todoapp.database.categories;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity
public class Icon {
    @PrimaryKey(autoGenerate = true)
    private int iid;

    public Icon(int iid) {
        this.iid = iid;
    }

    public int getIid() {
        return iid;
    }

    public void setUid(int iid) {
        this.iid = iid;
    }

}
