package com.example.cmed_task2

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.cmed_task2.databinding.ActivityNextBinding
import com.example.cmed_task2.networkCommunication.CharacterModelList
import com.google.gson.Gson

class NextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNextBinding

    private lateinit var nextDetailsAdapter: NextDetailsAdapter
    private val viewModel: NextDetailsViewModel by viewModels()

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareViews()
        getBundleData()



    }

    private fun getBundleData() {
        intent.extras?.let {
            val item: CharacterModelList = Gson().fromJson(intent.extras?.getString("CHARACTER_LIST"), CharacterModelList::class.java)

            Toast.makeText(this,item.name, Toast.LENGTH_SHORT).show()
            viewModel.dataList.postValue(mutableListOf())
            Toast.makeText(this,item.actorName, Toast.LENGTH_SHORT).show()
            viewModel.dataList.postValue(mutableListOf())
            Toast.makeText(this,item.houseName, Toast.LENGTH_SHORT).show()
            viewModel.dataList.postValue(mutableListOf())
            Toast.makeText(this,item.imageThumbnail, Toast.LENGTH_SHORT).show()
            viewModel.dataList.postValue(mutableListOf())
        }
    }

    private fun prepareViews() {
        binding.recyclerViewNextDetails.adapter = nextDetailsAdapter
        viewModel.dataList.observe(this) {
            nextDetailsAdapter.updateData(it)

        }    }
}