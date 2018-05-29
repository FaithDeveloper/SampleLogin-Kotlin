package com.kcs.sampleLogin.slash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.common.Constants
import com.kcs.sampleLogin.main.MainActivity
import com.kcs.sampleLogin.common.Utils
import com.kcs.sampleLogin.dto.User
import com.kcs.sampleLogin.join.JoinActivity
import com.kcs.sampleLogin.login.LoginActivity
import com.kcs.sampleLogin.module.UserRealmManager

/**
 * Created by kcs on 2018. 4. 28..
 */
class SplashActivity : AppCompatActivity() {
    var realmManager = UserRealmManager()

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

        Handler().postDelayed({
            if (userID.isEmpty()
                || userPwd.isEmpty()
                || userEmail.isEmpty()){
              startActivity(JoinActivity.newIntent(this))
                finish()
                return@postDelayed
            }
            if(Utils.getAutoLogin(this)){
                val user = realmManager.find(userID, "id", User::class.java)
                if (user?.id == userID && user?.password == userPwd){
                    val intent = MainActivity.newIntent(this@SplashActivity)
                    intent.putExtra(Constants.INTENT_DATA, userID)
                    startActivity(intent)
                    finish()
                    return@postDelayed
                }
            }

            startActivity(LoginActivity.newIntent(this))
            finish()
        }, 800)
    }

}