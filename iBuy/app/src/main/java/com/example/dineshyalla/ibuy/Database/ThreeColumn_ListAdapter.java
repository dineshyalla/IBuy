package com.example.dineshyalla.ibuy.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dineshyalla.ibuy.R;
import com.example.dineshyalla.ibuy.model.Product;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<Product> {

    private LayoutInflater mInflater;
    private ArrayList<Product> products;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Product> products) {
        super(context, textViewResourceId, products);
        this.products = products;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Product product = products.get(position);

        if (product != null) {
            TextView firstName = (TextView) convertView.findViewById(R.id.textFirstName);
            TextView lastName = (TextView) convertView.findViewById(R.id.textLastName);
            TextView favFood = (TextView) convertView.findViewById(R.id.textFavFood);
            if (firstName != null) {
                firstName.setText(product.getFirstName());
            }
            if (lastName != null) {
                lastName.setText((product.getLastName()));
            }
            if (favFood != null) {
                favFood.setText((product.getFavFood()));
            }
        }

        return convertView;
    }
}

