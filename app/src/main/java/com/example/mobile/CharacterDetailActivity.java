package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile.helper.MenuMain;

public class CharacterDetailActivity extends AppCompatActivity {

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