package com.kcs.sampleLogin

import android.content.Context

/**
 * Created by kcs on 2018. 4. 28..
 */
class Utils {
    companion object {
        val INTENT_ID_DATA = "com.kcs.weektest001.utils.id"
        val INTENT_PWD_DATA = "com.kcs.weektest001.utils.pwd"
        val INTENT_EMAIL_DATA = "com.kcs.weektest001.utils.email"


        fun setIDData(ctx: Context, value : String){
            val preferences = ctx.getSharedPreferences(INTENT_ID_DATA, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(INTENT_ID_DATA, value)
            editor.commit()
        }

        fun getIDData(ctx: Context) : String{
            val preferences = ctx.getSharedPreferences(INTENT_ID_DATA, Context.MODE_PRIVATE)
            return preferences.getString(INTENT_ID_DATA, "")
        }

        fun setPWDData(ctx: Context, value : String){
            val preferences = ctx.getSharedPreferences(INTENT_PWD_DATA, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(INTENT_PWD_DATA, value)
            editor.commit()
        }

        fun getPWDData(ctx: Context) : String{
            val preferences = ctx.getSharedPreferences(INTENT_PWD_DATA, Context.MODE_PRIVATE)
            return preferences.getString(INTENT_PWD_DATA, "")
        }

        fun setEMAILData(ctx: Context, value : String){
            val preferences = ctx.getSharedPreferences(INTENT_EMAIL_DATA, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(INTENT_EMAIL_DATA, value)
            editor.commit()
        }

        fun getEMAILData(ctx: Context) : String{
            val preferences = ctx.getSharedPreferences(INTENT_EMAIL_DATA, Context.MODE_PRIVATE)
            return preferences.getString(INTENT_EMAIL_DATA, "")
        }
    }
}