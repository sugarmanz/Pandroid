package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-getsettings
 */
object GetSettings: BaseMethod() {
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