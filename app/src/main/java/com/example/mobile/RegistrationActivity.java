package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mobile.helper.MenuMain;
import com.example.mobile.user.User;
import com.example.mobile.user.UserManager;

public class RegistrationActivity extends AppCompatActivity {

    EditText etRegistrationUserName;
    EditText etRegistrationPassword;
    Button btnRegistrationCreateAccount;
    private ImageButton ibRegistrationChangePasswordVisibility;
    private boolean showPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etRegistrationUserName = findViewById(R.id.etRegistrationUserName);
        etRegistrationPassword = findViewById(R.id.etRegistrationPassword);
        btnRegistrationCreateAccount = findViewById(R.id.btnRegistrationCreateAccount);
        ibRegistrationChangePasswordVisibility = findViewById(R.id.ibRegistrationChangePasswordVisibility);
        MenuMain.Init(this, R.id.toolbarRegistration);

        btnRegistrationCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValid()){
                    Toast.makeText(RegistrationActivity.this, "Completar datos",Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                agregarUsuario();
                Intent intent = new Intent(RegistrationActivity.this, MainWikiActivity.class);
                intent.putExtra("USER_NAME", etRegistrationUserName.getText().toString());
                startActivity(intent);
            }
        });
        ibRegistrationChangePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showPassword){
                    showPassword = false;
                    etRegistrationPassword.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    ibRegistrationChangePasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }else{
                    showPassword = true;
                    etRegistrationPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    ibRegistrationChangePasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_24);
                }
            }
        });
    }

    private boolean isValid() {
        String username = etRegistrationUserName.getText().toString();
        String password = etRegistrationPassword.getText().toString();
        return !username.isEmpty() && !password.isEmpty();
    }

    private void agregarUsuario() {
        User user = new User();
        user.setUsername(etRegistrationUserName.getText().toString());
        user.setPassword(etRegistrationPassword.getText().toString());
        Log.i("Info", etRegistrationUserName.getText().toString() + " " + etRegistrationPassword.getText().toString());
        Log.i("Info", user.getUsername() + " " + user.getPassword());
        try {
            UserManager.getInstance(RegistrationActivity.this).registrarUsuario(user);
            etRegistrationUserName.setText("");
            etRegistrationPassword.setText("");
            Toast.makeText(RegistrationActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}