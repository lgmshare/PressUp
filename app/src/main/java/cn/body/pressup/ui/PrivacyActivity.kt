package cn.body.pressup.ui

import cn.body.pressup.databinding.ActivityPrivacyBinding

class PrivacyActivity : BaseActivity<ActivityPrivacyBinding>() {

    override fun buildLayoutBinding(): ActivityPrivacyBinding {
        return ActivityPrivacyBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}