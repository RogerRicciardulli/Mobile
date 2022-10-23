package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.mobile.helper.MenuMain;

public class MovieDetailActivity extends AppCompatActivity {
    private EditText etDirector;
    private EditText etProductor;
    private EditText etDuracion;
    private EditText etLanzamiento;
    private EditText etPuntuacion;
    private EditText etDescripcion;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MovieDetailActivity.this, MoviesActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MenuMain.Init(this, R.id.toolbarMovieDetail);
        etDirector = findViewById(R.id.etDirector);
        etProductor = findViewById(R.id.etProductor);
        etDuracion = findViewById(R.id.etDuracion);
        etLanzamiento = findViewById(R.id.etLanzamiento);
        etPuntuacion = findViewById(R.id.etPuntuacion);
        etDescripcion = findViewById(R.id.etDescripcion);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_inicio) {
            Intent intent = new Intent(MovieDetailActivity.this, MainWikiActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}