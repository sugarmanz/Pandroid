package com.jz.pandroid.request.method.exp.user

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-getsettings
 */
object GetSettings: Method() {
    data class RequestBody(
            val includeFacebook: Boolean
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val gender: String,
            val birthYear: Int,
            val zipCode: String,
            val isProfilePrivate: Boolean,
            val enableComments: Boolean,
            val emailOptIn: Boolean,
            val emailComments: Boolean,
            val emailNewFollowers: Boolean,
            val isExplicitContentFilterEnabled: Boolean,
            val isExplicitContentFilterPINProtected: Boolean,
            val newUsername: String,
            val newPassword: String,
            val facebookAutoShareEnabled: Boolean,
            val autoShareTrackPlay: Boolean,
            val autoShareLikes: Boolean,
            val autoShareFollows: Boolean,
            val facebookSettingChecksum: Boolean
    )
}