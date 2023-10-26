package com.example.cmed_task2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cmed_task2.networkCommunication.CharacterModelList

class CharacterViewModel : ViewModel() {
    val followingTagDataList: MutableLiveData<MutableList<CharacterModelList>> = MutableLiveData(mutableListOf())
    var isDialogueShowing = MutableLiveData<Boolean>(false)


    fun loadFollowingData() {
        isDialogueShowing.postValue(true)

        CharacterModelList().loadApi{ isSuccess, dataList, error ->
            isDialogueShowing.postValue(false)

            if (isSuccess) {
                dataList?.let {
                    followingTagDataList.postValue(it.toMutableList())
                }
            }
        }
    }

}