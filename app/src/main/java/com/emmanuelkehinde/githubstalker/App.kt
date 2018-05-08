package com.emmanuelkehinde.githubstalker

import android.app.Application
import com.emmanuelkehinde.githubstalker.di.component.DaggerDependencyComponent
import com.emmanuelkehinde.githubstalker.di.component.DependencyComponent
import com.emmanuelkehinde.githubstalker.di.module.AppModule
import com.emmanuelkehinde.githubstalker.di.module.DependencyModule
import com.squareup.leakcanary.LeakCanary

class App: Application() {

    private lateinit var dependencyComponent: DependencyComponent

    companion object {
        private lateinit var mInstance: App

        fun getInstance(): App {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        initLeakCanary()

        dependencyComponent = DaggerDependencyComponent.builder()
                .appModule(AppModule(this))
                .dependencyModule(DependencyModule())
                .build()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    fun getDependencyComponent(): DependencyComponent {
        return dependencyComponent
    }
}