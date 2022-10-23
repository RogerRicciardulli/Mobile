package com.example.mobile;

import static com.example.mobile.ExtensionesKt.getImage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mobile.helper.MenuMain;

public class MovieDetailActivity extends AppCompatActivity {
    private EditText etOriginalTitleRomanised;
    private EditText etOriginalTitle;
    private EditText etDirector;
    private EditText etProductor;
    private EditText etDuracion;
    private EditText etLanzamiento;
    private EditText etPuntuacion;
    private EditText etDescripcion;
    private ImageView ivMovieImage;


    private String originalTitleRomanised;
    private String originalTitle;
    private String directorName;
    private String productorName;
    private String releaseDate;
    private String runningTime;
    private String rtScore;
    private String description;

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
        etOriginalTitleRomanised = findViewById(R.id.etOriginalTitleRomanised);
        etOriginalTitle = findViewById(R.id.etOriginalTitle);
        etDirector = findViewById(R.id.etDirector);
        etProductor = findViewById(R.id.etProductor);
        etDuracion = findViewById(R.id.etDuracion);
        etLanzamiento = findViewById(R.id.etLanzamiento);
        etPuntuacion = findViewById(R.id.etPuntuacion);
        etDescripcion = findViewById(R.id.etDescripcion);
        ivMovieImage = findViewById(R.id.ivSelectedMovieImage);

        Intent intent = getIntent();
        originalTitleRomanised = intent.getStringExtra("originalTitleRomanised");
        originalTitle = intent.getStringExtra("originalTitle");
        directorName = intent.getStringExtra("directorName");
        productorName = intent.getStringExtra("productorName");
        releaseDate = intent.getStringExtra("releaseDate");
        runningTime = intent.getStringExtra("runningTime");
        rtScore = intent.getStringExtra("rtScore");
        description = intent.getStringExtra("description");

        etOriginalTitleRomanised.setText(originalTitleRomanised);
        etOriginalTitle.setText(originalTitle);
        etDirector.setText(directorName);
        etProductor.setText(productorName);
        etDuracion.setText(runningTime);
        etLanzamiento.setText(releaseDate);
        etPuntuacion.setText(rtScore);
        etDescripcion.setText(description);
        int resID = getResources().getIdentifier(getImage(originalTitle), "drawable", getPackageName());
        ivMovieImage.setImageResource(resID);
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