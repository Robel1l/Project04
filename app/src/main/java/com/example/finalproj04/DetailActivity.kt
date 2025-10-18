package com.example.finalproj04



import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    // UI elements for actor details
    private lateinit var hshotView: ImageView
    private lateinit var posterV: ImageView
    private lateinit var nVi: TextView
    private lateinit var famousFr_Vi: TextView
    private lateinit var oVi: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail)

        // link ui elements to layout elements
        hshotView = findViewById(R.id.hshot)
        posterV = findViewById(R.id.film_des)
        nVi = findViewById(R.id.deName)
        famousFr_Vi = findViewById(R.id.Famous_For)
        oVi = findViewById(R.id.oV)

        // get actor from intent and display details
        val intent = intent
        val actor = intent.getSerializableExtra("ACTOR_EXTRA") as Actor

        // display movie or tv show actors known for
        famousFr_Vi.text = "famous_fr: " +
                (actor.famous_fr?.get(0)?.famous_frTitle
                    ?: actor.famous_fr?.get(0)?.famus_frName
                    ?: "None")

        // display actor name and overview
        nVi.text = actor.actor_name
        oVi.text = actor.famous_fr?.get(0)?.desc

        // load actors headshot
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + actor.hshotURL)
            .into(hshotView)

        // load poster image of known for
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + actor.famous_fr?.get(0)?.famour_frPosterU)
            .into(posterV)
    }
}
