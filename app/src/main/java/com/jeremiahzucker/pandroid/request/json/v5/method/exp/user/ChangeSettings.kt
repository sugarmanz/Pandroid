package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.google.gson.annotations.Expose
import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.Account
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-changesettings
 */
object ChangeSettings : BaseMethod() {
    data class RequestBody(
        val currentUsername: String,
        val currentPassword: String,
        val userInitiatedChange: Boolean? = null,
        val includeFacebook: Boolean? = null,

        @Expose(serialize = false, deserialize = false)
        val genderType: Account.Gender? = null,
        val birthYear: Int? = null,
        val zipCode: String? = null,
        val isProfilePrivate: Boolean? = null,
        val enableComments: Boolean? = null,
        val emailOptIn: Boolean? = null,
        val emailComments: Boolean? = null,
        val emailNewFollowers: Boolean? = null,
        val isExplicitContentFilterEnabled: Boolean? = null,
        val isExplicitContentFilterPINProtected: Boolean? = null,
        val newUsername: String? = null,
        val newPassword: String? = null,
        val facebookAutoShareEnabled: Boolean? = null,
        val autoShareTrackPlay: Boolean? = null,
        val autoShareLikes: Boolean? = null,
        val autoShareFollows: Boolean? = null,
        val facebookSettingChecksum: Boolean? = null
    ) : SyncTokenRequestBody(TokenType.USER) {
        val gender: String? = genderType?.getProperName()
    }
}
