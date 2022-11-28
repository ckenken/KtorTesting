package com.example.ktortesting.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktortesting.databinding.ActivityMainPagingBinding
import com.example.ktortesting.datamodel.isError
import com.example.ktortesting.datamodel.isLoading
import com.example.ktortesting.datamodel.isSuccess
import com.example.ktortesting.viewmodel.MainPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainPagingActivity : AppCompatActivity() {

    private val viewModel: MainPagingViewModel by viewModels()
    private lateinit var binding: ActivityMainPagingBinding

    private val schoolPagingListAdapter = SchoolPagingListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainPagingBinding.inflate(
            layoutInflater,
            findViewById(android.R.id.content),
            false,
        ).also {
            binding = it
            setContentView(it.root)
        }
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        binding.listRecyclerView.apply {
            adapter = schoolPagingListAdapter
            layoutManager = LinearLayoutManager(this@MainPagingActivity)
        }
        binding.errorImage.setOnClickListener {
            setupViewModel()
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.pageData.collectLatest {
                schoolPagingListAdapter.submitData(it)
            }
        }
    }
}