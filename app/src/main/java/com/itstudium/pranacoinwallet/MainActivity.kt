package com.itstudium.pranacoinwallet

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener
{
    private val homeFragment    : Fragment? = HomeFragment()
    private val historyFragment : Fragment? = HistoryFragment()
    private val sendFragment    : Fragment? = SendFragment()
    private val restoreFragment : Fragment? = RestoreFragment()
    private val backupFragment  : Fragment? = BackupFragment()
    
    private var fragment        : Fragment? = null
    
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        loadFragment(HomeFragment())

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
        
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.navigation_home    -> fragment = homeFragment
            R.id.navigation_history -> fragment = historyFragment
            R.id.navigation_send    -> fragment = sendFragment
            R.id.navigation_restore -> fragment = restoreFragment
            R.id.navigation_backup  -> fragment = backupFragment
        }
        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean
    {
        if (fragment != null)
        {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            return true
        }
        return false
    }
}