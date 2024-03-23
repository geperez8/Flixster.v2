package com.example.flixsterv2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixster.movieAdapter
import movieItem
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    lateinit var items: ArrayList<movieItem>

    lateinit var adapter: movieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        items = ArrayList()

        performAPIcall()

        // Lookup the RecyclerView in activity layout
        val moviesRv = findViewById<RecyclerView>(R.id.movies)
        // Create adapter passing in the list of emails
        adapter = movieAdapter(items)
        // Attach the adapter to the RecyclerView to populate items
        moviesRv.adapter = adapter
        // Set layout manager to position the items
        moviesRv.layoutManager = LinearLayoutManager(this)


        val actorsButton = findViewById<Button>(R.id.actors)
        val showsButton = findViewById<Button>(R.id.shows)

        showsButton.setOnClickListener {
            val i = Intent(this@MainActivity, ShowsActivity::class.java)

            startActivity(i)
        }

        actorsButton.setOnClickListener {
            val i = Intent(this@MainActivity, ActorsActivity::class.java)

            startActivity(i)
        }
    }

    private fun performAPIcall() {
        val client = AsyncHttpClient()
        val params = RequestParams()
        //params["limit"] = "5"
        //params["page"] = "0"

        client["https://api.themoviedb.org/3/movie/now_playing?&api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", params, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // Access a JSON array response with `json.jsonArray`
                // Log.d("DEBUG ARRAY", json.jsonArray.toString())
                // Access a JSON object response with `json.jsonObject`


                var moviesJson = json.jsonObject.getJSONArray("results")

                for ( i in 0 until moviesJson.length()){
                    Log.d("DEBUG OBJECT",moviesJson.getJSONObject(i).toString())

                    val currentMovie = moviesJson.getJSONObject(i)
                    val title = currentMovie.getString("title")
                    val description = currentMovie.getString("overview")
                    val posterPath = currentMovie.getString("poster_path")

                    val foundMovie = movieItem(title, description, posterPath)

                    items.add(foundMovie)

                }

                Log.d("DEBUG ITEMS OBJECT", items.toString())


                adapter.notifyDataSetChanged()


            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
            }
        }]


    }
}