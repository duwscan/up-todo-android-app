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

import java.net.URISyntaxException;
import java.util.List;

public class CreateCategoryAdapter extends RecyclerView.Adapter<CreateCategoryAdapter.CreateCategoryViewHolder> implements Filterable {
    private List<Category> listCategory;
    Context context;
    private IClickItemCategoryListener iClickItemCategoryListener;
    public CreateCategoryAdapter(List<Category> listCategory, IClickItemCategoryListener listener) {
        this.listCategory = listCategory;
        this.iClickItemCategoryListener = listener;
    }

    @NonNull
    @Override
    public CreateCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_categories,parent,false);
        return new CreateCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCategoryViewHolder holder, int position) {
        final Category category = listCategory.get(position);
        if(category == null){
            return;
        }
        holder.button.setImageResource(category.getUid());
        holder.name.setText(category.getName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemCategoryListener.OnClickItemCategory(category);
            }
        });

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemCategoryListener.OnClickItemCategory(category);
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

    public class CreateCategoryViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layoutItem;
        private final ImageView button;
        private final TextView name;

        public CreateCategoryViewHolder(View itemView) {
            super(itemView);
            layoutItem =itemView.findViewById(R.id.createCategoryItem);
            button = itemView.findViewById(R.id.iconCre);
            name = itemView.findViewById(R.id.creName);
        }
    }
}
