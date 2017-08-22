package com.jz.pandroid.request.method.auth

import org.junit.Assert
import org.junit.Test

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 */
class UserLoginTest {

    @Test
    fun testMethodName() {
        Assert.assertEquals("auth.userLogin", UserLogin.methodName)
    }

}