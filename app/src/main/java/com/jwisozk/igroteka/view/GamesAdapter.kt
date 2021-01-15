package com.jwisozk.igroteka.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jwisozk.igroteka.databinding.ItemGameBinding
import com.jwisozk.igroteka.model.Game

class GamesAdapter(private val listener: (Game) -> Unit): ListAdapter<Game, GameViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem == newItem
        }
    }
}