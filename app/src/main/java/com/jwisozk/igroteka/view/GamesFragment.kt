package com.jwisozk.igroteka.view

import android.content.Context.INPUT_METHOD_SERVICE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.FragmentGamesBinding
import com.jwisozk.igroteka.viewmodel.GamesViewModel

class GamesFragment : Fragment(R.layout.fragment_games) {

    private var binding: FragmentGamesBinding? = null
    private lateinit var viewModel: GamesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamesBinding.bind(view)
        init()
        initListeners()
    }

    private fun init() {
        val manager = GridLayoutManager(activity, 2)
        binding?.gamesList?.layoutManager = manager
    }

    private fun initListeners() {
        binding?.let {
            it.searchInput.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                    hideSoftInputFromWindow(v)
            }
        } ?: throw IllegalStateException("Binding is null in InputFragment")
    }

    private fun hideSoftInputFromWindow(v: View) {
        val inputMethodManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GamesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = GamesFragment()
    }
}