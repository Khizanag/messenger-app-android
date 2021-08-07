package mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases

import mariamormotsadze.gigakhizanishvili.messengerapp.data.FakeData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.UserModel

object SignInUseCase {
    fun signIn(nickname: String, password: String): UserModel? {
        return FakeData.getUser1()
    }
}