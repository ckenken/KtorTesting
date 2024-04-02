package com.example.ktortesting.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktortesting.databinding.ActivityMainPagingBinding
import com.example.ktortesting.viewmodel.MainPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainPagingActivity : AppCompatActivity() {

    private val viewModel: MainPagingViewModel by viewModel()

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