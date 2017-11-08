package com.incubasys.incubasystest.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.incubasys.incubasystest.R;
import com.incubasys.incubasystest.api.ApiService;
import com.incubasys.incubasystest.utils.InternetConnection;
import com.incubasys.incubasystest.api.RetrofitClient;
import com.incubasys.incubasystest.model.AuthResponse;
import com.incubasys.incubasystest.model.User;
import com.incubasys.incubasystest.utils.UserNotification;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mLogin;
    private TextView mPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = findViewById(R.id.input_login);
        mPassword = findViewById(R.id.input_password);
        Button mSignIn = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        mSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (validate()) {
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    if (InternetConnection.checkConnection(getApplicationContext()))
                        connect();
                    else {
                        UserNotification.showMessage(getApplicationContext(), "Bad Internet connection");
                        progressDialog.dismiss();
                    }
                }
                break;
        }
    }

    private void connect() {
        User user = new User(mLogin.getText().toString(), mPassword.getText().toString());

        ApiService api = RetrofitClient.getApiService();
        Call<AuthResponse> call = api.authorization(user);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Response<AuthResponse> response, Retrofit retrofit) {
                if (response.body().getSuccess()) {
                    Log.d("Response","Success");
                    User.setToken(response.body().getData().getToken());
                    System.out.println(response.body().getData().getToken());
//                    UserNotification.showMessage(getApplicationContext(), "OK");
                    Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                else UserNotification.showMessage(getApplicationContext(), response.body().getError().getMessage());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Response","Failure");
                UserNotification.showMessage(getApplicationContext(), "Timeout. Bad internet connection.");
                progressDialog.dismiss();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String email = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        if (email.isEmpty()) {
            mLogin.setError("enter a valid login");
            valid = false;
        } else {
            mLogin.setError(null);
        }

        if (password.isEmpty()) {
            mPassword.setError("enter a valid password");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }
}
