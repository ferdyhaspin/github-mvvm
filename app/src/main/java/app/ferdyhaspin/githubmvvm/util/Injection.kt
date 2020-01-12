package app.ferdyhaspin.githubmvvm.util

import android.content.Context
import android.preference.PreferenceManager
import app.ferdyhaspin.githubmvvm.data.source.MainDataRepository
import app.ferdyhaspin.githubmvvm.data.source.local.MainDataLocalSource
import app.ferdyhaspin.githubmvvm.data.source.remote.MainDataRemoteSource

object Injection {
    fun providedMainDataRepository(context: Context) = MainDataRepository.getInstance(
        MainDataRemoteSource,
        MainDataLocalSource.getInstance(PreferenceManager.getDefaultSharedPreferences(context))!!
    )
}