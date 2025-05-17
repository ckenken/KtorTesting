package com.example.ktortesting.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktortesting.databinding.ActivityEpoxyBinding
import com.example.ktortesting.datamodel.isLoading
import com.example.ktortesting.datamodel.isSuccess
import com.example.ktortesting.viewmodel.MainViewModel

class EpoxyActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityEpoxyBinding

    private val schoolListAdapter by lazy { SchoolListAdapter() }

    private val singleTextController : SingleTextController by lazy { SingleTextController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityEpoxyBinding.inflate(
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
        val linearLayoutManager = LinearLayoutManager(this@EpoxyActivity)
        binding.listRecyclerView.apply {
//            adapter = schoolListAdapter
            adapter = singleTextController.adapter
            addItemDecoration(DividerItemDecoration(this@EpoxyActivity, linearLayoutManager.orientation))
            layoutManager = linearLayoutManager
//            singleTextController.requestModelBuild()
        }
        binding.errorImage.setOnClickListener {
            viewModel.getSchools()
        }
    }

    private fun setupViewModel() {
        viewModel.pageData.observe(this) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.listRecyclerView.isVisible = state.isSuccess
            if (state.isSuccess) {
//                schoolListAdapter.items = state.data ?: listOf()
                singleTextController.schoolNameItems = state.data!!

            }
        }
    }
}