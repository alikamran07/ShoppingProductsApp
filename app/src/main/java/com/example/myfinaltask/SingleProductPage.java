package com.example.myfinaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleProductPage extends AppCompatActivity {

    ImageView imageView;
    TextView id,title,category,description,price,rate,count;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_page);

        imageView=findViewById(R.id.putImage);
        id=findViewById(R.id.putId);
        title=findViewById(R.id.putTitle);
        category=findViewById(R.id.putCategory);
        description=findViewById(R.id.putDescription);
        price=findViewById(R.id.putPrice);
        rate=findViewById(R.id.putRate);
        count=findViewById(R.id.putCount);

        Picasso.with(context).load(getIntent().getStringExtra("putImage")).into(imageView);
        id.setText(getIntent().getStringExtra("putId"));
        title.setText(getIntent().getStringExtra("putTitle"));
        category.setText(getIntent().getStringExtra("putCategory"));
        description.setText(getIntent().getStringExtra("putDescription"));
        price.setText("$"+getIntent().getStringExtra("putPrice"));
        rate.setText(getIntent().getStringExtra("putRate"));
        count.setText("("+getIntent().getStringExtra("putCount")+")");


    }
}