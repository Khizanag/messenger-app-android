package mariamormotsadze.gigakhizanishvili.messengerapp.shared.usecases

import mariamormotsadze.gigakhizanishvili.messengerapp.data.FakeData
import mariamormotsadze.gigakhizanishvili.messengerapp.data.UserModel

class LoginUseCase {
    companion object {

        fun login(nickname: String, password: String): UserModel? {
            return FakeData.getUser1()
        }
    }
}