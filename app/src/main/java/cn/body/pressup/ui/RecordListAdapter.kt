package cn.body.pressup.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.body.pressup.R
import cn.body.pressup.databinding.ItemRecordBinding
import cn.body.pressup.models.BloodPress
import cn.body.pressup.utils.Utils

class RecordListAdapter : RecyclerView.Adapter<RecordListAdapter.ItemViewHolder>() {

    var dataList: ArrayList<BloodPress> = arrayListOf()

    inner class ItemViewHolder : RecyclerView.ViewHolder {

        val binding: ItemRecordBinding

        constructor(item: View) : super(item) {
            binding = ItemRecordBinding.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]
        when (item.level()) {
            BloodPress.LEVEL_A -> {
                holder.binding.ivLevel.setImageResource(R.mipmap.stage_1)
                holder.binding.tvTitle.setText(R.string.stage_level_title_1)
            }

            BloodPress.LEVEL_B -> {
                holder.binding.ivLevel.setImageResource(R.mipmap.stage_2)
                holder.binding.tvTitle.setText(R.string.stage_level_title_2)
            }

            BloodPress.LEVEL_C -> {
                holder.binding.ivLevel.setImageResource(R.mipmap.stage_3)
                holder.binding.tvTitle.setText(R.string.stage_level_title_3)
            }

            BloodPress.LEVEL_D -> {
                holder.binding.ivLevel.setImageResource(R.mipmap.stage_4)
                holder.binding.tvTitle.setText(R.string.stage_level_title_4)
            }

            BloodPress.LEVEL_E -> {
                holder.binding.ivLevel.setImageResource(R.mipmap.stage_5)
                holder.binding.tvTitle.setText(R.string.stage_level_title_5)
            }

            BloodPress.LEVEL_F -> {
                holder.binding.ivLevel.setImageResource(R.mipmap.stage_6)
                holder.binding.tvTitle.setText(R.string.stage_level_title_6)
            }
        }

        holder.binding.tvDatetime.text = Utils.formatDatetime(item.datetime)
        holder.binding.tvSystolic.text = item.systolic.toString()
        holder.binding.tvDiastolic.text = item.diastolic.toString()
        holder.binding.tvPulse.text = item.pulse.toString()

        holder.itemView.setOnClickListener {
            itemClickCallback?.invoke(item, position)
        }

    }

    var itemClickCallback: ((BloodPress, Int) -> Unit)? = null

}