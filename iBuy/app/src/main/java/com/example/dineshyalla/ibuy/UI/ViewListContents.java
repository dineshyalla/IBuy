package com.example.dineshyalla.ibuy.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.example.dineshyalla.ibuy.Database.DatabaseHelper;
import com.example.dineshyalla.ibuy.Database.ThreeColumn_ListAdapter;
import com.example.dineshyalla.ibuy.QRCode;
import com.example.dineshyalla.ibuy.R;
import com.example.dineshyalla.ibuy.model.Order;
import com.example.dineshyalla.ibuy.model.Payment;
import com.example.dineshyalla.ibuy.model.Product;
import com.example.dineshyalla.ibuy.service.PaymentClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewListContents extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    public String cardHolderName = "three";
    public int CardNumber=2222222;
    Random rand = new Random();
    int n = rand.nextInt(5000) + 1;
    public String qrcode = Integer.toString(n);

    List<Order> list = new ArrayList<>();
public int price = 0;
    DatabaseHelper myDB;
    ArrayList<Product> userList;
    ListView listView;
    Product product;
    TextView textView;
    BillingProcessor bp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

//        String s = "Total: " + getIntent().getStringExtra("price") + "$";
        TextView total = (TextView)findViewById(R.id.totalView);


        bp = new BillingProcessor(this, null, null);

        textView =(TextView)findViewById(R.id.textView);


        myDB = new DatabaseHelper(this);
        userList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(ViewListContents.this,"The Database is empty  :(.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                product = new Product(data.getString(1),data.getString(2),data.getString(3));
                userList.add(i,product);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3));
                System.out.println(userList.get(i).getFirstName());
                price = price + Integer.parseInt(data.getString(4));
                i++;
                int a = Integer.parseInt(data.getString(2));
                int id = Integer.parseInt("2");
                //Order order = new Order(Integer.parseInt(data.getString(2)), data.getString(1),1);
                Order order = new Order(id, data.getString(1), 3);
                list.add(order);
            }
            String s = "Total Price: " + Integer.toString(price) + '$';
            total.setText(s);
            ThreeColumn_ListAdapter adapter =  new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, userList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(ViewListContents.this,"android.test.purchased");
//                Order order = new Order(
//                        3,
//                        "pencil",
//                        1
//                );
//                list.add(order);
                Payment payment = new Payment(
                        cardHolderName,
                        CardNumber,
                        price,
                        qrcode,
                        list
                );

                paymentPost(payment);
                Intent intent = new Intent(ViewListContents.this, QRCode.class);
                intent.putExtra("qrcode", qrcode);
                startActivity(intent);

            }
        });
    }

    private void paymentPost(Payment payment) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ibuyapp-env.us-east-2.elasticbeanstalk.com/webapi/myresource/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        PaymentClient client2 = retrofit.create(PaymentClient.class);
        Call<Payment> call = client2.createPayment(payment);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                Toast.makeText(getApplicationContext(), "Success: Qrcode is: " + qrcode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(this, "You've purchased something",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(this, "Something went wrong",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
