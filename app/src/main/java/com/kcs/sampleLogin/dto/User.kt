package com.kcs.sampleLogin.dto

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by kcs on 2018. 5. 27..
 */
@RealmClass
open class User : RealmObject(){
    @PrimaryKey
    open var num:Long = 0
    open var id:String? = null
    open var email:String? = null
    open var password:String? = null
}