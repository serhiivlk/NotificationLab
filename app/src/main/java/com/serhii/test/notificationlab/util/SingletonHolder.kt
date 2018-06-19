package com.serhii.test.notificationstest.util

open class SingletonHolder<out T, in A>(private var creator: ((A) -> T)?) {
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i1 = instance
        if (i1 != null) {
            return i1
        }
        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}