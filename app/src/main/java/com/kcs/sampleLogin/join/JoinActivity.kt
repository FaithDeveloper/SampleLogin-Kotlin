package com.kcs.sampleLogin.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.Utils
import com.kcs.sampleLogin.main.MainActivity
import kotlinx.android.synthetic.main.activity_join.*
import com.jakewharton.rxbinding2.widget.*
import java.util.regex.Pattern

/**
 * Created by kcs on 2018. 4. 28..
 */
class JoinActivity : AppCompatActivity() {

    private lateinit var inputDataField : Array<EditText>
    private lateinit var inputInfoLayoutField : Array<LinearLayout>
    private lateinit var inputInfoField : Array<TextView>
    //패스워드 정규식
    // 대문자,소문자, 숫자 또는 특수문자
//    private val passwordRules = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{6,20}\$"
    // 대문자, 소문자 숫자, 특수문자 최소 8자 - 최대 20자
    private val passwordRules = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}\$"
    //이메일 정규식
    private val emailRules = "^[a-z0-9_+.-]+@([a-z0-9-]+\\.)+[a-z0-9]{2,4}\$"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        init()
        setListener()
    }

    private fun init(){
        inputDataField = arrayOf(editID, editPWD, editPWDConfirm, editEmail)
        inputInfoLayoutField = arrayOf(layoutInfoInputID, layoutInfoInputPWD, layoutInfoInputRePWD, layoutInfoInputEmail)
        inputInfoField = arrayOf(txtInfoInputID, txtInfoInputPWD, txtInfoInputRePWD, txtInfoInputEmail)
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
        // ID
        RxTextView.textChanges(inputDataField[0])
                .map { t -> t.length in 1 .. 4 }
                .subscribe({ it ->
                    if (it){
                        layoutInfoInputID.visibility = View.VISIBLE
                        txtInfoInputID.text = getString(R.string.txtInputInfoID)
                    }else{
                        layoutInfoInputID.visibility = View.GONE
                    }
                })

        // Password
        for (i in 1 ..2) {
            RxTextView.textChanges(inputDataField[i])
                    .map { t -> t.isEmpty() ||  Pattern.matches(passwordRules, t)}
                    .subscribe({ it ->
                        if (it){
                            inputInfoLayoutField[i].visibility = View.GONE
                        }else{
                            inputInfoLayoutField[i].visibility = View.VISIBLE
                            inputInfoField[i].text = getString(R.string.txtInputInfoPWD)
                        }
                    })
        }

        //Email
        RxTextView.textChanges(inputDataField[3])
                .map { t -> t.isEmpty() || Pattern.matches(emailRules, t) }
                .subscribe({
                    if (it){
                        inputInfoLayoutField[3].visibility = View.GONE
                }else{
                        inputInfoLayoutField[3].visibility = View.VISIBLE
                        inputInfoField[3].text = getString(R.string.txtInputInfoEmail)
                }})

    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JoinActivity::class.java)
        }
    }

}