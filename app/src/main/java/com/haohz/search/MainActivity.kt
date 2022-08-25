package com.haohz.search

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.haohz.search.loading.LoadingDialogHelper
import com.haohz.search.search.CustomFlowLayout
import com.haohz.search.search.LocalSimpleDataCacheUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val INDEX_SEARCH = "indexSearch"
    }
    var mLoadingDialogHelper: LoadingDialogHelper? = null
    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_search.requestFocus()
        handleHistoryWords()

        tv_search.setOnClickListener {
            doSearch()
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(edit: Editable?) {
                val isSearchable = edit?.length != 0

                tv_search.isEnabled = isSearchable
                tv_search.background = ContextCompat.getDrawable(
                    this@MainActivity,
                    if (isSearchable) R.drawable.bg_search_button_enable else R.drawable.bg_search_button
                )

            }

        })

        et_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                doSearch()
                return@setOnEditorActionListener true
            }
            false
        }


    }

    fun doSearch(){
        showLoadingDialog("searching...")
        val searchKey: String = et_search.text.toString().trim()
        LocalSimpleDataCacheUtil.insert(
            applicationContext,
            INDEX_SEARCH,
            searchKey,
            5
        )
        handleHistoryWords()
        mHandler.postDelayed({
            startActivity(Intent(this,SearchActivity::class.java))
        }, 2000)

    }

    fun handleHistoryWords(){
        val cacheData = LocalSimpleDataCacheUtil.query(
            applicationContext, INDEX_SEARCH
        )
        if (cacheData.size>0){
            tv_tip.visibility = View.VISIBLE
            fl_search_history.visibility =View.VISIBLE
            addFlowLayoutChildView(fl_search_history,cacheData)
        }else{
            tv_tip.visibility = View.GONE
            fl_search_history.visibility =View.GONE
        }
    }

    fun addFlowLayoutChildView(fl: CustomFlowLayout, dataList: List<String?>) {
        fl.removeAllViews()
        for (i in dataList.indices) {
            val mInflater = LayoutInflater.from(this)
            val itemView: View = mInflater.inflate(R.layout.item_whole_search_flowlayout, fl, false)
            val tv = itemView.findViewById<TextView>(R.id.tv_name)
            tv.setTextColor(Color.parseColor("#202020"))
            val word = dataList[i]
            tv.text = word
            fl.addView(itemView)
            itemView.setOnClickListener { view: View? ->

                startActivity(Intent(this,SearchActivity::class.java))
            }
        }
    }

    fun showLoadingDialog(content: String?) {
        mLoadingDialogHelper?.dismiss()
        mLoadingDialogHelper = LoadingDialogHelper.with(this, supportFragmentManager)
        mLoadingDialogHelper?.show(content)
    }

    fun dismissLoadingDialog() {
        mLoadingDialogHelper?.dismiss()
    }


    override fun onPause() {
        super.onPause()
        dismissLoadingDialog()
    }
}