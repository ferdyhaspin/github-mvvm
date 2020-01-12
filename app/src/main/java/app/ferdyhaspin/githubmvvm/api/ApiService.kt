package app.ferdyhaspin.githubmvvm.api

import app.ferdyhaspin.githubmvvm.api.dao.MainDataDao
import app.ferdyhaspin.githubmvvm.api.dao.RepoDataDao
import app.ferdyhaspin.githubmvvm.util.Constant
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{username}")
    fun getMainData(
        @Path("username") username: String
    ): Observable<MainDataDao>

    @GET("https://api.github.com/users/{username}/repos")
    fun getReposData(
        @Path("username") username: String
    ): Observable<List<RepoDataDao>>

    companion object Factory {

        fun create():ApiService{

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}