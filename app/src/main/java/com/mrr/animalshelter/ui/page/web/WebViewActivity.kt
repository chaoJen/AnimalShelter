package com.mrr.animalshelter.ui.page.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mrr.animalshelter.R
import com.mrr.animalshelter.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : BaseActivity() {

    companion object {
        private const val EXTRA_KEY_WEB_URL = "EXTRA_KEY_WEB_URL"
        fun getStartActivityIntent(context: Context, url: String): Intent {
            return Intent(context, WebViewActivity::class.java)
                .putExtra(EXTRA_KEY_WEB_URL, url)
        }
    }

    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        mUrl = intent.getStringExtra(EXTRA_KEY_WEB_URL)
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        webView.settings.javaScriptEnabled = true
        mUrl?.let { webView.loadUrl(it) } ?: finish()
    }
}