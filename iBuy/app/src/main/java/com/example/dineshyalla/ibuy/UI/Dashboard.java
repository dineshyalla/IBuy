package com.example.dineshyalla.ibuy.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dineshyalla.ibuy.R;
import com.example.dineshyalla.ibuy.model.Aisle;
import com.example.dineshyalla.ibuy.model.ProductClientClass;
import com.example.dineshyalla.ibuy.service.AisleClient;
import com.example.dineshyalla.ibuy.service.ProductClient;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    TextView txtScan;
    TextView txtView;
    EditText editText3;
    String productName="";
   // String productName = "book";
    private ZXingScannerView zXingScannerView;
    public List<String> list =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txtScan = (TextView)findViewById(R.id.txtScan);
        ImageView img = (ImageView)findViewById(R.id.cartView);
        //Button btnAisle = (Button) findViewById(R.id.btnAisle);
        txtView = (TextView) findViewById(R.id.textView3);
        TextView search = (TextView) findViewById(R.id.txtSearch);
        editText3 =(EditText)findViewById(R.id.editText3);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pro = editText3.getText().toString();
                productName=productName+pro;
                //Toast.makeText(getApplicationContext(), "Aisle No: " + pro, Toast.LENGTH_SHORT).show();
                Log.d("Pro is: ", "" + pro);
                findAisle();
            }
        });

//        btnAisle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                findAisle();
//            }
//        });

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

    private void findAisle(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ibuyapp-env.us-east-2.elasticbeanstalk.com/webapi/myresource/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AisleClient client = retrofit.create(AisleClient.class);
        Call<Aisle> call = client.getAisle(productName);
        call.enqueue(new Callback<Aisle>() {
            @Override
            public void onResponse(Call<Aisle> call, Response<Aisle> response) {
                Toast.makeText(getApplicationContext(), "Aisle No. is: " + response.body().getAisleno(), Toast.LENGTH_SHORT).show();
                String aisle = "Aisle No: " + response.body().getAisleno();
                txtView.setText(aisle);

            }

            @Override
            public void onFailure(Call<Aisle> call, Throwable t) {

            }
        });

    }
}
