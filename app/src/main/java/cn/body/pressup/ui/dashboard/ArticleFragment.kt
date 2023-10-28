package cn.body.pressup.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.body.pressup.databinding.FragmentArticleBinding
import cn.body.pressup.ui.ArticleDetailActivity

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.articleItem1.setOnClickListener {
            startActivity(Intent(requireActivity(), ArticleDetailActivity::class.java).putExtra("index", 1))
        }
        binding.articleItem2.setOnClickListener {
            startActivity(Intent(requireActivity(), ArticleDetailActivity::class.java).putExtra("index", 2))
        }
        binding.articleItem3.setOnClickListener {
            startActivity(Intent(requireActivity(), ArticleDetailActivity::class.java).putExtra("index", 3))
        }
        binding.articleItem4.setOnClickListener {
            startActivity(Intent(requireActivity(), ArticleDetailActivity::class.java).putExtra("index", 4))
        }
        binding.articleItem5.setOnClickListener {
            startActivity(Intent(requireActivity(), ArticleDetailActivity::class.java).putExtra("index", 5))
        }

        val articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        articleViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}