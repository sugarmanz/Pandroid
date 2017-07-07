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

    fun doPartnerLogin() {
        if (partnerLoginCall == null) {
            Log.i(TAG, "Creating Call")
            val pandoraAPI = buildPandoraAPI().create(PandoraAPI::class.java)
            val requestModel = PartnerLoginRequest("android", "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7", "android-generic", "5")
            partnerLoginCall = pandoraAPI.attemptPOST("auth.partnerLogin", requestModel = requestModel)

            Log.i(TAG, "Making Call")
            partnerLoginCall?.enqueue(object : BasicCallback<ResponseModel>() {
                override fun handleSuccess(responseModel: ResponseModel) {
                    if (responseModel.isOk) {
                        Log.i(TAG, "Handling success")

                        val fugu = BlowFish()
                        val decoded = fugu.hexStringToByteArray(responseModel.result["syncTime"].toString())
                        var decrypted = fugu.decrypt(decoded)

                        decrypted = decrypted.copyOfRange(4, decrypted.size)
                        Log.i(TAG, String(decrypted))
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
