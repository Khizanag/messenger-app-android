package mariamormotsadze.gigakhizanishvili.messengerapp.pages.home_page.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mariamormotsadze.gigakhizanishvili.messengerapp.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        init()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun init(){}
}