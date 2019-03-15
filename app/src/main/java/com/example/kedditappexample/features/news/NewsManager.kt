package com.example.kedditappexample.features.news

import com.example.kedditappexample.api.RestAPI
import com.example.kedditappexample.commons.RedditNews
import com.example.kedditappexample.commons.RedditNewsItem
import rx.Observable

// News Manager allows you to request news from Reddit API.

class NewsManager(private val api: RestAPI = RestAPI()) {

    //fun getNews(): Observable<List<RedditNewsItem>> {
    //fun getNews(limit: String = "10"): Observable<List<RedditNewsItem>> {
    /**
     *
     * Returns Reddit News paginated by the given limit.
     *
     * @param after indicates the next page to navigate.
     * @param limit the number of news to request.
     */
    fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create { subscriber ->
            //val callResponse = api.getNews("", limit)
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                //val news = response.body()!!.data.children.map {
                val dataResponse = response.body()!!.data
                val news = dataResponse.children.map {
                    val item = it.data
                    RedditNewsItem(
                        item.author, item.title, item.num_comments,
                        item.created, item.thumbnail, item.url
                    )
                }
                //subscriber.onNext(news)
                val redditNews = RedditNews(
                    dataResponse.after ?: "",
                    dataResponse.before ?: "",
                    news
                )

                subscriber.onNext(redditNews)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }


            /*val news = mutableListOf<RedditNewsItem>()
            for (i in 1..10) {
                news.add(RedditNewsItem(
                    "author$i",
                    "Title $i",
                    i, // number of comments
                    1457207701L - i * 200, // time
                    "http://lorempixel.com/200/200/technics/$i", // image url
                    "url"
                ))
            }
            subscriber.onNext(news)
            */
        }
    }
}