package cn.body.pressup

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import cn.body.pressup.ui.WelcomeActivity

class App : Application(), LifecycleEventObserver {

    var startCount: Int = 0

    var running = false

    companion object {

        @Volatile
        private lateinit var INSTANCE: App

        fun getInstance(): App {
            return INSTANCE
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                startCount++
                if (startCount == 1) {
                    if (activity !is WelcomeActivity) {
                        activity.startActivity(Intent(activity, WelcomeActivity::class.java))
                    }
                }
            }

            override fun onActivityResumed(p0: Activity) {
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
                startCount--
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }
        })
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
            }

            Lifecycle.Event.ON_STOP -> {
            }

            else -> {
            }
        }
    }
}