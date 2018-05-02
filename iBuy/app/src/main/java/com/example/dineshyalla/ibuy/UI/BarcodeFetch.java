package com.example.dineshyalla.ibuy.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dineshyalla.ibuy.Database.DatabaseHelper;
import com.example.dineshyalla.ibuy.R;

import java.util.ArrayList;

public class BarcodeFetch extends AppCompatActivity {

    EditText etFirstName,etLastName,etFavFood;
    Button btnAdd,btnView;
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
        btnView = (Button) findViewById(R.id.btnView);
        myDB = new DatabaseHelper(this);

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
                String fName = etFirstName.getText().toString();
                String lName = etLastName.getText().toString();
                String fFood = etFavFood.getText().toString();
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

    public void AddData(String firstName,String lastName, String favFood ){
        boolean insertData = myDB.addData(firstName,lastName,favFood);

        if(insertData==true){
            Toast.makeText(BarcodeFetch.this,"Successfully Entered Data!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(BarcodeFetch.this,"Something went wrong :(.",Toast.LENGTH_LONG).show();
        }
    }
}
