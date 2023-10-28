package cn.body.pressup.ui

import android.content.Intent
import android.os.Build
import android.widget.NumberPicker
import androidx.lifecycle.lifecycleScope
import cn.body.pressup.R
import cn.body.pressup.databases.AppDatabase
import cn.body.pressup.databinding.ActivityEditBinding
import cn.body.pressup.extensions.sp2px
import cn.body.pressup.extensions.toast
import cn.body.pressup.models.BloodPress
import kotlinx.coroutines.launch

class EditActivity : BaseActivity<ActivityEditBinding>() {

    private lateinit var bpData: BloodPress

    override fun buildLayoutBinding(): ActivityEditBinding {
        return ActivityEditBinding.inflate(layoutInflater)
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
            bpData.systolic = 110
            bpData.diastolic = 80
            bpData.pulse = 80
            bpData.datetime = System.currentTimeMillis()
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            finish()
        }

        if (bpData.id == 0) {
            binding.tvTitle.setText(R.string.add_bp_record)
        } else {
            binding.tvTitle.setText(R.string.edit_record)
        }

        binding.btnSave.setOnClickListener {
            val systolic = binding.wheelSystolic.value
            val diastolic = binding.wheelDiastolic.value
            val pulse = binding.wheelPulse.value

            bpData.systolic = systolic
            bpData.diastolic = diastolic
            bpData.pulse = pulse

            if (systolic < diastolic) {
                toast(R.string.error_tips)
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (bpData.id > 0) {
                    AppDatabase.getInstance().pressDataDao().update(bpData)
                } else {
                    bpData.datetime = System.currentTimeMillis()
                    AppDatabase.getInstance().pressDataDao().insert(bpData)
                }
                startActivity(Intent(this@EditActivity, RecordDetailActivity::class.java).apply {
                    putExtra("item", bpData)
                })
                finish()
            }
        }

        if (Build.VERSION.SDK_INT >= 29) {
            binding.wheelSystolic.textSize = sp2px(20.0f).toFloat()
        }
        binding.wheelSystolic.minValue = 0
        binding.wheelSystolic.maxValue = 200
        binding.wheelSystolic.value = bpData.systolic
        binding.wheelSystolic.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                val systolic = binding.wheelSystolic.value
                bpData.systolic = systolic
                updateLevelView()
            }

        })
        if (Build.VERSION.SDK_INT >= 29) {
            binding.wheelDiastolic.textSize = sp2px(20.0f).toFloat()
        }
        binding.wheelDiastolic.showDividers = NumberPicker.SHOW_DIVIDER_NONE
        binding.wheelDiastolic.minValue = 0
        binding.wheelDiastolic.maxValue = 200
        binding.wheelDiastolic.value = bpData.diastolic
        binding.wheelDiastolic.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                val diastolic = binding.wheelDiastolic.value
                bpData.diastolic = diastolic
                updateLevelView()
            }
        })
        if (Build.VERSION.SDK_INT >= 29) {
            binding.wheelPulse.textSize = sp2px(20.0f).toFloat()
        }
        binding.wheelPulse.showDividers = NumberPicker.SHOW_DIVIDER_NONE
        binding.wheelPulse.minValue = 0
        binding.wheelPulse.maxValue = 200
        binding.wheelPulse.value = bpData.pulse
        binding.wheelPulse.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                val pulse = binding.wheelPulse.value
                bpData.pulse = pulse
                updateLevelView()
            }
        })

        binding.searchBar.isEnabled = false

        updateLevelView()
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