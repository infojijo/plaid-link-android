package com.plaid.linksample.network

import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

/**
 * Android-specific LinkToken with Gson annotation
 */
data class AndroidLinkToken(
  @SerializedName("link_token") val link_token: String
)

/**
 * Retrofit interface for RxJava calls
 */
interface LinkSampleApiRetrofit {
  @POST("/api/create_link_token")
  fun getLinkToken(): Single<AndroidLinkToken>
}

object LinkTokenRequester {
  // This value is setup to work with emulators. Modify this value to your PC's IP address if not.
  private val baseUrl = "http://10.0.2.2:8000"

  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(OkHttpClient.Builder().build())
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .build()

  private val api = retrofit.create(LinkSampleApiRetrofit::class.java)

  /**
   * In production, make an API request to your server to fetch
   * a new link_token. Learn more at https://plaid.com/docs/#create-link-token.
   *
   * You can optionally curl for a link_token instead of running the node-quickstart server,
   * and copy and paste the link_token value here.
   */
/*  val token
    get() = api.getLinkToken()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .map { it.link_token }*/

  //Comment out the above and uncomment the below to use a curled Link Token
  val token
    get() = Single.just("link-sandbox-2fdd8054-8018-4ec6-b9b0-1c0856890623")
}
