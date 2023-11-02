package com.app.todoapp.categories;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.MainActivity;
import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;

import java.util.ArrayList;
import java.util.List;

public class CreateCategories extends AppCompatActivity {
    private RecyclerView rcvCreCate;
    private CreateCategoryAdapter createCategoryAdapter;

    List<Category> list = new ArrayList<>();
    int icon;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_categories);

//        Bundle bundle = getIntent().getExtras();
//        if(bundle == null){
//            return;
//        }

//        Category category = (Category) bundle.get("object_category");
//        icon = category.getUid();
//        name = category.getName();

        rcvCreCate = findViewById(R.id.createCategoryRecycler);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4);
        rcvCreCate.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvCreCate.addItemDecoration(itemDecoration);

        createCategoryAdapter = new CreateCategoryAdapter(getListCategories());

        rcvCreCate.setAdapter(createCategoryAdapter);

    }

    private void onClickOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tiêu đề của AlertDialog");  // Đặt tiêu đề của dialog
        builder.setMessage("Nội dung của AlertDialog");  // Đặt nội dung của dialog

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                Categories.list.add(new Category(icon, name));
                // Xử lý khi người dùng nhấn nút "Đồng ý"
                dialog.dismiss();  // Đóng dialog
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút "Hủy bỏ"
                dialog.dismiss();  // Đóng dialog
            }
        });

        AlertDialog dialog = builder.create();  // Tạo AlertDialog từ builder
        dialog.show();  // Hiển thị AlertDialog
    }

    private List<Category> getListCategories() {
        list.add(new Category(R.drawable.category_grocery, ""));
        list.add(new Category(R.drawable.category_sport, ""));
        list.add(new Category(R.drawable.category_design, ""));
        list.add(new Category(R.drawable.category_university, ""));
        list.add(new Category(R.drawable.category_social, ""));
        list.add(new Category(R.drawable.category_music, ""));
        list.add(new Category(R.drawable.category_health, ""));
        list.add(new Category(R.drawable.category_movie, ""));
        list.add(new Category(R.drawable.category_home, ""));

        return list;
    }
}
