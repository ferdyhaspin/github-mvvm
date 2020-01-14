package app.ferdyhaspin.githubmvvm.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.ferdyhaspin.githubmvvm.data.source.MainDataRepository
import app.ferdyhaspin.githubmvvm.main.MainViewModel
import app.ferdyhaspin.githubmvvm.repo.RevoViewModel

class ViewModelFactory private constructor(
    private val application: Application,
    private val mainDataRepository: MainDataRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>)= with(modelClass) {
        when{
            isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(application,mainDataRepository)
            isAssignableFrom(RevoViewModel::class.java) ->
                RevoViewModel(application, mainDataRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }as T

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE
                ?: synchronized(ViewModelFactory::class.java){
                    INSTANCE
                        ?: ViewModelFactory(
                            application, Injection.providedMainDataRepository(application.applicationContext))
                            .also { INSTANCE = it }
                }

        @VisibleForTesting
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}