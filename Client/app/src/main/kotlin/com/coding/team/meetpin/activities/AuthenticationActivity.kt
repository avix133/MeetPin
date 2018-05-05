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
        var account= GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    override fun onClick(v:View){
        when(v.id){
            R.id.logInButton->
                signIn()
            R.id.logOutButton->
                signOut()
        }
    }

    private fun signIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, {
            val intent = Intent(applicationContext, AuthenticationActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "AuthenticationActivity")
            startActivity(intent)
        })
    }

    private fun revokeAccess(){
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this, OnCompleteListener {

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            var account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        }catch (e: ApiException){
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        val extras = intent.extras
        if (account==null){
            logInButton.visibility = View.VISIBLE
            logOutButton.visibility = View.GONE
        }else if(extras!=null && extras.getBoolean("LOGGED_IN")){
            logInButton.visibility = View.GONE
            logOutButton.visibility = View.VISIBLE
        }else{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("FROM_ACTIVITY", "AuthenticationActivity")
            intent.putExtra("LoggedIn", true)
            startActivity(intent)
        }
    }

}