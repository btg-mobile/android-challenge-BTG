package com.androidchallengebtg.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.BaseActivity;
import com.androidchallengebtg.activities.movies.MoviesActivity;

public class LoginActivity extends BaseActivity {

    private EditText mInputLogin;
    private EditText mInputPassword;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputLogin = findViewById(R.id.input_login);
        mInputPassword = findViewById(R.id.input_password);

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        loginController = new LoginController(new LoginController.Listener() {
            @Override
            public void onSuccess() {
                goAhead();
            }

            @Override
            public void onError(String message) {
                hideProgressDialog();
                LoginActivity.this.showToast(message);
            }
        }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginController.loginWithPass(new LoginController.LoginWithPassListener() {
            @Override
            public void onFound(boolean b) {
                if(b){
                    showProgressDialog(LoginActivity.this, getString(R.string.authenticating),true);
                }
            }
        });
    }

    /*
    Valida os campos do formuário de login
     */
    private void performLogin () {

        final String username = mInputLogin.getText().toString().trim();
        final String password = mInputPassword.getText().toString().trim();

        /*
        Aborta se login ou senha estiverem vazios
         */
        if(username.length() <1 || password.length() <1){
            showToast(getString(R.string.empty_login_or_pass));
            return;
        }

        showProgressDialog(this, getString(R.string.authenticating),true);

        /*
        inicia o processo de autenticação
         */
        loginController.createRequestToken(username, password);

    }

    /*
    Segue para a tela principal do aplicativo para início da navegação
     */
    private void goAhead(){
        hideProgressDialog();
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
        finish();
    }


}
