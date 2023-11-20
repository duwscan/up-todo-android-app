package com.app.todoapp.categories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {

    private List<Category> listCategory;
    private List<Category> listCategoryOld;
    private Categories categories;
    public CategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
        this.listCategoryOld = listCategory;
    }
    public CategoryAdapter(Categories categories, List<Category> listCategory){
        this.categories = categories;
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Category category = listCategory.get(position);
        if(category == null){
            return;
        }
        holder.button.setImageResource(category.getUid());
        holder.name.setText(category.getName());
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categories.list.remove(listCategory.get(position));
                categories.getData(Categories.list);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCategory != null){
            return listCategory.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    listCategory = listCategoryOld;
                }else{
                    List<Category> list = new ArrayList<>();
                    for(Category category :listCategoryOld){
                        if(category.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(category);
                        }
                    }
                    listCategory = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listCategory;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listCategory =(List<Category>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView button;
        private final TextView name;
        private final LinearLayout delButton;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonCateHome);
            name = itemView.findViewById(R.id.cateName);
            delButton = itemView.findViewById(R.id.categoryArea);
        }
    }
}
