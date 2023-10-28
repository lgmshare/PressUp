package cn.body.pressup.ui

import android.content.Intent
import android.os.Build
import cn.body.pressup.R
import cn.body.pressup.databinding.ActivityRecordDetailBinding
import cn.body.pressup.models.BloodPress
import cn.body.pressup.utils.Utils

class RecordDetailActivity : BaseActivity<ActivityRecordDetailBinding>() {

    private lateinit var bpData: BloodPress

    override fun buildLayoutBinding(): ActivityRecordDetailBinding {
        return ActivityRecordDetailBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val lastData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item", BloodPress::class.java)
        } else {
            intent.getParcelableExtra("item")
        }
        if (lastData != null) {
            bpData = lastData
        } else {
            bpData = BloodPress()
            bpData.systolic = 100
            bpData.diastolic = 70
            bpData.pulse = 70
            bpData.datetime = System.currentTimeMillis()
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        updateLevelView()
        binding.tvDatetime.text = Utils.formatDatetime(bpData.datetime)

        binding.tvSystolic.text = bpData.systolic.toString()
        binding.tvDiastolic.text = bpData.diastolic.toString()
        binding.tvPulse.text = bpData.pulse.toString()

        val list = arrayListOf<Int>(1, 2, 3, 4, 5)
        val index_1 = list.random()
        list.remove(index_1)
        val index_2 = list.random()

        when (index_1) {
            1 -> {
                binding.article1.setText(R.string.article_title_1)
            }

            2 -> {
                binding.article1.setText(R.string.article_title_2)
            }

            3 -> {
                binding.article1.setText(R.string.article_title_3)
            }

            4 -> {
                binding.article1.setText(R.string.article_title_4)
            }

            5 -> {
                binding.article1.setText(R.string.article_title_4)
            }
        }
        when (index_2) {
            1 -> {
                binding.article2.setText(R.string.article_title_1)
            }

            2 -> {
                binding.article2.setText(R.string.article_title_2)
            }

            3 -> {
                binding.article2.setText(R.string.article_title_3)
            }

            4 -> {
                binding.article2.setText(R.string.article_title_4)
            }

            5 -> {
                binding.article2.setText(R.string.article_title_4)
            }
        }

        binding.article1.setOnClickListener {
            startActivity(Intent(this, ArticleDetailActivity::class.java).putExtra("index", index_1))
        }

        binding.article2.setOnClickListener {
            startActivity(Intent(this, ArticleDetailActivity::class.java).putExtra("index", index_2))
        }

        binding.btnHome.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun updateLevelView() {
        binding.stageTips.setText(getString(R.string.systolic_x_diastolic_x, bpData.systolic, bpData.diastolic))
        when (bpData.level()) {
            BloodPress.LEVEL_A -> {
                binding.searchBar.progress = 0
                binding.stageLevel.setText(R.string.stage_level_title_1)
                binding.stageIntroduce.setText(R.string.stage_level_tips_1)
            }

            BloodPress.LEVEL_B -> {
                binding.searchBar.progress = 20
                binding.stageLevel.setText(R.string.stage_level_title_2)
                binding.stageIntroduce.setText(R.string.stage_level_tips_2)
            }

            BloodPress.LEVEL_C -> {
                binding.searchBar.progress = 40
                binding.stageLevel.setText(R.string.stage_level_title_3)
                binding.stageIntroduce.setText(R.string.stage_level_tips_3)
            }

            BloodPress.LEVEL_D -> {
                binding.searchBar.progress = 60
                binding.stageLevel.setText(R.string.stage_level_title_4)
                binding.stageIntroduce.setText(R.string.stage_level_tips_4)
            }

            BloodPress.LEVEL_E -> {
                binding.searchBar.progress = 80
                binding.stageLevel.setText(R.string.stage_level_title_5)
                binding.stageIntroduce.setText(R.string.stage_level_tips_5)
            }

            BloodPress.LEVEL_F -> {
                binding.searchBar.progress = 100
                binding.stageLevel.setText(R.string.stage_level_title_6)
                binding.stageIntroduce.setText(R.string.stage_level_tips_6)
            }
        }
    }
}