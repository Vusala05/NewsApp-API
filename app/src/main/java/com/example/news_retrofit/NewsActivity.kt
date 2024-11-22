package com.example.news_retrofit

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.news_retrofit.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val url = intent.getStringExtra("url")

        if (url != null) {
            webViewSetUp(url)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp(url: String) {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            binding.webView.settings.builtInZoomControls = true



        }
    }

}
