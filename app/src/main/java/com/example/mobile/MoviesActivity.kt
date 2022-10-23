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
import com.example.mobile.list.movie.MovieAdapter
import com.example.mobile.list.movie.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response

class MoviesActivity : AppCompatActivity() {
    private var rvMovieList: RecyclerView? = null
    private var adapter: MovieAdapter? = null
    private var count = 30

    private var apiResponse = listOf<MovieDetail>()
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
        var movies: List<MovieDetail>

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
                intent.putExtra("originalTitleRomanised",it.originalTitleRomanised)
                intent.putExtra("originalTitle",it.originalTitle)
                intent.putExtra("directorName",it.directorName)
                intent.putExtra("productorName",it.productorName)
                intent.putExtra("releaseDate",it.releaseDate)
                intent.putExtra("runningTime",it.runningTime)
                intent.putExtra("rtScore",it.rtScore)
                intent.putExtra("description",it.description)
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

    private fun getFilms(): List<MovieDetail> {
        var apiResponse = ArrayList<MovieDetail>()
        val api = RetroFitClient.retrofit.create(MyAPI::class.java)
        val callGetPost = api.getFilms()
        callGetPost.enqueue(object : retrofit2.Callback<List<MovieResponse>>{
            override fun onResponse(call: Call<List<MovieResponse>>, response: Response<List<MovieResponse>>) {
                val apiRest = response.body()
                apiRest?.forEach {
                    val movie = MovieDetail(it.title, it.original_title_romanised, it.description, it.director, it.producer, it.release_date, it.running_time, it.rt_score)
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

    private fun convertToMovie(contador: Int): List<MovieDetail> {
        val allFilms = sharedPreferencesHelper!!.getString(SP.MOVIES_WITHOUT_DETAIL)
        Log.d("LOG", "Data guardada: " + allFilms)
        val OriginalTitle = StringBuilder()
        val OriginalTitleRomanised = StringBuilder()
        val Description = StringBuilder()
        val DirectorName = StringBuilder()
        val ProductorName = StringBuilder()
        val ReleaseDate = StringBuilder()
        val RunningTime = StringBuilder()
        val RtScore = StringBuilder()

        val elements = arrayOf("OriginalTitle", "OriginalTitleRomanised", "Description", "DirectorName", "ProductorName", "ReleaseDate", "RunningTime", "RtScore")
        var cont = 0
        var actualElement: String
        var beginning = 0
        var beginningAddition = 2

        var films = ArrayList<MovieDetail>()
        var movie = MovieDetail()
        for(counter in allFilms.indices){
            if(films.count() <= contador-1) {
                if(allFilms[counter] == '|'){
                    actualElement = elements[cont]
                    when(actualElement){
                        "OriginalTitle" -> {
                            if(films.count() == 1)
                                beginning += 2
                            OriginalTitle.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + 2
                            movie.originalTitle = OriginalTitle.toString()
                        }
                        "OriginalTitleRomanised" -> {
                            OriginalTitleRomanised.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + 2
                            movie.originalTitleRomanised = OriginalTitleRomanised.toString()
                        }
                        "Description" -> {
                            Description.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + 2
                            movie.description = Description.toString()
                        }
                        "DirectorName" -> {
                            DirectorName.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + 2
                            movie.directorName = DirectorName.toString()
                        }
                        "ProductorName" -> {
                            ProductorName.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + 2
                            movie.productorName = ProductorName.toString()
                        }
                        "ReleaseDate" -> {
                            ReleaseDate.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + 2
                            movie.releaseDate = ReleaseDate.toString()
                        }
                        "RunningTime" -> {
                            RunningTime.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + 2
                            movie.runningTime = RunningTime.toString()
                        }
                        "RtScore" -> {
                            RtScore.append(allFilms.substring(beginning, counter - 1))
                            beginning = counter + beginningAddition
                            movie.rtScore = RtScore.toString()
                        }
                    }
                    cont+=1
                }
                if (allFilms[counter] == '*') {
                    Log.d("LOG", movie.originalTitle + movie.originalTitleRomanised + movie.description + movie.directorName +
                            movie.productorName + movie.releaseDate + movie.runningTime + movie.rtScore)
                    films.add(MovieDetail(movie.originalTitle, movie.originalTitleRomanised, movie.description, movie.directorName,
                        movie.productorName, movie.releaseDate, movie.runningTime, movie.rtScore))
                    OriginalTitle.setLength(0)
                    OriginalTitleRomanised.setLength(0)
                    Description.setLength(0)
                    DirectorName.setLength(0)
                    ProductorName.setLength(0)
                    ReleaseDate.setLength(0)
                    RunningTime.setLength(0)
                    RtScore.setLength(0)
                    cont = 0
                    beginningAddition = 4
                }
            }
        }
        Log.d("LOG", "Data guardada: " + allFilms)
        return films;
    }

    private fun uploadDataInSharedPreferences(){
        val builder = StringBuilder()
        if(apiResponse.isNotEmpty() && sharedPreferencesHelper!!.getString(SP.MOVIES_WITHOUT_DETAIL)
                .isNotEmpty()) {
            for(counter in apiResponse.indices){
                builder.append(apiResponse[counter].originalTitle)
                builder.append(" | ")
                builder.append(apiResponse[counter].originalTitleRomanised)
                builder.append(" | ")
                builder.append(apiResponse[counter].description)
                builder.append(" | ")
                builder.append(apiResponse[counter].directorName)
                builder.append(" | ")
                builder.append(apiResponse[counter].productorName)
                builder.append(" | ")
                builder.append(apiResponse[counter].releaseDate)
                builder.append(" | ")
                builder.append(apiResponse[counter].runningTime)
                builder.append(" | ")
                builder.append(apiResponse[counter].rtScore)
                builder.append(" | ")
                if(counter != apiResponse.size-1){
                    builder.append("* ")
                }
            }
            sharedPreferencesHelper!!.setString(SP.MOVIES_WITHOUT_DETAIL, builder.toString())
            Log.d("REST", sharedPreferencesHelper!!.getString(SP.MOVIES_WITHOUT_DETAIL))
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