package com.exam.myapp.ui.pokemon_detail

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.exam.myapp.R
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.databinding.ActivityDetailBinding
import com.exam.myapp.util.DialogHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PokemonDetailActivity: DaggerAppCompatActivity()  {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory)[PokemonDetailViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.pokemonName = intent.getStringExtra("POKEMON_NAME").toString()
        viewModel.start()

        viewModel.pokemonDetail.observe(this) { pokemon ->
            for(type in pokemon.types) {
                val textView = TextView(this).apply {
                    text = type.type.name
                    textSize = 15f
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                    setPadding(70, 5, 70, 5)
                    background = ContextCompat.getDrawable(context, R.drawable.curved_background)
                }

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 40, 10)
                }
                textView.layoutParams = layoutParams

                binding.llTypes.addView(textView)
            }

        }

        viewModel.isBack.observe(this) { back ->
            if(back) finish()
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

    }

    public override fun onDestroy() {
        super.onDestroy()
        DialogHelper.dismissProgressDialog()
    }

}