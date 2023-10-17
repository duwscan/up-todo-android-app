package com.app.todoapp.database.categories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM Category")
    Flowable<List<Category>> getAll();

    @Query("SELECT* FROM Category where uid = :id")
    Category getById(int id);

    @Query("SELECT* FROM Category where name = :name")
    Flowable<Category> getByName(String name);


    // insert and update combine
    @Upsert
    Completable save(Category... categories);

    @Delete
    void delete(Category user);
}
