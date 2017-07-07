package com.jz.pandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.jz.pandroid.request.BasicCallback
import com.jz.pandroid.request.PandoraAPI
import com.jz.pandroid.request.buildPandoraAPI
import com.jz.pandroid.request.crypt.BlowFish
import com.jz.pandroid.request.model.PartnerLoginRequest
import com.jz.pandroid.request.model.ResponseModel
import retrofit2.Call


class LaunchActivity : AppCompatActivity() {

    val TAG = "LaunchActivity"
    var partnerLoginCall: Call<ResponseModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doPartnerLogin()
    }

    fun decryptSyncTime(raw: String): String {
        val fugu = BlowFish()
        val decoded = fugu.hexStringToByteArray(raw)
        var decrypted = fugu.decrypt(decoded)

        decrypted = decrypted.copyOfRange(4, decrypted.size)

        return String(decrypted, Charsets.UTF_8)
    }

    fun doPartnerLogin() {
        if (partnerLoginCall == null) {
            Log.i(TAG, "Creating Call")
            val pandoraAPI = buildPandoraAPI().create(PandoraAPI::class.java)
            val requestModel = PartnerLoginRequest(
                    PartnerLogin.partnerUsername,
                    PartnerLogin.partnerPassword,
                    PartnerLogin.deviceType,
                    PartnerLogin.version
            )
            partnerLoginCall = pandoraAPI.attemptPOST(PartnerLogin.methodName, requestModel = requestModel)

            Log.i(TAG, "Making Call")
            partnerLoginCall?.enqueue(object : BasicCallback<ResponseModel>() {
                override fun handleSuccess(responseModel: ResponseModel) {
                    if (responseModel.isOk) {
                        Log.i(TAG, "Handling success")

                        Preferences.syncTime = decryptSyncTime(responseModel.result["syncTime"].toString())
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
