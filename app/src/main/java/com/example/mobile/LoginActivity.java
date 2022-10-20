package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mobile.helper.MenuMain;
import com.example.mobile.helper.SP;
import com.example.mobile.helper.SharedPreferencesHelper;
import com.example.mobile.user.User;
import com.example.mobile.user.UserManager;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPassword;
    private CheckBox cbRememberUser;
    private TextView btnCreateUser;
    private Button btnLogin;
    private ImageButton ibChangePasswordVisibility;
    private boolean showPassword = false;
    private SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        cbRememberUser = findViewById(R.id.cbRememberUser);
        btnCreateUser = findViewById(R.id.btnCreateUser);
        btnLogin = findViewById(R.id.btnLogin);
        ibChangePasswordVisibility = findViewById(R.id.ibChangePasswordVisibility);
        MenuMain.Init(this, R.id.toolbarLogin);
        sharedPreferencesHelper = new SharedPreferencesHelper(getPreferences(MODE_PRIVATE));
        cbRememberUser.setChecked(sharedPreferencesHelper.getBoolean(SP.REMEMBER_USER));
        etUserName.setText(sharedPreferencesHelper.getString(SP.REMEMBER_USER_USERNAME));
        this.btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUserExistence(etUserName.toString(), etPassword.toString())){
                    if(cbRememberUser.isChecked()){
                        sharedPreferencesHelper.setBoolean(SP.REMEMBER_USER, true);
                        sharedPreferencesHelper.setString(SP.REMEMBER_USER_USERNAME, etUserName.getText().toString());
                    }else{
                        sharedPreferencesHelper.setBoolean(SP.REMEMBER_USER, false);
                        sharedPreferencesHelper.setString(SP.REMEMBER_USER_USERNAME, "");
                    }
                    Intent intent = new Intent(LoginActivity.this, MainWikiActivity.class);
                    startActivity(intent);
                } else {
                    etUserName.setText("");
                    etPassword.setText("");
                    ExtensionesKt.mensajeCorto(LoginActivity.this,"Usuario o Contraseña incorrecta");
                }
            }
        });
        ibChangePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showPassword){
                    showPassword = false;
                    etPassword.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ibChangePasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }else{
                    showPassword = true;
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    ibChangePasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_24);
                }
            }
        });
    }

    private boolean checkUserExistence(String username, String password) {
        boolean exists = false;
        if(isValid()){
            for(User user : getUsuarios()){
                if(user.getUsername().equals(etUserName.getText().toString()) && user.getPassword().equals(etPassword.getText().toString()))
                    exists = true;
            }
        }
        return exists;
    }

    private boolean isValid() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        return !userName.isEmpty() && !password.isEmpty();
    }

    private List<User> getUsuarios() {
        try {
            return UserManager.getInstance(this).getUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }
}