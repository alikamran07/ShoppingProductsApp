package com.example.myfinaltask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapterClass extends RecyclerView.Adapter<ProductAdapterClass.ViewHolder> {

    List<MyModelCLassProduct> listData;
    LayoutInflater layoutInflater;
    Context context;

    public ProductAdapterClass(Context context, List<MyModelCLassProduct> listData) {
        this.listData = listData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.productlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyModelCLassProduct data = listData.get(position);

        holder.proTitle.setText(data.getTitle());
        holder.proPrice.setText( "$"+data.getPrice());
        holder.proRate.setText(""+ data.getRate());
        holder.proCount.setText("("+data.getCount()+")");
        Picasso.with(context).load(data.getImage()).into(holder.proImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(context,SingleProductPage.class);

                intent.putExtra("putId",""+listData.get(position).getId());
                intent.putExtra("putTitle",listData.get(position).getTitle());
                intent.putExtra("putCategory",listData.get(position).getCategory());
                intent.putExtra("putDescription",listData.get(position).getDescription());
                intent.putExtra("putPrice",""+listData.get(position).getPrice());
                intent.putExtra("putImage",listData.get(position).getImage());
                intent.putExtra("putRate",""+listData.get(position).getRate());
                intent.putExtra("putCount",""+listData.get(position).getCount());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(intent);
            }
        });
    }
    public void filterList(List<MyModelCLassProduct> filteredList) {
        listData = filteredList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView proTitle, proPrice,proRate,proCount;
        ImageView proImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            proTitle = itemView.findViewById(R.id.prodTitle);
            proPrice = itemView.findViewById(R.id.prodPrice);
            proImage = itemView.findViewById(R.id.prodImage);
            proRate=itemView.findViewById(R.id.prodRate);
            proCount=itemView.findViewById(R.id.prodCount);

        }
    }

}

