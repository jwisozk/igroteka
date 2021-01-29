package com.jwisozk.igroteka.view

import androidx.recyclerview.widget.DiffUtil
import com.jwisozk.igroteka.model.Game

class GamesDiffCallback : DiffUtil.ItemCallback<Game>() {

    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
        oldItem == newItem
}