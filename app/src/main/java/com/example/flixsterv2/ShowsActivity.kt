package com.example.flixsterv2

import android.os.Bundle
import android.util.Log
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
import okhttp3.OkHttpClient

class ShowsActivity : AppCompatActivity() {
    lateinit var items: ArrayList<movieItem>

    lateinit var adapter: movieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shows)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        items = ArrayList()

        performAPIcall()

        // Lookup the RecyclerView in activity layout
        val showsRv = findViewById<RecyclerView>(R.id.shows)
        // Create adapter passing in the list of emails
        adapter = movieAdapter(items)
        // Attach the adapter to the RecyclerView to populate items
        showsRv.adapter = adapter
        // Set layout manager to position the items
        showsRv.layoutManager = LinearLayoutManager(this)
    }

    private fun performAPIcall() {
        val client = AsyncHttpClient()
        val params = RequestParams()
        //params["limit"] = "5"
        //params["page"] = "0"

        client["https://api.themoviedb.org/3/discover/tv?&api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", params, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // Access a JSON array response with `json.jsonArray`
                Log.d("DEBUG ARRAY", json.jsonObject.toString())
                // Access a JSON object response with `json.jsonObject`


                var showsJson = json.jsonObject.getJSONArray("results")

                for ( i in 0 until showsJson.length()){
                    Log.d("DEBUG OBJECT",showsJson.getJSONObject(i).toString())
//
                    val currentMovie = showsJson.getJSONObject(i)
                    val title = currentMovie.getString("name")
                    var description = currentMovie.getString("overview")
                    val posterPath = currentMovie.getString("poster_path")

                    if (description == ""){
                        description = "No overview provided."
                    }

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