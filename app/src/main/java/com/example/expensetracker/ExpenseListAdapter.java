package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ListItemHolder> {

    private ArrayList<Expense> list;

    private Context context;
    private RecyclerViewClickListner listner;

    public ExpenseListAdapter(Context context, ArrayList<Expense> list,RecyclerViewClickListner listner) {
        this.context = context;
        this.list = list;
        this.listner=listner;
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_row, parent, false);
        return new ListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        Expense expense=list.get(position);
        holder.textViewExpense.setText(String.format("%.2f", expense.getExpense()));
        holder.textViewCategory.setText(expense.getCategory());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewExpense;
        private TextView textViewCategory;
        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewExpense = itemView.findViewById(R.id.textViewExpense);
            textViewCategory= itemView.findViewById(R.id.textViewCategory);
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
