package com.inquirypro.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.inquirypro.R
import com.inquirypro.model.User

abstract class BaseAuthFragment(layoutResId: Int) : BaseFragment(layoutResId) {


    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 9001
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            GoogleSignIn.getSignedInAccountFromIntent(data)

        }
    }

    open fun signInIntent() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    open fun getSignInUser(): Pair<User?, String?> {
        val lastSignInAccount = GoogleSignIn.getLastSignedInAccount(requireActivity())

        return if (lastSignInAccount != null) {
            val user = User(
                accountId = lastSignInAccount.id,
                email = lastSignInAccount.email,
                firstName = lastSignInAccount.givenName,
                lastName = lastSignInAccount.familyName
            )
            Pair(user, null)
        } else {
            Pair(null, "Google Sign-In failed")
        }
    }
}