package cn.body.pressup.ui

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cn.body.pressup.databases.AppDatabase
import cn.body.pressup.databinding.ActivityRecordListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecordListActivity : BaseActivity<ActivityRecordListBinding>() {

    private var loadJob: Job? = null

    private val adapter by lazy {
        RecordListAdapter()
    }

    override fun buildLayoutBinding(): ActivityRecordListBinding {
        return ActivityRecordListBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        adapter.itemClickCallback = { item, position ->
            startActivity(Intent(this@RecordListActivity, EditActivity::class.java).apply {
                putExtra("item", item)
            })
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onStop() {
        super.onStop()
        loadJob?.cancel()
    }

    private fun loadData() {
        loadJob = lifecycleScope.launch {
            kotlin.runCatching {
                val data = AppDatabase.getInstance().pressDataDao().queryAll()
                val list = data.reversed()
                list
            }.onSuccess { data ->
                if (data.isNotEmpty()) {
                    adapter.dataList.clear()
                    adapter.dataList.addAll(data)
                    adapter.notifyDataSetChanged()
                }
            }.onFailure {

            }
        }
    }
}