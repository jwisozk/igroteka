package com.jwisozk.igroteka.view

import androidx.recyclerview.widget.RecyclerView
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.ItemGameBinding
import com.jwisozk.igroteka.model.Game
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    private val transformation: Transformation

    init {
        val dimension = itemView.resources.getDimension(R.dimen.cornerRadius)
        val cornerRadius = dimension.toInt()
        transformation = RoundedCornersTransformation(cornerRadius, 0)
    }

    fun bind(game: Game, listener: (Game) -> Unit) {
        setName(game)
        setThumbnail(game)
        setClickListener(listener, game)
    }

    private fun setClickListener(
        listener: (Game) -> Unit,
        game: Game
    ) {
        itemView.setOnClickListener { listener(game) }
    }

    private fun setName(game: Game) {
        binding.gameName.text = game.name
    }

    private fun setThumbnail(game: Game) {
        Picasso.get()
            .load(game.thumbnail)
            .placeholder(R.drawable.ph_game)
            .error(R.drawable.ph_game)
            .transform(transformation)
            .fit()
            .centerCrop()
            .into(binding.gameThumbnail)
    }
}