package com.example.cmed_task2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cmed_task2.databinding.ItemCharacterListBinding
import com.example.cmed_task2.networkCommunication.CharacterModelList

class CharacterAdapter(private var characterAdapterInterface: CharacterAdapterInterface): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    private var dataList: MutableList<CharacterModelList> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val binding = ItemCharacterListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(updatedDataList: MutableList<CharacterModelList>) {
        val diffCallback = DiffUtilItem(dataList, updatedDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(updatedDataList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: CharacterAdapter.ViewHolder, position: Int) {
        val item = dataList[position]

        holder.binding.textViewName.text = item.name
        holder.binding.textViewSpecies.text = item.species
        holder.binding.textViewGender.text = item.gender
        holder.binding.textViewHouse.text = item.houseName
        holder.binding.textViewDateOfBirth.text = item.dateOfBirth
        holder.binding.textViewYearOfBirth.text = item.yearOfBirth
        holder.binding.textViewAncestry.text = item.ancestry
        holder.binding.textViewEyeColour.text = item.eyeColour
        holder.binding.textViewHairColour.text = item.hairColour
        holder.binding.textViewPatronus.text = item.patronus
        holder.binding.textViewActor.text = item.actorName

        holder.binding.textViewId.text = position.toString()

//        Glide.with(context)
//            .load(item.imageThumbnail)
//            .into(holder.binding.imageViewThumbnail)

        holder.binding.root.setOnClickListener {
            characterAdapterInterface.onItemClick(item)
        }


    }

    class ViewHolder (val binding: ItemCharacterListBinding) : RecyclerView.ViewHolder(binding.root)


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

interface CharacterAdapterInterface {
    fun  onItemClick(item : CharacterModelList)
}