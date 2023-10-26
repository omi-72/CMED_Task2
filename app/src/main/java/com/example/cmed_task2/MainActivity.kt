package com.example.cmed_task2

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.cmed_task2.databinding.ActivityMainBinding

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
        characterAdapter = CharacterAdapter(this)
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