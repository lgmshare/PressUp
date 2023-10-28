package cn.body.pressup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.body.pressup.databases.AppDatabase
import cn.body.pressup.models.BloodPress
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _liveData = MutableLiveData<List<BloodPress>>().apply {
        value = arrayListOf()
    }
    val liveData: LiveData<List<BloodPress>> = _liveData

    fun loadData() {
        viewModelScope.launch {
            val data = AppDatabase.getInstance().pressDataDao().queryAll()
            val list = data.reversed()
            _liveData.value = list
        }
    }
}