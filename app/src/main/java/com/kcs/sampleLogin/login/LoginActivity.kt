package com.kcs.sampleLogin.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kcs.sampleLogin.R
import com.kcs.sampleLogin.common.Constants
import com.kcs.sampleLogin.common.Utils
import com.kcs.sampleLogin.dto.User
import com.kcs.sampleLogin.join.JoinActivity
import com.kcs.sampleLogin.main.MainActivity
import com.kcs.sampleLogin.module.UserRealmManager
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by kcs on 2018. 5. 25..
 */
class LoginActivity  : AppCompatActivity() {
    lateinit var inputDataField : Array<EditText>

    var idData : String? = null
    var realmManager = UserRealmManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        initObservable()
        setListener()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private fun initObservable(){
        val observableId = RxTextView.textChanges(inputDataField[0])
                .map({ t -> !t.isEmpty()})
        val observablepwd = RxTextView.textChanges(inputDataField[1])
                .map({ t -> !t.isEmpty()})

        val combineLatestLoginEnable: io.reactivex.Observable<Boolean> = io.reactivex.Observable.combineLatest(observableId, observablepwd, BiFunction{ i, e -> i && e})
        combineLatestLoginEnable.distinctUntilChanged()
                .subscribe{enable ->
                    btnDone.isEnabled = enable
                    when(enable) {
                        true -> {
                            btnDone.setBackgroundColor(resources.getColor(R.color.enableButton))
                        }
                        false -> {
                            btnDone.setBackgroundColor(resources.getColor(R.color.disableButton))
                        }
                    }
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
        btnJoin.setOnClickListener({
            startActivity<JoinActivity>()
        })
        btnDone.setOnClickListener {
            if(checkEmpty()){
                toast(R.string.error_join_field_empty)
//                Toast.makeText(this@LoginActivity, getString(R.string.error_join_field_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//                val intent = Intent(this@JoinActivity, MainActivity::class.java)
//                startActivity(intent)

            if (checkSaveUser()){
                val intent = MainActivity.newIntent(this@LoginActivity)
                intent.putExtra(Constants.INTENT_DATA, idData ?: "")
                Utils.setIDData(this@LoginActivity, inputDataField[0].text.toString())
                Utils.setPWDData(this@LoginActivity, inputDataField[1].text.toString())
                startActivity(intent)
                finish()
                false
            }else{
                Toast.makeText(this@LoginActivity, getString(R.string.error_fail_login), Toast.LENGTH_SHORT).show()
            }
        }

        btn_clear.setOnClickListener{
            realmManager.clear()
            Utils.setIDData(this@LoginActivity, "")
            Utils.setEMAILData(this@LoginActivity, "")
            Utils.setPWDData(this@LoginActivity, "")
            startActivity(JoinActivity.newIntent(this@LoginActivity))
            finish()
        }

        switch_auto_login.isChecked = Utils.getAutoLogin(this@LoginActivity)
        switch_auto_login.setOnClickListener({
            Utils.setAutoLogin(this@LoginActivity, switch_auto_login.isChecked)
        })
    }

    private fun checkSaveUser() : Boolean{

        val userData = realmManager.find(inputDataField[0].text.toString(), "id", User::class.java)

        if(userData == null){
            Log.d(Constants.LOG_TEST, "User Realm Data Null!!")
            return false
        }


        for (field in inputDataField){
            when(field.id){
                R.id.editID -> {
                    if(userData.id != field.text.toString()){
                        return false
                    }else{
                        idData = field.text.toString()
                    }
                }
                R.id.editPWD ->
                    if(userData.password != field.text.toString()){
                        return false
                    }
            }
        }
        return true
    }
}
