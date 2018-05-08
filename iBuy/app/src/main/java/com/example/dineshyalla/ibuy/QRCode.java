package com.example.dineshyalla.ibuy;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCode extends AppCompatActivity {



    ImageView img;
    String code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            code = code + extras.getString("qrcode");
        }

        TextView gen = (TextView) findViewById(R.id.generate);
        img = (ImageView)findViewById(R.id.qrCode);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    img.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), "QRcode: " + code, Toast.LENGTH_SHORT).show();

                } catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
