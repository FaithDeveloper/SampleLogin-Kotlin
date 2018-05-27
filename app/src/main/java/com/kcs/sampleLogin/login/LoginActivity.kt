package com.kcs.sampleLogin.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.common.Constants
import com.kcs.sampleLogin.common.Utils
import com.kcs.sampleLogin.join.JoinActivity
import com.kcs.sampleLogin.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by kcs on 2018. 5. 25..
 */
class LoginActivity  : AppCompatActivity() {
    lateinit var inputDataField : Array<EditText>

    var idData : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        setListener()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
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
        inputDataField = arrayOf(editID, editPWD)
    }

    private fun setListener(){
        btnDone.setOnClickListener {
            if(checkEmpty()){
                Toast.makeText(this@LoginActivity, getString(R.string.error_join_field_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//                val intent = Intent(this@JoinActivity, MainActivity::class.java)
//                startActivity(intent)

            if (checkSaveUser()){
                val intent = MainActivity.newIntent(this@LoginActivity)
                intent.putExtra(Constants.INTENT_DATA, idData ?: "")
                startActivity(intent)
            }else{
                Toast.makeText(this@LoginActivity, getString(R.string.error_fail_login), Toast.LENGTH_SHORT).show()
            }
        }

        btn_clear.setOnClickListener{
            Utils.setIDData(this@LoginActivity, "")
            Utils.setEMAILData(this@LoginActivity, "")
            Utils.setPWDData(this@LoginActivity, "")
            startActivity(JoinActivity.newIntent(this@LoginActivity))
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