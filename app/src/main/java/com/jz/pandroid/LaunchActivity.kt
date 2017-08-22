package com.jz.pandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.jz.pandroid.request.BasicCallback
import com.jz.pandroid.request.Pandora
import com.jz.pandroid.crypt.BlowFish
import com.jz.pandroid.request.method.auth.PartnerLogin
import com.jz.pandroid.request.model.ResponseModel
import com.jz.pandroid.util.hexStringToByteArray
import retrofit2.Call


class LaunchActivity : AppCompatActivity() {

    val TAG = "LaunchActivity"
    var partnerLoginCall: Call<ResponseModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Preferences.reset()
        doPartnerLogin()
    }

    fun decryptSyncTime(raw: String): String {
        val fugu = BlowFish()
        val decoded = raw.hexStringToByteArray()
        var decrypted = fugu.decrypt(decoded)

        decrypted = decrypted.copyOfRange(4, decrypted.size)

        return String(decrypted, Charsets.UTF_8)
    }

    fun doPartnerLogin() {
        if (partnerLoginCall == null) {
            Log.i(TAG, "Creating Call")
            partnerLoginCall = Pandora().RequestBuilder(PartnerLogin.methodName)
                    .body(PartnerLogin.RequestBody())
                    .encrypted(false)
                    .build()

            Log.i(TAG, "Making Call")
            partnerLoginCall?.enqueue(object : BasicCallback<ResponseModel>() {
                override fun handleSuccess(responseModel: ResponseModel) {
                    if (responseModel.isOk) {
                        Log.i(TAG, "Handling success")
                        // Following Pithos impl. Differs from docs.
                        Preferences.syncTimeOffset = decryptSyncTime(responseModel.result["syncTime"].toString()).toLong() - (System.currentTimeMillis() / 1000L)
                        Preferences.partnerId = responseModel.result["partnerId"] as String?
                        Preferences.partnerAuthToken = responseModel.result["partnerAuthToken"] as String?
                        goToMain()
                    } else {
                        handleCommonError()
                    }
                }

                override fun handleConnectionError() {
                    Log.e(TAG, "Connection Error")
                }

                override fun handleStatusError(responseCode: Int) {
                    Log.e(TAG, responseCode.toString())
                }

                override fun handleCommonError() {
                    Log.e(TAG, "Common Error")
                }

                override fun onFinish() {
                    Log.i(TAG, "Call finished")
                    partnerLoginCall = null
                }

            })
        }
    }

    fun goToMain() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent) // should probs use no history
        finish()
    }
}
