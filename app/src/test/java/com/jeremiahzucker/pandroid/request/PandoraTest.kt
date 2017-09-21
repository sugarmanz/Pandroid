package com.jeremiahzucker.pandroid.request

import com.jeremiahzucker.pandroid.request.method.auth.UserLogin
import org.junit.Assert
import org.junit.Test

/**
 * Created by sugarmanz on 9/19/17.
 */
class PandoraTest {

    @Test
    fun testMethodName() {
//        val hash = hashMapOf<Pandora.RequestBuilder, Int>()
        Assert.assertEquals(0, Pandora.inflightRequests.size)
        val r = Pandora.RequestBuilder("same").build<Any>()
//        hash.put(r, 1)

//        Assert.assertEquals(true, hash.contains(r))
        Assert.assertEquals(1, Pandora.inflightRequests.size)
        val s = Pandora.RequestBuilder("same").build<Any>()
//        Assert.assertEquals(true, hash.keys.contains(s))
//        Assert.assertEquals(1, hash.get(s))
//        Assert.assertEquals(true, hash.contains(s))
//        hash.
        Assert.assertEquals(1, Pandora.inflightRequests.size)
        val t = Pandora.RequestBuilder("Not the same").build<Any>()
        Assert.assertEquals(2, Pandora.inflightRequests.size)
    }

}