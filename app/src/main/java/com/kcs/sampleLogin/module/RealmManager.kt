package com.kcs.sampleLogin.module

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel

/**
 * Created by kcs on 2018. 5. 27..
 *
 * ref :
 * https://github.com/boarderYuki/AOS_study_0001/blob/master/app/src/main/java/io/indexpath/study_001/JoinActivity.kt
 * https://github.com/Developer-Leby/LoginSample-Kotlin/blob/master/app/src/main/java/com/example/lebyykim/realm/AccountRealmManager.kt
 * https://github.com/realm/realm-java/blob/master/examples/kotlinExample/src/main/kotlin/io/realm/examples/kotlin/KotlinExampleActivity.kt
 * https://realm.io/docs/java/latest/?_ga=2.172749477.769786165.1527428973-973285666.1523330369#realms
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