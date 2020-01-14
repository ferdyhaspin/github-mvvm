package app.ferdyhaspin.githubmvvm.repo

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import app.ferdyhaspin.githubmvvm.R
import app.ferdyhaspin.githubmvvm.util.obtainViewModel
import app.ferdyhaspin.githubmvvm.util.replaceFragmentInActivity

class RepoActivity : AppCompatActivity() {

    private lateinit var mActivity: AppCompatActivity
    private lateinit var viewModel: RevoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)

        mActivity = this
        setupViewModel()
        setupFragment()
    }

    private fun setupViewModel() {
        viewModel = obtainViewModel().apply {
            openRepo.observe(this@RepoActivity, Observer {
                onRepoClicked(it)
            })
        }
    }

    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.frameRepo)
        replaceFragmentInActivity(RepoFragment.newInstance(), R.id.frameRepo)
    }

    private fun onRepoClicked(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(mActivity, R.color.colorPrimary))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(mActivity, Uri.parse(url))
    }

    fun obtainViewModel(): RevoViewModel = obtainViewModel(RevoViewModel::class.java)

}
