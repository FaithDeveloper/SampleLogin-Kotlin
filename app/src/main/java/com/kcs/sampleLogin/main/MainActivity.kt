package com.kcs.sampleLogin.main

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.kcs.sampleLogin.LoginDetailActivity
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.Utils
import com.kcs.sampleLogin.common.Constants
import com.kcs.sampleLogin.join.JoinActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var inputDataField : Array<EditText> = arrayOf(editID, editPWD)

    var idData : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setListener()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private fun checkEmpty() : Boolean{
        for(dataField in inputDataField){
            if (dataField.text.isEmpty()){
                return true
            }
        }
        return false
    }

    private fun init(){
    }

    private fun setListener(){
        btnDone.setOnClickListener {
            if(checkEmpty()){
                Toast.makeText(this@MainActivity, getString(R.string.error_join_field_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//                val intent = Intent(this@JoinActivity, MainActivity::class.java)
//                startActivity(intent)

            if (checkSaveUser()){
                val intent = LoginDetailActivity.newIntent(this@MainActivity)
                intent.putExtra(Constants.INTENT_DATA, idData ?: "")
                startActivity(intent)
            }else{
                Toast.makeText(this@MainActivity, getString(R.string.error_fail_login), Toast.LENGTH_SHORT).show()
            }
        }

        btn_clear.setOnClickListener{
            Utils.setIDData(this@MainActivity, "")
            Utils.setEMAILData(this@MainActivity, "")
            Utils.setPWDData(this@MainActivity, "")
            startActivity(JoinActivity.newIntent(this@MainActivity))
            finish()
        }
    }

    private fun checkSaveUser() : Boolean{
        for (field in inputDataField){
            when(field.id){
                R.id.editID -> {
                    if(Utils.getIDData(this) != field.text.toString()){
                        return false
                    }else{
                        idData = field.text.toString()
                    }
                }
                R.id.editPWD ->
                    if(Utils.getPWDData(this) != field.text.toString()){
                        return false
                    }
             }
        }
        return true
    }
}
