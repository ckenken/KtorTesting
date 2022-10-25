package com.example.ktortesting.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktortesting.databinding.ActivityMainBinding
import com.example.ktortesting.datamodel.RequestState
import com.example.ktortesting.datamodel.isError
import com.example.ktortesting.datamodel.isLoading
import com.example.ktortesting.datamodel.isSuccess
import com.example.ktortesting.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val schoolListAdapter by lazy { SchoolListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(
            layoutInflater,
            findViewById(android.R.id.content),
            false,
        ).also {
            binding = it
            setContentView(it.root)
        }
        setupViews()
        setupViewModel()
        viewModel.getSchools()
    }

    private fun setupViews() {
        binding.listRecyclerView.apply {
            adapter = schoolListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        binding.errorImage.setOnClickListener {
            viewModel.getSchools()
        }
    }

    private fun setupViewModel() {
        viewModel.pageData.observe(this) { state ->
            Log.d("ckenken", "state = $state")
            binding.progressBar.isVisible = state.isLoading
            binding.errorImage.isVisible = state.isError
            binding.listRecyclerView.isVisible = state.isSuccess
            if (state.isSuccess) {
                schoolListAdapter.items = state.data ?: listOf()
            }
        }
    }
}