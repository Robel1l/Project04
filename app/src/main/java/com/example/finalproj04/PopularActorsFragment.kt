package com.example.finalproj04


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

// API key for database
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

// Helper function for JSON parsing
fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class PopularActorsFragment : Fragment(), OnListFragmentInteractionListener {

    // list of actors to display
    private val film_actors = mutableListOf<Actor>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate layout for fragment
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        // UI elements for progress bar and recycler view
        val Bar = view.findViewById<ContentLoadingProgressBar>(R.id.prog)
        val reView = view.findViewById<RecyclerView>(R.id.movie_list)

        // set layout and adapter of recycler view
        reView.layoutManager = GridLayoutManager(view.context, 2)
        // update recycler view
        updateAdapter(Bar, reView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // create and set up AsyncHttpClient and parameters
        val cli = AsyncHttpClient()
        val par = RequestParams()
        par["api_key"] = API_KEY

        // request to display popular actors
        cli["https://api.themoviedb.org/3/person/popular", par, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                progressBar.hide()
                Log.d("PopularActorsFragment", " API success with status $statusCode")

                try {
                    // parse JSON response
                    val parsedJson = createJson().decodeFromString(
                        ActorSearchResponse.serializer(),
                        json.jsonObject.toString()
                    )


                    // clear and update list
                    film_actors.clear()
                    parsedJson.results?.let { list -> film_actors.addAll(list) }

                    // set adapt for new list
                    recyclerView.adapter = ActorRecyclerViewAdapter(film_actors, this@PopularActorsFragment)
                    Log.d("PopularActorsFragment", " Loaded ${film_actors.size} actors")

                } catch (e: JSONException) {
                    // log parsing error
                    Log.e("PopularActorsFragment", "JSON parse error: ${e.message}")
                } catch (e: Exception) {
                    // log any other errors
                    Log.e("PopularActorsFragment", "Unexpected error: ${e.message}")
                }
            }

            // if request fails
            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressBar.hide()
                Log.e("PopularActorsFragment", " API failed — code: $statusCode, response: $errorResponse")
            }
        }]
    }

    // handle actor click
    override fun onItemClick(item: Actor) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("ACTOR_EXTRA", item)
        context?.startActivity(intent)
    }
}
