package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ListItemHolder> {

    private CategoryActivity categoryActivity;
    private List<Category> list;
    private RecyclerViewClickListner listner;

    public CategoryAdapter(CategoryActivity categoryActivity, List<Category> list,RecyclerViewClickListner listner) {
        this.categoryActivity = categoryActivity;
        this.list = list;
        this.listner=listner;
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from (parent.getContext())
                .inflate(R.layout.category_row, parent, false);

        return new ListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        Category cat = list.get(position);

        holder.txtCategoryName.setText(cat.getCategoryName());
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtCategoryName;
        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.categoryNameEditText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (int) itemView.getTag();
            listner.onClick(v,getAdapterPosition());

        }
    }
    public interface RecyclerViewClickListner{
        void onClick(View v, int position);
    }
}
