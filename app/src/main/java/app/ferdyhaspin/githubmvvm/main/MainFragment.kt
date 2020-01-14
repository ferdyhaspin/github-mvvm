package app.ferdyhaspin.githubmvvm.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.ferdyhaspin.githubmvvm.databinding.FragmentMainBinding


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var viewBinding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)

        viewBinding = FragmentMainBinding.inflate(inflater, container, false).apply {
            vm = (activity as MainActivity).obtainViewModel()

            action = object : MainActionListener {
                override fun onClickRepos() {
                    vm?.openRepo()
                }
            }
        }

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewBinding.vm?.start()

    }

    companion object {
        fun newInstance() = MainFragment().apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding.vm?.onDestroy()
    }

}
