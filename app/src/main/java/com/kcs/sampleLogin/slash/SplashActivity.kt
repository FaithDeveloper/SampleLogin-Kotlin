package com.kcs.sampleLogin.slash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kcs.sampleLogin.main.MainActivity
import com.kcs.sampleLogin.Utils
import com.kcs.sampleLogin.join.JoinActivity

/**
 * Created by kcs on 2018. 4. 28..
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (Utils.getIDData(this).isEmpty()
                || Utils.getPWDData(this).isEmpty()
                || Utils.getEMAILData(this).isEmpty()){
             startActivity(JoinActivity.newIntent(this))
        }else{
            startActivity(MainActivity.newIntent(this))
        }
        finish()
    }
}