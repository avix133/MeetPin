package com.coding.team.meetpin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.coding.team.meetpin.R

open class MenuActivity : AppCompatActivity() {

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.main_menu_with_map, menu)

            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            when (item?.getItemId()) {
                R.id.action_settings -> {
                    val intent = Intent(applicationContext, MyProfileActivity::class.java)
                    intent.putExtra("FROM_ACTIVITY", "MainActivity")
                    startActivity(intent)
                    return true
                }
                R.id.mapButton -> {
                    val intent = Intent(applicationContext, MapActivity::class.java)
                    intent.putExtra("FROM_ACTIVITY", "MainActivity")
                    startActivity(intent)
                    return true
                }
                R.id.friendsListButton -> {
                    val intent = Intent(applicationContext, FriendsListActivity::class.java)
                    intent.putExtra("FROM_ACTIVITY", "MainActivity")
                    startActivity(intent)
                    return true
                }
                R.id.inviteFriendButton -> {
                    val intent = Intent(applicationContext, InviteFriendsActivity::class.java)
                    intent.putExtra("FROM_ACTIVITY", "MainActivity")
                    startActivity(intent)
                    return true
                }
                R.id.logOutButton -> {
                    val intent = Intent(applicationContext, AuthenticationActivity::class.java)
                    intent.putExtra("FROM_ACTIVITY", "MainActivity")
                    intent.putExtra("LOGGED_IN", true)
                    startActivity(intent)
                    return true
                }
                else -> return super.onOptionsItemSelected(item)
            }
        }
}
