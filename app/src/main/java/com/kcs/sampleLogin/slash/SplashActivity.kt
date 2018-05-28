package com.kcs.sampleLogin.slash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.main.MainActivity
import com.kcs.sampleLogin.common.Utils
import com.kcs.sampleLogin.join.JoinActivity
import com.kcs.sampleLogin.login.LoginActivity

/**
 * Created by kcs on 2018. 4. 28..
 */
class SplashActivity : AppCompatActivity() {
    val userID : String by lazy {
        Utils.getIDData(this)
    }
    val userPwd : String by lazy {
        Utils.getPWDData(this)
    }
    val userEmail : String by lazy {
        Utils.getEMAILData(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ if (userID.isEmpty()
                || userPwd.isEmpty()
                || userEmail.isEmpty()){
            startActivity(JoinActivity.newIntent(this))
        }else{
            startActivity(LoginActivity.newIntent(this))
        }
            finish()}, 800)
    }

}