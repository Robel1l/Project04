package com.example.finalproj04


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ActorRecyclerViewAdapter(
    // list of actor to displayed
    private val mov_actors:  MutableList<Actor>,
    // listener when actors is cliched
    private val mov_Li: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<ActorRecyclerViewAdapter.ActorViewHolder>() {

        // ViewHolder for recycler view
    inner class ActorViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        // current actor being displayed
        var flim_I: Actor? = null
            // UI elements for actor name and headshot
        val film_actorName: TextView = mView.findViewById<View>(R.id.nV) as TextView
        val film_actorHshot: ImageView = mView.findViewById<View>(R.id.hshotV) as ImageView

        // return actors name as string
            override fun toString(): String {
            return film_actorName.toString()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorViewHolder {
        // inflate layout for actor
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        // get actor at position
        val actor = mov_actors[position]

        // bind actor name and headshot
        holder.flim_I = actor
        holder.film_actorName.text = actor.actor_name

        // load actors headshot image
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/" + actor.hshotURL)
            .centerInside()
            .into(holder.film_actorHshot)

        // set click listener for actor
        holder.mView.setOnClickListener {
            holder.flim_I?.let { actor ->
                mov_Li?.onItemClick(actor)
            }
        }
    }

    // return total number of actors
    override fun getItemCount(): Int {
        return mov_actors.size
    }
}
