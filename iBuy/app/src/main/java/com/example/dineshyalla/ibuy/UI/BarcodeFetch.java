package com.example.dineshyalla.ibuy.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dineshyalla.ibuy.R;

import java.util.ArrayList;

public class BarcodeFetch extends AppCompatActivity {
    public ArrayList<String> listBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_fetch);

        Intent intent = getIntent();
        listBarcode = intent.getStringArrayListExtra("test");
        TextView textView = (TextView) findViewById(R.id.barcodeView);
        for (String str : listBarcode) {
            textView.setText(str);
            Log.d("msg", str);
        }
    }
}
