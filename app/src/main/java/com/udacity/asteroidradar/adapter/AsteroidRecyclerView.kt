package com.udacity.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemBinding
import com.udacity.asteroidradar.model.Asteroid

class AsteroidRecyclerView(
    val clickListener: (asteroid: Asteroid) -> Unit
) : RecyclerView.Adapter<AsteroidRecyclerView.AsteroidViewHolder>() {

    private var asteroidlist: List<Asteroid> = emptyList()

    fun setList(asteroidlist: List<Asteroid>) {
        this.asteroidlist = asteroidlist
        notifyDataSetChanged()
    }

    class AsteroidViewHolder(
        private val binding: ListItemBinding,
        val clickListener: (asteroid: Asteroid) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid

            binding.root.setOnClickListener {
                clickListener(asteroid)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return AsteroidViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid: Asteroid = asteroidlist[position]
        holder.bind(asteroid)

    }

    override fun getItemCount(): Int {
        return asteroidlist.size
    }
}