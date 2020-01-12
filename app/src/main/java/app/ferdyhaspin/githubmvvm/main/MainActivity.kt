package app.ferdyhaspin.githubmvvm.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import app.ferdyhaspin.githubmvvm.R
import app.ferdyhaspin.githubmvvm.repo.RepoActivity
import app.ferdyhaspin.githubmvvm.util.obtainViewModel
import app.ferdyhaspin.githubmvvm.util.replaceFragmentInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mActivity: AppCompatActivity
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mActivity = this

        setupFragment()
        setupViewModel()
    }

    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.frameMain)
        replaceFragmentInActivity(MainFragment.newInstance(), R.id.frameMain)
    }


    private fun setupViewModel() {
        viewModel = obtainViewModel().apply{
            openRepo.observe(this@MainActivity, Observer{
                startActivity(Intent(mActivity, RepoActivity::class.java))
            })
        }
    }

    fun obtainViewModel(): MainViewModel = obtainViewModel(MainViewModel::class.java)
}
