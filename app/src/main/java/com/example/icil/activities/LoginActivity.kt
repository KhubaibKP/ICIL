package com.example.icil.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.icil.R
import com.example.icil.beans.DataResponse
import com.example.icil.beans.SharedPrefs
import com.example.icil.retrofit.Api
import com.example.icil.retrofit.WebServiceFactory.retrofit
import com.example.icil.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    var isPasswordVisible = false
    lateinit var loginViewModel: LoginViewModel
    private lateinit var retrofits: Retrofit
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = LoginViewModel()
        sharedPrefs = SharedPrefs(this)
        clickListeners()
        setObserver()

    }

    private fun clickListeners() {
        ivEye.setOnClickListener {
            if (isPasswordVisible) {
                ivEye.setImageResource(R.drawable.ic_hide_password)
                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                etPassword.setSelection(etPassword.text.toString().length)
            } else {
                ivEye.setImageResource(R.drawable.ic_show_password)
                etPassword.inputType = InputType.TYPE_CLASS_TEXT
                etPassword.setSelection(etPassword.text.toString().length)
            }
            isPasswordVisible = !isPasswordVisible
        }

        tvLogin.setOnClickListener {
            showLoading()
            sharedPrefs.saveName(etEmail.text.toString())
            sharedPrefs.savePassword(etPassword.text.toString())
            loginViewModel.login(etEmail.text.toString(), etPassword.text.toString())

        }
    }

    fun setObserver() {

        loginViewModel.mutableLiveDataLogin.observe(this, Observer {

            it?.let {
                hideLoading()
                if (it.status == 1)
                {
                    etEmail.setText("")
                    etPassword.setText("")
                    val intent = Intent(this, UserActivity::class.java)
                    startActivity(intent)
                }
                else if (it.status == 0){
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    fun showLoading() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        if (progressBar2 != null) {
            progressBar2.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        if (progressBar2 != null) {
            progressBar2.visibility = View.GONE
        }
    }
}