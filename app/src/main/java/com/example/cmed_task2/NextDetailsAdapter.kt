package com.example.cmed_task2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cmed_task2.databinding.ItemNextDetailsBinding
import com.example.cmed_task2.networkCommunication.CharacterModelList

class NextDetailsAdapter(private val context: Context) : RecyclerView.Adapter<NextDetailsAdapter.ViewHolder>() {
    private var dataList: MutableList<CharacterModelList> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding = ItemNextDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NextDetailsAdapter.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.binding.textViewName.text = item.name
        holder.binding.textViewHouse.text = item.houseName
        holder.binding.textViewActor.text = item.actorName

        Glide.with(context)
            .load(item.imageThumbnail)
            .into(holder.binding.imageViewThumbnail)



    }

    fun updateData(updatedDataList: MutableList<CharacterModelList>) {
        val diffCallback = DiffUtilItem(dataList, updatedDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(updatedDataList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder (val binding: ItemNextDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffUtilItem(
        private val oldList: MutableList<CharacterModelList>,
        private val newList: List<CharacterModelList>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            //val (_, value, name) = oldList
            //val (_, value1, name1) = newList
            return oldList[oldPosition] == newList[newPosition]
        }
    }
}