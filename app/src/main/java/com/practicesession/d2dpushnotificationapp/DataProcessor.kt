package com.practicesession.d2dpushnotificationapp

import android.content.Context
import android.content.SharedPreferences

class DataProcessor(context: Context) {

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object {

        private const val PREFS_NAME = "appname_prefs"
        private lateinit var sharedPreferences: SharedPreferences

        fun setInt(key: String, value: Int) {
            sharedPreferences.edit().putInt(key, value)?.apply()
        }

        fun getInt(key: String): Int {
            return sharedPreferences.getInt(key, 0)
        }

        fun setStr(key: String, value: String) {
            sharedPreferences.edit().putString(key, value).apply()
        }

        fun getStr(key: String): String {
            return if (sharedPreferences.contains(key))
                sharedPreferences.getString(key, "DNF")!!
            else
                "DNF"
        }

        fun setBool(key: String, value: Boolean) {
            sharedPreferences.edit().putBoolean(key, value).apply()
        }

        fun getBool(key: String): Boolean {
            return sharedPreferences.getBoolean(key, false)
        }

        fun getCurrentNotificationId(): Int {
            val NOTIFICATION_UPPER_LIMIT = 30000
            val NOTIFICATION_LOWER_LIMIT = 0
            val previousTokenId = getInt(Constants.NOTIFICATION_ID)
            val currentTokenId = previousTokenId + 1

            if (currentTokenId < NOTIFICATION_UPPER_LIMIT) {
                setInt(Constants.NOTIFICATION_ID, currentTokenId)
            } else {
                setInt(Constants.NOTIFICATION_ID, NOTIFICATION_LOWER_LIMIT)
            }

            return currentTokenId
        }
    }
}