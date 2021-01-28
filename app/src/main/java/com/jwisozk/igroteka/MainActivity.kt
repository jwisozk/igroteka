package com.jwisozk.igroteka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.jwisozk.igroteka.model.Game
import com.jwisozk.igroteka.util.KeyboardResetByClickOutside
import com.jwisozk.igroteka.view.GameDetailFragment

class MainActivity : AppCompatActivity() {

    private val keyboardResetByClickOutside = KeyboardResetByClickOutside()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun transitionToGameDetailFragment(game: Game) {
        supportFragmentManager.beginTransaction()
            .add(R.id.containerFragmentView, GameDetailFragment.newInstance(game))
            .addToBackStack(null)
            .commit()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        keyboardResetByClickOutside.onDispatchTouchEvent(event, currentFocus)
        return super.dispatchTouchEvent(event)
    }
}