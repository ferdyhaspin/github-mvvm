package app.ferdyhaspin.githubmvvm.main

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import app.ferdyhaspin.githubmvvm.data.MainData
import app.ferdyhaspin.githubmvvm.data.source.MainDataRepository
import app.ferdyhaspin.githubmvvm.data.source.MainDataSource
import app.ferdyhaspin.githubmvvm.util.SingleLiveEvent

class MainViewModel(application: Application, private val mainDataRepository: MainDataRepository) :
    AndroidViewModel(application) {

    val mainDataField: ObservableField<MainData> = ObservableField()
    internal val openRepo = SingleLiveEvent<MainData>()

    fun start() {
        getMainData()
    }

    fun openRepo() {
        openRepo.value = mainDataField.get()
    }

    private fun getMainData() {
        mainDataRepository.getMainData(object : MainDataSource.GetMainDataCallback {
            override fun onDataLoaded(mainData: MainData?) {
                mainDataField.set(mainData)
            }

            override fun onError(msg: String?) {
                Toast.makeText(getApplication(), "Error: ${msg}", Toast.LENGTH_LONG).show()
            }

            override fun onNotAvailable() {
                Toast.makeText(getApplication(), "Data not available", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun onDestroy(){
        mainDataRepository.onDestroy()
    }

}