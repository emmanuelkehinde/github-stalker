package com.emmanuelkehinde.githubstalker.ui.repo_list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.emmanuelkehinde.githubstalker.R
import com.emmanuelkehinde.githubstalker.data.model.Repo

class RepoListAdapter(val context: Context, val repoListListener: RepoListListener):
        RecyclerView.Adapter<RepoListAdapter.RepoListVH>() {

    private var repos: ArrayList<Repo> = ArrayList()

    interface RepoListListener {
        fun onRepoClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListVH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_repo_item,parent,false)
        return RepoListVH(view)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: RepoListVH, position: Int) {
        holder.txtRepoName.text = repos[position].name
        holder.txtRepoStars.text = repos[position].stars
    }

    inner class RepoListVH(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var txtRepoName: TextView = itemView?.findViewById(R.id.txtRepoName)!!
        var txtRepoStars: TextView = itemView?.findViewById(R.id.txtRepoStars)!!

        init {
            itemView?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            repoListListener.onRepoClicked(adapterPosition)
        }
    }

    fun setRepoListAndRefresh(repos: ArrayList<Repo>) {
        this.repos = repos
        notifyDataSetChanged()
    }
}