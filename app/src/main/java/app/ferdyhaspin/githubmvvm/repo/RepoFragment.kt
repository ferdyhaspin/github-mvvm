package app.ferdyhaspin.githubmvvm.repo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.ferdyhaspin.githubmvvm.databinding.FragmentRepoBinding

/**
 * A simple [Fragment] subclass.
 */
class RepoFragment : Fragment() {

    private lateinit var viewBinding: FragmentRepoBinding
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentRepoBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as RepoActivity).obtainViewModel()
        }

        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRepo()
    }

    private fun setupRepo() {
        val viewModel = viewBinding.viewModel
        if (viewModel != null) {
            repoAdapter = RepoAdapter(viewModel.repoList, viewModel)
            viewBinding.rvRepo.adapter = repoAdapter

        }
    }

    override fun onResume() {
        super.onResume()
        viewBinding.viewModel?.start()
    }

    companion object {
        fun newInstance() = RepoFragment().apply {}
    }

}
