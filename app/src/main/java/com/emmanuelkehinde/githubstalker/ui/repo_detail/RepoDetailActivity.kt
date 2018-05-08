package com.emmanuelkehinde.githubstalker.ui.repo_detail

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.emmanuelkehinde.githubstalker.App
import com.emmanuelkehinde.githubstalker.R
import com.emmanuelkehinde.githubstalker.data.model.Repo
import com.emmanuelkehinde.githubstalker.helper.Constants
import com.emmanuelkehinde.githubstalker.util.ImageUtil
import kotlinx.android.synthetic.main.activity_repo_detail.*
import kotlinx.android.synthetic.main.content_repo_detail.*
import javax.inject.Inject

class RepoDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var imageUtil: ImageUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        App.getInstance().getDependencyComponent().inject(this)

        val repo: Repo? = intent.getParcelableExtra(Constants.REPO_EXTRA)
        setAllViews(repo)
    }

    private fun setAllViews(repo: Repo?) {
        repo?.let {
            collapsingToolbar.title = it.name
            txtRepoDescription.text = it.description
            txtRepoOwner.text = it.owner.name
            txtRepoStars.text = it.stars
            txtRepoForks.text = it.forks

            imageUtil.loadImage(it.owner.avatarUrl,imgRepoOwner)
            fabVisitRepo.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url)).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
