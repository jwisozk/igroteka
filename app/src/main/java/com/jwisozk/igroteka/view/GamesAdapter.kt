package com.jwisozk.igroteka.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.jwisozk.igroteka.databinding.ListItemGameBinding
import com.jwisozk.igroteka.model.Game

class GamesAdapter(private val gameFrameClickListener: (Game) -> Unit) :
    ListAdapter<Game, GameViewHolder>(GamesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position), gameFrameClickListener)
    }
}