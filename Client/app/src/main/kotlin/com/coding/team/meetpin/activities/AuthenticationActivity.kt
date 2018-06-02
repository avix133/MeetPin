package com.coding.team.meetpin.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.coding.team.meetpin.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class AuthenticationActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private val RC_SIGN_IN = 9001
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var logInButton: Button
    private lateinit var logOutButton: Button


    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("coding", "onConnectionFailed $connectionResult")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        logInButton = findViewById(R.id.logInButton)
        logOutButton = findViewById(R.id.logOutButton)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        logInButton.setOnClickListener(this)

        logOutButton.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        println("In onStart -> Account email:" + account?.email.toString())
        if(account != null && intent.extras==null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "AuthenticationActivity")
            intent.putExtra("LoggedIn", true)
            startActivity(intent)
        }
        updateUI(account)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logInButton ->
                signIn()
            R.id.logOutButton ->
                signOut()
        }
    }

    private fun signIn() {
        println("In signIn")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, {
            updateUI(null)
        })
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this, OnCompleteListener {

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("In onActivityResult...")
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            println("In onActivityResult -> " + task.toString())
            println("Task success: "+ task.isSuccessful)
            handleSignInResult(task)
        } else {
            println("In onActivityResult -> Request code is: " + requestCode)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            println("In handleSignInResult")
            val account = completedTask.getResult(ApiException::class.java)
            println("In handleSignInResult, account = " + account.toString())
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "AuthenticationActivity")
            intent.putExtra("LoggedIn", true)
            startActivity(intent)
            updateUI(account)
        } catch (e: ApiException) {
            println("Api exception: ")
            e.printStackTrace()
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        val extras = intent.extras
        println("In updateUI")
        if (account == null ){
            println("In updateUI: Account is null")
            logInButton.visibility = View.VISIBLE
            logOutButton.visibility = View.GONE
        } else if (extras != null && extras.getBoolean("LOGGED_IN")) {
            println("In updateUI: LOGGED IN")
            logInButton.visibility = View.GONE
            logOutButton.visibility = View.VISIBLE
        } else {
            println("In updateUI: starting activity")
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "AuthenticationActivity")
            intent.putExtra("LoggedIn", true)
            startActivity(intent)
        }
    }

}