package app.ferdyhaspin.githubmvvm.data.source.remote

import android.util.Log
import app.ferdyhaspin.githubmvvm.api.ApiService
import app.ferdyhaspin.githubmvvm.api.dao.RepoDataDao
import app.ferdyhaspin.githubmvvm.data.MainData
import app.ferdyhaspin.githubmvvm.data.RepoData
import app.ferdyhaspin.githubmvvm.data.source.MainDataSource
import app.ferdyhaspin.githubmvvm.util.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object MainDataRemoteSource : MainDataSource {

    private val apiService = ApiService.create()
    private val compositeDisposable = CompositeDisposable()

    override fun getMainData(callback: MainDataSource.GetMainDataCallback) {
        compositeDisposable.add(
            apiService.getMainData(Constant.username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    run {
                        if (it.name != "") {
                            val mainData = MainData(
                                it.name,
                                it.location,
                                it.avatar_url,
                                "${it.followers}\nFollowers",
                                "${it.following}\nFollowings",
                                "${it.public_repos}\nRepos"
                            )
                            callback.onDataLoaded(mainData)
                        } else {
                            callback.onNotAvailable()
                        }

                    }
                }, {
                    callback.onError(it.message)
                })
        )
    }

    override fun getRepoData(callback: MainDataSource.GetRepoDataCallback) {
        compositeDisposable.add(
            apiService.getReposData(Constant.username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    run {

                        if (it.isNotEmpty()) {
                            Log.i("xx", " ${it.size}")

                            val listRepo: MutableList<RepoData?> = mutableListOf<RepoData?>()
                            for (item: RepoDataDao in it) {
                                val repoData = RepoData(
                                    item.name,
                                    item.language,
                                    item.description,
                                    item.html_url
                                )
                                listRepo.add(repoData)
                            }
                            callback.onDataLoaded(listRepo)
                        } else {
                            callback.onNotAvailable()
                        }

                    }
                }, {
                    callback.onError(it.message)
                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}