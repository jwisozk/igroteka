package com.jwisozk.igroteka.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.ItemGameBinding
import com.jwisozk.igroteka.model.Game
import jp.wasabeef.glide.transformations.BitmapTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    private val transformation: BitmapTransformation

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

    private fun setName(game: Game) {
        binding.gameName.text = game.name
    }

    private fun setClickListener(listener: (Game) -> Unit, game: Game) {
        itemView.setOnClickListener {
            listener(game)
        }
    }

    private fun setThumbnail(game: Game) {
        Glide.with(binding.gameThumbnail.context)
            .load(game.thumbnail)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ph_game)
                    .error(R.drawable.ph_game)
            )
            .transform(transformation)
            .fitCenter()
            .centerCrop()
            .into(binding.gameThumbnail)
    }
}