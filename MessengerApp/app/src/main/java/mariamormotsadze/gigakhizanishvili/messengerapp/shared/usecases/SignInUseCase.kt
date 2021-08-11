package mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases

import mariamormotsadze.gigakhizanishvili.messengerapp.data.fake_data.FakeUserData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user.UserModel

object SignInUseCase {
    fun signIn(nickname: String, password: String): UserModel? {
        return FakeUserData.getUser1()
    }
}