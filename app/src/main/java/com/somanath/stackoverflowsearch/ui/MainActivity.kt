package com.somanath.stackoverflowsearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.somanath.stackoverflowsearch.R
import com.somanath.stackoverflowsearch.data.model.Item
import com.somanath.stackoverflowsearch.databinding.ActivityMainBinding
import com.somanath.stackoverflowsearch.ui.adapter.SearchResultsAdapter
import com.somanath.stackoverflowsearch.viewmodel.StackOverflowViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , IItemClickListener{

    private lateinit var binding: ActivityMainBinding
    private val viewModel: StackOverflowViewModel by viewModels()
    private  var adapter: SearchResultsAdapter   = SearchResultsAdapter(this)
    private val disposables = mutableListOf<Disposable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        observeUpdate()
    }

    private fun observeUpdate() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.questions.observe(this) { questions ->
            adapter.updateData(questions)
        }

        viewModel.error.observe(this@MainActivity) { errorMessage ->
            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        binding.apply {
            with(searchResults) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                this.adapter = this@MainActivity.adapter
            }

            with(searchEditText) {
                val disposable =  textChanges()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { query ->
                        if (query.isNotEmpty()) {
                            viewModel.searchQueries(query.toString())
                        } else if(searchEditText.text?.isEmpty() == true){
                            adapter.updateData(emptyList())
                        }
                    }

                disposables.add(disposable)
            }
        }
    }

    override fun onItemClick(item: Item) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.forEach { if(!it.isDisposed) { it.dispose()} }
    }
}