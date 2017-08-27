package com.jeremiahzucker.pandroid.request.model

/**
 * Created by Jeremiah Zucker on 8/25/2017.
 */
object Account {

    enum class Gender {
        MALE,
        FEMALE;

        fun getProperName(): String {
            val sub = this.name.substring(1)
            return this.name.replace(sub, sub.toLowerCase())
        }
    }

}