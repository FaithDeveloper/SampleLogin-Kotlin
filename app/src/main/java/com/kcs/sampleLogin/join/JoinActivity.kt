package com.kcs.sampleLogin.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import android.widget.EditText
import android.widget.Toast
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.Utils
import com.kcs.sampleLogin.main.MainActivity
import kotlinx.android.synthetic.main.activity_join.*
import com.jakewharton.rxbinding2.widget.*

/**
 * Created by kcs on 2018. 4. 28..
 */
class JoinActivity : AppCompatActivity() {

    private lateinit var inputDataField : Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        init()
        setListener()
    }

    private fun init(){
        inputDataField = arrayOf(editID, editPWD, editPWDConfirm, editEmail)
        typingListener()
    }

    private fun setListener(){
        btnDone.setOnClickListener {
            if(checkEmpty()){
                Toast.makeText(this@JoinActivity, getString(R.string.error_join_field_empty), Toast.LENGTH_SHORT).show()
            }else{
//                val intent = Intent(this@JoinActivity, MainActivity::class.java)
//                startActivity(intent)

                if(inputDataField[1].text.toString() == inputDataField[2].text.toString()) {
                    Utils.setPWDData(this@JoinActivity, inputDataField[1].text.toString())
                }else{
                    Toast.makeText(this@JoinActivity, getString(R.string.error_do_not_same_pwd), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if(inputDataField[3].text.toString().contains("@")){
                    Utils.setEMAILData(this@JoinActivity, inputDataField[3].text.toString())
                }else{
                    Toast.makeText(this@JoinActivity, getString(R.string.error_discorrent_email), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Utils.setIDData(this@JoinActivity, inputDataField[0].text.toString())

                startActivity(MainActivity.newIntent(this@JoinActivity))
                finish()
            }
        }
    }

    private fun checkEmpty() : Boolean{
        for(dataField in inputDataField){
            if (dataField.text.toString().isEmpty()){
                return true
            }
        }
        return false
    }

    private fun typingListener(){
        RxTextView.textChanges(editID)
                .map { t -> t.length in 1 .. 8 }
                .subscribe({ it ->
                    if (it){
                        layoutInfoInputID.visibility = View.VISIBLE
                        txtInfoInputID.text = getString(R.string.txtInputInfoID)
                    }else{
                        layoutInfoInputID.visibility = View.GONE
                    }
                })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JoinActivity::class.java)
        }
    }

}