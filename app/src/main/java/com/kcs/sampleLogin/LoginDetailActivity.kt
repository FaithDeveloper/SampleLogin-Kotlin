package com.kcs.sampleLogin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kcs.sampleLogin.common.Constants
import kotlinx.android.synthetic.main.activity_login_detail.*

/**
 * Created by kcs on 2018. 4. 28..
 */
class LoginDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_detail)

        val useIdD = intent.getStringExtra(Constants.INTENT_DATA) as String
        txt_id.text = useIdD


    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginDetailActivity::class.java)
        }
    }

}