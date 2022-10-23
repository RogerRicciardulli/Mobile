package com.example.mobile;

import static com.example.mobile.ExtensionesKt.getImage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.helper.MenuMain;

public class CharacterDetailActivity extends AppCompatActivity {
    private EditText etTitleName;
    private EditText etName;
    private EditText etGender;
    private EditText etAge;
    private EditText etEyeColor;
    private EditText etHairColor;

    private String name;
    private String gender;
    private String age;
    private String eyeColor;
    private String hairColor;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CharacterDetailActivity.this, CharactersActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        MenuMain.Init(this, R.id.toolbarCharacterDetail);

        etTitleName = findViewById(R.id.etTitleName);
        etName = findViewById(R.id.etName);
        etGender = findViewById(R.id.etGender);
        etAge = findViewById(R.id.etAge);
        etEyeColor = findViewById(R.id.etEyeColor);
        etHairColor = findViewById(R.id.etHairColor);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        gender = intent.getStringExtra("gender");
        age = intent.getStringExtra("age");
        eyeColor = intent.getStringExtra("eyeColor");
        hairColor = intent.getStringExtra("hairColor");

        etTitleName.setText(name);
        etName.setText(name);
        etGender.setText(gender);
        etAge.setText(age);
        etEyeColor.setText(eyeColor);
        etHairColor.setText(hairColor);

        //int resID = getResources().getIdentifier(getImage(name), "drawable", getPackageName());
        //ivCharacterImage.setImageResource(resID);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_inicio) {
            Intent intent = new Intent(CharacterDetailActivity.this, MainWikiActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}