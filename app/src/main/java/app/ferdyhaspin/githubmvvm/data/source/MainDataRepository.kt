package app.ferdyhaspin.githubmvvm.data.source

import app.ferdyhaspin.githubmvvm.data.MainData
import app.ferdyhaspin.githubmvvm.data.RepoData
import app.ferdyhaspin.githubmvvm.data.source.local.MainDataLocalSource

class MainDataRepository(
    val remoteDataSource: MainDataSource,
    val localDataSource: MainDataSource
) : MainDataSource {


    override fun getMainData(callback: MainDataSource.GetMainDataCallback) {
        remoteDataSource.getMainData(
            object : MainDataSource.GetMainDataCallback {
                override fun onDataLoaded(mainData: MainData?) {
                    callback.onDataLoaded(mainData)
                }

                override fun onNotAvailable() {
                    callback.onNotAvailable()
                }

                override fun onError(msg: String?) {
                    callback.onError(msg)
                }
            }
        )
    }

    override fun getRepoData(callback: MainDataSource.GetRepoDataCallback) {
        remoteDataSource.getRepoData(object :
            MainDataSource.GetRepoDataCallback {
            override fun onNotAvailable() {
                callback.onNotAvailable()
            }

            override fun onError(msg: String?) {
                callback.onError(msg)
            }

            override fun onDataLoaded(repoData: MutableList<RepoData?>) {
                callback.onDataLoaded(repoData)
            }
        })
    }

    override fun onDestroy() {

    }

    companion object {
        private var INSTANCE: MainDataRepository? = null

        @JvmStatic
        fun getInstance(
            mainDataRemoteSource: MainDataSource,
            newsLocalDataSource: MainDataLocalSource
        ) =
            INSTANCE ?: synchronized(
                MainDataRepository::class.java
            ) {
                INSTANCE
                    ?: MainDataRepository(
                        mainDataRemoteSource,
                        mainDataRemoteSource
                    )
                        .also { INSTANCE = it }

            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}