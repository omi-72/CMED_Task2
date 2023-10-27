package com.example.cmed_task2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cmed_task2.networkCommunication.CharacterModelList

class NextDetailsViewModel: ViewModel() {
    val dataList: MutableLiveData<MutableList<CharacterModelList>> = MutableLiveData(mutableListOf())

}