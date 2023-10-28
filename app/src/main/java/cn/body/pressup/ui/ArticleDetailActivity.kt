package cn.body.pressup.ui

import android.text.Html
import cn.body.pressup.R
import cn.body.pressup.databinding.ActivityArticleDetailBinding
import cn.body.pressup.utils.Utils

class ArticleDetailActivity : BaseActivity<ActivityArticleDetailBinding>() {

    override fun buildLayoutBinding(): ActivityArticleDetailBinding {
        return ActivityArticleDetailBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        val index = intent.getIntExtra("index", 1)
        when (index) {
            1 -> {
                binding.articleTitle.setText(R.string.article_title_1)
            }

            2 -> {
                binding.articleTitle.setText(R.string.article_title_2)
            }

            3 -> {
                binding.articleTitle.setText(R.string.article_title_3)
            }

            4 -> {
                binding.articleTitle.setText(R.string.article_title_4)
            }

            5 -> {
                binding.articleTitle.setText(R.string.article_title_4)
            }
        }
        val content = Utils.readFileFromAssets(this@ArticleDetailActivity, "article_${index}.html")
        binding.articleContent.text = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
    }
}