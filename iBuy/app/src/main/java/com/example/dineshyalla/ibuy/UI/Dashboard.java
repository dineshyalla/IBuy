package com.example.dineshyalla.ibuy.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dineshyalla.ibuy.R;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Dashboard extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    TextView txtScan;
    private ZXingScannerView zXingScannerView;
    public List<String> list =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txtScan = (TextView)findViewById(R.id.txtScan);
        ImageView img = (ImageView)findViewById(R.id.cartView);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,ViewListContents.class);
                startActivity(intent);
            }
        });

    }

    public void scan(View view) {
        zXingScannerView =new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(this, BarcodeFetch.class);
        String barcode = result.getText().toString();
        list.add(barcode);

        intent.putStringArrayListExtra("test", (ArrayList<String>) list);
        //intent.putExtra("test", barcode);
        //intent.putExtra(EXTRA_MESSAGE, list);
        startActivity(intent);
        zXingScannerView.resumeCameraPreview(this);
    }
}
