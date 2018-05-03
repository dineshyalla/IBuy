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

import com.example.dineshyalla.ibuy.Database.DatabaseHelper;
import com.example.dineshyalla.ibuy.R;
import com.example.dineshyalla.ibuy.model.Product;
import com.example.dineshyalla.ibuy.model.ProductClientClass;
import com.example.dineshyalla.ibuy.service.ProductClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarcodeFetch extends AppCompatActivity {

    public String barcode = "01020306";
    public String fName="";
    public String lName="";
    public String fFood="";
    EditText etFirstName,etLastName,etFavFood;
    Button btnAdd, btnCall;
    ImageView img;
    DatabaseHelper myDB;
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

        etFavFood = (EditText) findViewById(R.id.etFavFood);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnView = (Button) findViewById(R.id.btnView);
        btnCall = (Button) findViewById(R.id.btnFetch);
        img = (ImageView) findViewById(R.id.cartView);
        myDB = new DatabaseHelper(this);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(BarcodeFetch.this,"Fetch Toast",Toast.LENGTH_LONG).show();

                 setNetworkRequest();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BarcodeFetch.this,ViewListContents.class);
                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String fName = etFirstName.getText().toString();
               // String lName = etLastName.getText().toString();
               // String fFood = etFavFood.getText().toString();
                if(fName.length() != 0 && lName.length() != 0 && fFood.length() != 0){
                    AddData(fName,lName, fFood);
                    etFavFood.setText("");
                    etLastName.setText("");
                    etFirstName.setText("");
                }else{
                    Toast.makeText(BarcodeFetch.this,"You must put something in the text field!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setNetworkRequest() {

        //create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ibuyapp-env.us-east-2.elasticbeanstalk.com/webapi/myresource/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ProductClient client = retrofit.create(ProductClient.class);
        Call<ProductClientClass> call = client.getProduct(barcode);
        call.enqueue(new Callback<ProductClientClass>() {
            @Override
            public void onResponse(Call<ProductClientClass> call, Response<ProductClientClass> response) {
                Toast.makeText(getApplicationContext(), "Success" + " " + response.body().getProductName(), Toast.LENGTH_SHORT).show();
                fName = fName + response.body().getProductName();
                lName = lName + response.body().getWeight();
                fFood = fFood + response.body().getProductprice();
            }

            @Override
            public void onFailure(Call<ProductClientClass> call, Throwable t) {

            }
        });

    }


    public void AddData(String firstName,String lastName, String favFood ){
        boolean insertData = myDB.addData(firstName,lastName,favFood);

        if(insertData==true){
            Toast.makeText(BarcodeFetch.this,"Successfully Entered Data!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(BarcodeFetch.this,"Something went wrong :(.",Toast.LENGTH_LONG).show();
        }
    }
}
