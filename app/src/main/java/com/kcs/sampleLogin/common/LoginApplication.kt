package com.kcs.sampleLogin.common

import android.app.Application
import io.realm.Realm

/**
 * Created by kcs on 2018. 5. 27..
 */
class LoginApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}