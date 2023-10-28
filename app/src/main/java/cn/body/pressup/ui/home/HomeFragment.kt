package cn.body.pressup.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.body.pressup.databinding.FragmentHomeBinding
import cn.body.pressup.ui.EditActivity
import cn.body.pressup.ui.RecordListActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.run {
            btnNew.setOnClickListener {
                startActivity(Intent(requireActivity(), EditActivity::class.java))
            }

            btnAdd.setOnClickListener {
                startActivity(Intent(requireActivity(), EditActivity::class.java))
            }

            btnRecord.setOnClickListener {
                startActivity(Intent(requireActivity(), RecordListActivity::class.java))
            }
        }

        homeViewModel.liveData.observe(viewLifecycleOwner) { data ->
            if (data.isNotEmpty()) {
                binding.emptyLayout.isVisible = false
                binding.recordLayout.isVisible = true

                val lastData = data[0]
                binding.tvSystolic.text = (lastData.systolic).toString()
                binding.tvDiastolic.text = (lastData.diastolic).toString()
                binding.tvPulse.text = (lastData.pulse).toString()
            } else {
                binding.emptyLayout.isVisible = true
                binding.recordLayout.isVisible = false
            }
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.loadData()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}