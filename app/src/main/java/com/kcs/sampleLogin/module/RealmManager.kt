package com.kcs.sampleLogin.module

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel

/**
 * Created by kcs on 2018. 5. 27..
 */
class RealmManager(val name: String) {
    //Realm 초기화
    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder().name(name).build()
        Realm.getInstance(config)
    }

    /**
     * Realm에 저장된 모든 데이터 삭제
     * */
    fun clear(){
        val config = RealmConfiguration.Builder().name(name).build()
        if (config != null) {
            Realm.deleteRealm(config)
        }
    }

    /**
     * T로 받은 RealModel 데이터에서 key,value 값을 가진 데이터를 찾습니다
     */
    fun <T : RealmModel> find(key: String, value: String, targetDto: Class<T>): T? {
        return realm.where(targetDto).equalTo(value, key).findFirst()
    }
}