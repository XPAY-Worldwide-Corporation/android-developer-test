package com.exam.myapp.ui.pokemon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.databinding.ActivityMainBinding
import com.exam.myapp.ui.pokemon_detail.PokemonDetailActivity
import com.exam.myapp.util.DialogHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class PokemonListActivity: DaggerAppCompatActivity()  {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PokemonViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory)[PokemonViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.start()

        val adapter = PokemonListAdapter { pokemon -> onPokemonClicked(pokemon) }
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.adapter = adapter

        viewModel.allPokemonList.observe(this) { pokemon ->
            viewModel.pokemonListDisplayed.value = pokemon
        }

        viewModel.pokemonListDisplayed.observe(this) { pokemon ->
            viewModel.isShowNoResult.value = pokemon.isEmpty()
            adapter.updatePokemonList(pokemon)
            binding.progressBar.visibility = View.GONE
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingVisibility.observe(this) {
            if(it) {
                DialogHelper.showLoading(this, null, "Loading...")
            } else {
                DialogHelper.dismissProgressDialog()
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = true
//            viewModel.offset = 0
            viewModel.getPokemonList()
        }

//        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.pokemonList.value!!.size - 1
//                    && viewModel.hasNextItems && recyclerView.canScrollVertically(-1)) {
//                    binding.progressBar.visibility = View.VISIBLE
//                    viewModel.getPokemonList()
//                }
//            }
//        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchPokemons(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchPokemons(newText)
                } else {
                    viewModel.pokemonListDisplayed.postValue(viewModel.allPokemonList.value)
                }
                return true
            }
        })

    }

    private fun onPokemonClicked(pokemon: PokemonEnity) {
        val intent = Intent(this, PokemonDetailActivity::class.java).apply {
            putExtra("POKEMON_NAME", pokemon.name)
        }

        startActivity(intent)
    }

    public override fun onDestroy() {
        super.onDestroy()
        DialogHelper.dismissProgressDialog()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            view.clearFocus()
//            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}