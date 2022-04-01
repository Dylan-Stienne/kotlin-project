package fr.iim.authapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), LoginFormFragment.Listener, LoggedFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onLogin(email: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentDisplayed, LoggedFragment.newInstance(email))
            .commitNow()
    }

    override fun onSearch(city: String) {
        val intent = Intent(this, MapsActivity::class.java).apply {
            this.putExtra(MapsActivity.EXTRA_CITY, city)
        }
        startActivity(intent)
    }
}