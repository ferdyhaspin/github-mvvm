package app.ferdyhaspin.githubmvvm.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.ferdyhaspin.githubmvvm.R
import app.ferdyhaspin.githubmvvm.data.RepoData
import app.ferdyhaspin.githubmvvm.databinding.RepoItemBinding

class RepoAdapter(
    private var repoData: MutableList<RepoData>,
    private var repoViewModel: RevoViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val repoItemBinding: RepoItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.repo_item,
                parent,
                false
            )
        return RepoHolder(repoItemBinding)
    }

    override fun getItemCount(): Int = repoData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val datas = repoData[position]
        val actionListener = object : ReposItemActionListener {
            override fun onRepoClicked() {
                repoViewModel.openRepo.value = datas.url
            }
        }
        (holder as RepoHolder).bind(datas, actionListener)
    }

    fun replaceData(repoData: MutableList<RepoData>) {
        setList(repoData)
    }

    fun setList(repoData: MutableList<RepoData>) {
        this.repoData = repoData
        notifyDataSetChanged()
    }

    class RepoHolder(binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val repoItemBinding = binding

        fun bind(repoData: RepoData, userActionListener: ReposItemActionListener) {
            repoItemBinding.action = userActionListener
            repoItemBinding.datas = repoData
            repoItemBinding.executePendingBindings()
        }
    }

}