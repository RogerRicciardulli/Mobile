package com.example.mobile

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.helper.MenuMain
import com.example.mobile.helper.SP
import com.example.mobile.helper.SharedPreferencesHelper
import com.example.mobile.list.movie.Movie
import com.example.mobile.list.movie.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import java.util.*

class MoviesActivity : AppCompatActivity() {
    private var rvMovieList: RecyclerView? = null
    private var adapter: MovieAdapter? = null
    private var count = 30

    private var apiResponse = listOf<Movie>()
    private var sharedPreferencesHelper: SharedPreferencesHelper? = null

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

        sharedPreferencesHelper = SharedPreferencesHelper(getPreferences(android.content.Context.MODE_PRIVATE))

        // Llamada a la API
        lifecycleScope.launch {
            runBlocking(Dispatchers.IO) {
                apiResponse = getFilms()
                setupAdapter()
            }
        }
        mensajeCorto("Cargando informacion...")
        Thread.sleep(3500)

        val dropdown = findViewById<Spinner>(R.id.spinnerMovies)
        val items = arrayOf("5", "15", "30")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        var movies: List<Movie>

        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                // La primera vez que se llama a la API se guardan los datos en SharedPreferences para utilizarlos sin consultarlos de nuevo
                uploadDataInSharedPreferences()
                val item = adapterView.getItemAtPosition(i)
                count = item.toString().toInt()
                movies = convertToMovie(count)
                this@MoviesActivity.adapter!!.set(movies)
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
            adapter = MovieAdapter(apiResponse) {
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

    private fun getFilms(): List<Movie> {
        var apiResponse = ArrayList<Movie>()
        val api = RetroFitClient.retrofit.create(MyAPI::class.java)
        val callGetPost = api.getFilms()
        callGetPost.enqueue(object : retrofit2.Callback<List<MovieResponse>>{
            override fun onResponse(call: Call<List<MovieResponse>>, response: Response<List<MovieResponse>>) {
                val apiRest = response.body()
                apiRest?.forEach {
                    val movie = Movie(it.id, it.title, "",null)
                    apiResponse.add(movie)
                }
                Log.d("REST", "Cantidad de peliculas: " + apiRest?.size.toString())
            }
            override fun onFailure(call: Call<List<MovieResponse>>, t: Throwable) {
                Log.e("REST", t.message ?:"")
            }
        })
        return apiResponse
    }

    private fun convertToMovie(count: Int): List<Movie> {
        val allFilms = sharedPreferencesHelper!!.getString(SP.MOVIES_WITHOUT_DETAIL)
        var filmName = StringBuilder()
        var beginning = 0
        var films = ArrayList<Movie>()
        for(counter in allFilms.indices){
            if(allFilms[counter] == '-' && films.count() <= count-1){
                filmName.append(allFilms.substring(beginning, counter-1))
                films.add(Movie("", filmName.toString(),"",null))
                beginning = counter+2
                filmName.setLength(0)
            }
        }
        return films;
    }

    private fun uploadDataInSharedPreferences(){
        val builder = StringBuilder()
        if(apiResponse.isNotEmpty() && sharedPreferencesHelper!!.getString(SP.MOVIES_WITHOUT_DETAIL)
                .isNotEmpty()) {
            for(counter in apiResponse.indices){
                builder.append(apiResponse[counter].title)
                if(counter != apiResponse.size-1){
                    builder.append(" - ")
                }
            }
            sharedPreferencesHelper!!.setString(SP.MOVIES_WITHOUT_DETAIL, builder.toString())
        }
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