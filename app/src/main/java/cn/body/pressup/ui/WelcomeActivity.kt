package cn.body.pressup.ui

import android.animation.ValueAnimator
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import cn.body.pressup.App
import cn.body.pressup.databinding.ActivityWelcomeBinding
import cn.body.pressup.extensions.progressAnimation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    private var job: Job? = null

    private var progressAnimator: ValueAnimator? = null


    override fun buildLayoutBinding(): ActivityWelcomeBinding {
        return ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun onStart() {
        super.onStart()
        binding.progressbar.progress = 0
        job = lifecycleScope.launch {
            kotlin.runCatching {
                withTimeoutOrNull(6000) {
                    startProgressAnimation(6000)
                    launch {
                        delay(6000)
                    }
                }
            }.onSuccess {
                if (!App.getInstance().running) {
                    startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                }
                finish()
            }.onFailure {
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stopProgressAnimation()
        job?.cancel()
    }

    override fun onBackPressed() {
    }

    private fun startProgressAnimation(duration: Long) {
        progressAnimator?.cancel()
        progressAnimator = binding.progressbar.progressAnimation(duration, binding.progressbar.progress)
    }

    private fun stopProgressAnimation() {
        progressAnimator?.cancel()
        progressAnimator = null
    }
}