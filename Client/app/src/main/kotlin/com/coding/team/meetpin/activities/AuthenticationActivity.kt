package com.coding.team.meetpin.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.coding.team.meetpin.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status

class AuthenticationActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null

    private var btnLogin: Button? = null
    private var btnLogout: Button? = null


    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("coding", "onConnectionFailed $connectionResult")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        btnLogin = findViewById(R.id.btnLogin)
//        btnLogout = findViewById(R.id.btnLogout)

        updateUI(false)
//Sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        btnLogin?.setOnClickListener(View.OnClickListener {
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })
//Sign out
        btnLogout?.setOnClickListener(View.OnClickListener {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    object : ResultCallback<Status> {
                        override fun onResult(status: Status) {
                            updateUI(status.isSuccess)
                        }
                    }
            )
        })

    }

    //Sign in
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            updateUI(result.isSuccess)
        }

    }

    private fun updateUI(isLogin: Boolean) {
        if (isLogin) {
            btnLogin?.visibility = View.GONE
            btnLogout?.visibility = View.VISIBLE
        } else {
            btnLogin?.visibility = View.VISIBLE
            btnLogout?.visibility = View.GONE
        }
    }
}