package com.app.todoapp.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.todoapp.R;
import com.app.todoapp.database.categories.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {

    private List<Category> listCategory;
    Context context;
    public CategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories,parent,false);
        context = parent.getContext();
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final Category category = listCategory.get(position);
        if(category == null){
            return;
        }
        holder.button.setImageResource(category.getUid());
        holder.name.setText(category.getName());
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

        private RelativeLayout layoutItem;
        private ImageView button;
        private TextView name;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            layoutItem =itemView.findViewById(R.id.categoryItem);
            button = itemView.findViewById(R.id.buttonCateHome);
            name = itemView.findViewById(R.id.cateName);
        }
    }
}
