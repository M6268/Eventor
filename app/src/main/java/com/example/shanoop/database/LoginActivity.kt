package com.example.shanoop.database

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUserLogged()) {
            startActivity(intentFor<DashBoardActivity>())
            finish()
        } else
            setContentView(R.layout.activity_login)

    }

    fun isUserLogged(): Boolean = getSharedPreferences(SESSION_STORAGE, 0).contains(SESSION_STORAGE)
            &&
            getSharedPreferences(SESSION_STORAGE, 0).getString(SESSION_STORAGE, "").isNotEmpty()

    fun verifyLogin(v: View) {
        if (usernameEditText.text.isEmpty())
            usernameEditText.error = "Invalid Username!"
        if (passwordEditText.text.isEmpty())
            passwordEditText.error = "Invalid Password"
        if (usernameEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
            login()
        }
    }

    fun login() {
        doAsync {
            val body = FormBody.Builder()
                    .add("username", usernameEditText.text.toString())
                    .add("password", passwordEditText.text.toString())
                    .build()

            val login = Request.Builder()
                    .url("https://test3.htycoons.in/api/login")
                    .post(body)
                    .build()

            val client = OkHttpClient()

            val response = client.newCall(login).execute()

            Log.w("LOGIN_RESP", response.body()!!.string())
            uiThread {
                val result = JSONObject(response.body()!!.string())
                when (response.code()) {
                    200 -> {
                        if (result.optBoolean("result", false)) {
                            getSharedPreferences(SESSION_STORAGE, 0).edit().putString(SESSION_STORAGE, result.optString("access_token")).apply()
                            startActivity(intentFor<DashBoardActivity>())
                            finish()
                        } else {
                            Snackbar.make(root, result.optString("message"), Snackbar.LENGTH_INDEFINITE)
                        }
                    }
                    400 -> {
                        Snackbar.make(root, result.optString("message"), Snackbar.LENGTH_INDEFINITE)
                    }
                }

            }

        }

    }

}
