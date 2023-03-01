package com.example.icil.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.icil.R
import com.example.icil.beans.SharedPrefs
import com.example.icil.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user.etPassword
import java.io.ByteArrayOutputStream
import java.util.Calendar


class UserActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel
    lateinit var sharedPrefs: SharedPrefs
    var photoBase: String = ""
    var dept: String = ""
    var desg: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        sharedPrefs = SharedPrefs(this)


        etUsername.setText(sharedPrefs.getName().toString())
        etPassword.setText(sharedPrefs.getPassword().toString())

        userViewModel = UserViewModel()
        clickListeners()
        setObserver()
    }

    private fun setObserver() {

        val department = resources.getStringArray(R.array.department)
        val designation = resources.getStringArray(R.array.designation)

        if (spinner != null)
        {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, department)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    etDept.text = department[position]
                    dept = department[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }

        if (spinner2 != null)
        {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, designation)
            spinner2.adapter = adapter

            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    etDesg.text = designation[position]
                    desg = designation[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
        }

        userViewModel.mutableLiveDataUser.observe(this, Observer {
            it?.let {
                hideLoading()
                if (it.status == 1)
                {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    etFullName.setText("")
                    etCnic.setText("")
                    etCode.setText("")
                    etDate.setText("")
                    photoBase = ""
                    ivImage.setImageDrawable(null)
                    etDept.setText("")
                    dept = ""
                    etDesg.setText("")
                    desg = ""


                }
                else if (it.status == 0)
                {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun clickListeners() {
        tvCreateEmp.setOnClickListener {
            showLoading()
            userViewModel.createEmployee(
                sharedPrefs.getName().toString(), sharedPrefs.getPassword().toString(), etFullName.text.toString(),
            etCnic.text.toString(), etCode.text.toString(), etDate.text.toString(),
                dept, desg, photoBase
            )
        }

        tvCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startForResult.launch(cameraIntent)

        }

        tvDateCalendar.setOnClickListener {
            val calendar = Calendar.getInstance()
            val years = calendar.get(Calendar.YEAR)
            val months = calendar.get(Calendar.MONTH)
            val days = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { datePicker, year, month, day ->

                    etDate.setText("$year-${month + 1}-$day")

                }, years, months, days
            )
            datePickerDialog.show()
        }
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK)
        {
            val photo: Bitmap = it.data?.extras?.get("data") as Bitmap
            ivImage.setImageBitmap(photo)

            val stream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.toByteArray()

            val photoBase64 = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
            photoBase = photoBase64
        }
    }

    fun showLoading() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        if (progressBar != null) {
            progressBar.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        if (progressBar != null) {
            progressBar.visibility = View.GONE
        }
    }

}