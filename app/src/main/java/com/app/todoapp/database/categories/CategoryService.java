package com.app.todoapp.database.categories;

import android.content.Context;

import androidx.core.util.Consumer;

import com.app.todoapp.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryService {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private CategoryDAO categoryDAO;

    public CategoryService(Context context) {
        this.context = context;
        categoryDAO = AppDatabase.getInstance(this.context).categoryDAO();
    }

    public <T> void getAllCategory(Consumer<List<Category>> onSuccess, Consumer<Throwable> onError) {
        compositeDisposable.add(categoryDAO.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess::accept,
                        onError::accept));
    }

    public Disposable saveCategory(Category category, Action successTask, Consumer<Throwable> errorTask) {
        return categoryDAO.save(category).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(successTask, errorTask::accept);
        // must be dispose;
    }

    public Disposable saveCategory(Category category) {
        return categoryDAO.save(category).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe();
        // must be dispose;
    }

    public void destroy() {
        compositeDisposable.dispose();
    }
}
