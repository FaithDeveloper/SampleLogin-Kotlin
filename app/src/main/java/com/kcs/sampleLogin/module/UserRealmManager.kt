package com.kcs.sampleLogin.module

import com.kcs.sampleLogin.dto.User
import io.realm.RealmModel

/**
 * Created by kcs on 2018. 5. 29..
 */
class UserRealmManager : RealmManager("User.realm") {
    fun <T: RealmModel, E: User>insertUser(targetDto: Class<T>, dto: E){

        realm.beginTransaction()

        //PrimaryKey 증가해서 넣어주는 것이 중요!!
        var nextNum : Long = realm.where(targetDto).count() +1
        val account = realm.createObject(targetDto, nextNum)
        if(account is User){
            account.id = dto.id
            account.password = dto.password
            account.email = dto.email
        }



        realm.commitTransaction()
    }
}