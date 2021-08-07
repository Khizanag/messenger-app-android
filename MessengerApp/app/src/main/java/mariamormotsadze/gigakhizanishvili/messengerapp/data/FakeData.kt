package mariamormotsadze.gigakhizanishvili.messengerapp.data

import android.content.Context
import mariamormotsadze.gigakhizanishvili.messengerapp.pages.search_users.FoundUserModel

class FakeData {

    companion object {
        // should be set during running of the application
        lateinit var context: Context

        fun getUser1() = UserModel(
            1,
            "ilia_chavchavadze",
            "i",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Ilia_Tchavtchavadze.jpg/500px-Ilia_Tchavtchavadze.jpg",
            "jurist, poet, novelist, humanist, publisher, philosopher",
        )

        fun getUser2() = UserModel(
            2,
            "galaktion_tabidze",
            "g",
            "https://upload.wikimedia.org/wikipedia/commons/e/e1/Galaktioni_1933.jpg",
            "poet"
        )

        fun getUser3() = UserModel(
            3,
            "shota_rustaveli",
            "s",
            "https://upload.wikimedia.org/wikipedia/commons/a/a4/Shota_rustaveli_qartuli.jpg",
            "poet, thinker, statesman, prince, treasurer"
        )

        fun getUser4() = UserModel(
            4,
            "vaja_pshavela",
            "v",
            "https://burusi.files.wordpress.com/2010/03/e18395e18390e1839fe18390-e183a4e183a8e18390e18395e18394e1839ae18390-e28093-vazha-pshavela.jpg",
            "Poet, short-story writer"
        )

        fun getUser5() = UserModel(
            5,
            "akaki_tsereteli",
            "a",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Georgian_poet_Akaki_Tsereteli%2C_c._early_1900s_in_Tbilisi%2C_full_image.jpg/500px-Georgian_poet_Akaki_Tsereteli%2C_c._early_1900s_in_Tbilisi%2C_full_image.jpg",
            "Poet"
        )

        fun getUsers() = listOf( getUser1(), getUser2(), getUser3(), getUser4(), getUser5(), )

        fun getFoundUsers(token: String) = getUsers().map { toFoundUser(it) }

        private fun toFoundUser(user: UserModel) = FoundUserModel(
            user.id, user.nickname, user.profession, user.imageUrl,
        )
    }

}
