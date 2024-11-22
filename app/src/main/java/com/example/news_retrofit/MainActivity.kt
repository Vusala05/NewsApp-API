package com.example.news_retrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://newsapi.org/v2/"
    private var articleList = ArrayList<Article>()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        newsAdapter = NewsAdapter(articleList)
        binding.recyclerView.adapter = newsAdapter

       binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        loadData()
    }

    private fun filterList(query : String?){
        if (query !=null) {
            val filteredList = ArrayList<Article>()
            for (i in articleList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }


            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            } else {
                newsAdapter.setFilteredList(filteredList)
            }
        }


    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NewsApi::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { apiResponse ->
                        articleList = ArrayList(apiResponse.articles)
                        newsAdapter.updateData(articleList)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Response error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }


    }

