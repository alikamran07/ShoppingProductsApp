package com.example.myfinaltask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapterClass extends RecyclerView.Adapter<CategoryAdapterClass.ViewHolder> {

    ArrayList<String> category;
    LayoutInflater layoutInflater;
    Context context;
    CategoryCallBack categoryCallBack;

    public CategoryAdapterClass(Context context, ArrayList<String> category, CategoryCallBack categoryCallBack) {
        this.category = category;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.categoryCallBack = categoryCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.category, parent, false);
        ViewHolder viewHolder =new ViewHolder(view,categoryCallBack);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.category1.setText(category.get(position).toString());


    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView category1;
        CategoryCallBack categoryCallBack;
        public ViewHolder(@NonNull View itemView,CategoryCallBack categoryCallBack) {
            super(itemView);
            category1=itemView.findViewById(R.id.category);

            this.categoryCallBack = categoryCallBack;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String category1 = category.get(getAdapterPosition());
            categoryCallBack.CatergotyClick(category1);
        }
    }
    public interface CategoryCallBack{
        void CatergotyClick(String s);
    }
}
