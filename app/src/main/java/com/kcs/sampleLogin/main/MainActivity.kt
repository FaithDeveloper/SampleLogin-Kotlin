package com.kcs.sampleLogin.main

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.common.Constants
import com.kcs.sampleLogin.common.Utils
import com.kcs.sampleLogin.join.JoinActivity
import com.kcs.sampleLogin.login.LoginActivity
import com.kcs.sampleLogin.module.UserRealmManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val useIdD = intent.getStringExtra(Constants.INTENT_DATA) as String
        txt_id.text = useIdD

        btn_logout.setOnClickListener({
            val realmManager = UserRealmManager()
            Utils.setIDData(this@MainActivity, "")
            Utils.setEMAILData(this@MainActivity, "")
            Utils.setPWDData(this@MainActivity, "")
            Utils.setAutoLogin(this@MainActivity, false)
            startActivity(LoginActivity.newIntent(this@MainActivity))

            finish()
        })

    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}