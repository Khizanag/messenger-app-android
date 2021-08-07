package mariamormotsadze.gigakhizanishvili.messengerapp.data

import android.content.Context
import mariamormotsadze.gigakhizanishvili.messengerapp.R

class FakeData {

    companion object {
        // should be set during running of the application
        lateinit var context: Context

        fun getUser1() = UserModel(
            1,
            "khizanag",
            "paroli",
            "https://scontent.ftbs2-1.fna.fbcdn.net/v/t1.6435-9/128471377_106458924644707_9154725346307266401_n.jpg?_nc_cat=104&ccb=1-4&_nc_sid=09cbfe&_nc_ohc=GIw6kaW87LEAX8uvz20&_nc_ht=scontent.ftbs2-1.fna&oh=08b087b775ab0418854c6e73f7b95cb8&oe=6133CE56",
            "iOS Developer",
        )

        fun getUser2() = UserModel(
            2,
            "Mariam Ormotsadze",
            "paroli",
            "placeholder",
            "Nothing in android project"
        )

        fun getUser3() = UserModel(
            3,
            "Davit Aghmashenebeli",
            "paroli",
            "placeholder",
            "Building Georgia"
        )
    }

}
