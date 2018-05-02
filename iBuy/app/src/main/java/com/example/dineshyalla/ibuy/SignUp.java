package com.example.dineshyalla.ibuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dineshyalla.ibuy.model.User;
import com.example.dineshyalla.ibuy.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    TextView signup;
    EditText name, email, password, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signup = (TextView)findViewById(R.id.signUp);
        name = (EditText) findViewById(R.id.customerName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(
                        name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        phone.getText().toString()

                );
                Toast.makeText(getApplicationContext(), "Input Success", Toast.LENGTH_SHORT).show();
                setNetworkRequest(user);
               // Log.d("User obj is: ", ""+user.toString());
            }
        });

    }

    private void setNetworkRequest(User user){
        //create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ibuyapp-env.us-east-2.elasticbeanstalk.com/webapi/myresource/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<User> call = client.createAccount(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                Log.d("Success Response is:", ""+ response.body().getClass());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
