package com.example.cmed_task2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.cmed_task2.databinding.ActivityMainBinding
import com.example.cmed_task2.networkCommunication.CharacterModelList
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var progressDialog: Dialog
    private lateinit var characterAdapter: CharacterAdapter

    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareViews()
        progressDialog = ProgressDialog(this).progressDialog()
        viewModel.loadFollowingData()

        viewModel.isDialogueShowing.observe(this, Observer {
            if (it == true){
                showProgressDialog()
            }else{
                hideProgressDialog()
            }
        })

    }

    private fun prepareViews() {
        characterAdapter = CharacterAdapter(object : CharacterAdapterInterface{
            override fun onItemClick(item: CharacterModelList){
                Log.d(this@MainActivity::class.java.simpleName, "item.character.toString()::"+item.name)
                Log.d(this@MainActivity::class.java.simpleName, "item.character.toString()::"+item.actorName)
                Log.d(this@MainActivity::class.java.simpleName, "item.character.toString()::"+item.houseName)
                Log.d(this@MainActivity::class.java.simpleName, "item.character.toString()::"+item.imageThumbnail)

                nextActivity(NextActivity::class.java){
                    putSerializable("CHARACTER_LIST",
                        Gson().toJson(item)
                    )
                }
            }
        })



        binding.recyclerView.adapter = characterAdapter
        viewModel.followingTagDataList.observe(this) {
            characterAdapter.updateData(it)

        }

    }

    private fun showProgressDialog() {
        this.runOnUiThread {
            progressDialog.show()
        }
    }

    private fun hideProgressDialog() {
        this.runOnUiThread {
            progressDialog.dismiss()
        }
    }
}

fun <T> Context.nextActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}