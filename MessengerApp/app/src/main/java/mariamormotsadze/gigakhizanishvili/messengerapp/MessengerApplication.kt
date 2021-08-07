package mariamormotsadze.gigakhizanishvili.messengerapp

import android.app.Application
import mariamormotsadze.gigakhizanishvili.messengerapp.data.FakeData

class MessengerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FakeData.context = this
    }

}