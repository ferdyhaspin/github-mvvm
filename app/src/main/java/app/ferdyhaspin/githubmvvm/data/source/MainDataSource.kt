package app.ferdyhaspin.githubmvvm.data.source

import app.ferdyhaspin.githubmvvm.data.MainData
import app.ferdyhaspin.githubmvvm.data.RepoData

interface MainDataSource {
    fun getMainData(callback: GetMainDataCallback)
    fun getRepoData(callback: GetRepoDataCallback)
    fun onDestroy()

    interface GetMainDataCallback {
        fun onDataLoaded(mainData: MainData?)
        fun onNotAvailable()
        fun onError(msg: String?)
    }

    interface GetRepoDataCallback {
        fun onDataLoaded(repoData: MutableList<RepoData?>)
        fun onNotAvailable()
        fun onError(msg: String?)
    }
}