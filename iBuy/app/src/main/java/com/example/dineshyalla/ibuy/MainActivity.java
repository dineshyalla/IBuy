package com.example.dineshyalla.ibuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dineshyalla.ibuy.UI.BarcodeFetch;
import com.example.dineshyalla.ibuy.UI.Dashboard;
import com.example.dineshyalla.ibuy.model.User;
import com.example.dineshyalla.ibuy.model.UserLogin;
import com.example.dineshyalla.ibuy.service.UserClient;
import com.example.dineshyalla.ibuy.service.UserLoginClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText userName, password;
    TextView textView2;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = (TextView)findViewById(R.id.textView2);
        userName = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        login = (TextView)findViewById(R.id.textView);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserLogin userLogin = new UserLogin(
                        userName.getText().toString(),
                        password.getText().toString()
                );
               // Toast.makeText(getApplicationContext(), "LOGGED IN", Toast.LENGTH_SHORT).show();
                setNetworkRequest(userLogin);

              // Call();

            }
        });


        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void Call(){
        Intent intent = new Intent( this, Dashboard.class);
        startActivity(intent);
    }

    private void setNetworkRequest(UserLogin userLogin){
        //create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ibuyapp-env.us-east-2.elasticbeanstalk.com/webapi/myresource/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserLoginClient client = retrofit.create(UserLoginClient.class);
        Call<UserLogin> call = client.loginAccount(userLogin);

        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, retrofit2.Response<UserLogin> response) {
                Toast.makeText(getApplicationContext(), "" + response.body().getResult(), Toast.LENGTH_SHORT).show();

                String res = response.body().getResult();
                if(res.equals("ValidUser")){
                    Call();
                }
                else{
                    Toast.makeText(getApplicationContext(), "AUTHENTICATION FAILED" + response.body().getResult(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure Response" + t, Toast.LENGTH_SHORT).show();
                Log.d("error is :",  ""+ t);
            }
        });
    }
}
