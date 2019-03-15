package com.example.kedditappexample.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kedditappexample.R
import com.example.kedditappexample.commons.InfiniteScrollListener
import com.example.kedditappexample.commons.RedditNews
import com.example.kedditappexample.commons.RxBaseFragment
import com.example.kedditappexample.commons.inflate
import com.example.kedditappexample.features.news.adapter.NewAdapter
import kotlinx.android.synthetic.main.fragment_new.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewFragment : RxBaseFragment() {
    companion object {
        private val KEY_REDDIT_NEWS = "redditNews"
    }

    private var redditNews: RedditNews? = null
    private val newsManager by lazy {
        NewsManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_new, container, false)
        /*val view = container?.inflate(R.layout.fragment_new)
        rvNews = view?.findViewById(R.id.rvNews)
        rvNews?.setHasFixedSize(true) // using this setting to improve performance
        rvNews?.layoutManager = LinearLayoutManager(context)
*/
        return container?.inflate(R.layout.fragment_new)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // rvNews is view element in fragment_new(using Kotlin Extension plugin)
        /*rvNews.setHasFixedSize(true)
        //rvNews.layoutManager = LinearLayoutManager(context)
        val linearLayout = LinearLayoutManager(context)
        rvNews.layoutManager = linearLayout
        rvNews.clearOnScrollListeners()
        rvNews.addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))*/
        rvNews.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))

        }
        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            (rvNews.adapter as NewAdapter).clearAndAddNews(redditNews!!.news)
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (rvNews.adapter as NewAdapter).getNews()
        if (redditNews != null && news.size > 0) {
            outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }
    }

    private fun requestNews() {
        //val subscription = newsManager.getNews()
        /**
         * first time will send empty string for after parameter.
         * Next time we will have redditNews set with the next page to
         * navigate with the after param.
         */
        val subscription = newsManager.getNews(redditNews?.after ?: "")
            .subscribeOn(Schedulers.io()) // move executed process get data from service to another Thread (background thread)
            .observeOn(AndroidSchedulers.mainThread()) // allow data handle on main thread
            .subscribe(
                { retrievedNews ->
                    //(rvNews.adapter as NewAdapter).addNews(retrievedNews)
                    redditNews = retrievedNews
                    (rvNews.adapter as NewAdapter).addNews(retrievedNews.news)
                },
                { e ->
                    Snackbar.make(rvNews, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            )

        // add subcription to subcriptions pool
        subcriptions.add(subscription)
    }

    private fun initAdapter() {
        if (rvNews.adapter == null) {
            rvNews.adapter = NewAdapter()
        }
    }
}