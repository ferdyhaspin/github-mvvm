package app.ferdyhaspin.githubmvvm.repo

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import app.ferdyhaspin.githubmvvm.data.RepoData
import app.ferdyhaspin.githubmvvm.data.source.MainDataRepository
import app.ferdyhaspin.githubmvvm.data.source.MainDataSource
import app.ferdyhaspin.githubmvvm.util.SingleLiveEvent

class RevoViewModel(application: Application, private val mainDataRepository: MainDataRepository) :
    AndroidViewModel(application) {

    val repoList: ObservableList<RepoData> = ObservableArrayList()
    internal val openRepo = SingleLiveEvent<String>()

    fun start() {
        getRepos()
    }

    private fun getRepos() {
        mainDataRepository.getRepoData(object : MainDataSource.GetRepoDataCallback {

            override fun onDataLoaded(repoData: MutableList<RepoData?>) {
                with(repoList) {
                    clear()
                    addAll(repoData)
                }
            }

            override fun onNotAvailable() {
                Toast.makeText(getApplication(), "No Data Found", Toast.LENGTH_SHORT).show()
            }

            override fun onError(msg: String?) {
                Toast.makeText(getApplication(), "Error at - $msg", Toast.LENGTH_SHORT).show()
            }
        })
    }

}