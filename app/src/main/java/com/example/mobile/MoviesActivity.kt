package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.list.movie.MovieAdapter
import android.content.Intent
import android.content.res.Resources
import com.example.mobile.MainWikiActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.mobile.R
import com.example.mobile.helper.MenuMain
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.example.mobile.MoviesActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobile.MovieDetailActivity
import com.example.mobile.list.movie.Movie
import java.util.ArrayList

class MoviesActivity : AppCompatActivity() {
    private var rvMovieList: RecyclerView? = null
    private var adapter: MovieAdapter? = null
    private var count = 30

    //TODO: Cambiar el margen horizontal en pantallas mas grandes
    override fun onBackPressed() {
        val intent = Intent(this@MoviesActivity, MainWikiActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        MenuMain.Init(this, R.id.toolbarMovies)
        setupAdapter()
        val dropdown = findViewById<Spinner>(R.id.spinnerMovies)
        val items = arrayOf("5", "20", "50", "100", "250")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val item = adapterView.getItemAtPosition(i)
                count = item.toString().toInt()
                this@MoviesActivity.adapter!!.set(getPlaceHolders(count))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun setupAdapter() {
        rvMovieList = findViewById(R.id.rvMovieList)
        val numberOfColumns = getNumberOfColumnsByScreenSize(screenWidth)
        if (rvMovieList != null) {
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, numberOfColumns)
            rvMovieList!!.layoutManager = layoutManager
            adapter = MovieAdapter(getPlaceHolders(count)) {
                val intent = Intent(this@MoviesActivity, MovieDetailActivity::class.java)
                startActivity(intent)
                finish()
            }
            rvMovieList!!.adapter = adapter
        } else {
            Log.d("LOG", "RV MOVIE LIST IS NULL")
        }
    }

    private fun getNumberOfColumnsByScreenSize(screenWidth: Int): Int {
        Log.d("LOG", Integer.toString(screenWidth))
        return if (screenWidth < 500) {
            1
        } else {
            2
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun getPlaceHolders(count: Int): List<Movie> {
        val movies = ArrayList<Movie>()
        for (i in 0 until count) {
            val id = Integer.toString(i)
            movies.add(Movie(id, "PlaceHolder_$id", "", null))
        }
        return movies
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_inicio) {
            val intent = Intent(this@MoviesActivity, MainWikiActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val screenWidth: Int
            get() = Resources.getSystem().displayMetrics.widthPixels
    }
}