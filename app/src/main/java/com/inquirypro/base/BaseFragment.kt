package com.inquirypro.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.inquirypro.R
abstract class BaseFragment(layoutResId: Int) : Fragment(layoutResId) {

    private val internetChangeReceiver = InternetChangeReceiver()
    private var noInternetView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackPressedCallback()
        registerInternetChangeReceiver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterInternetChangeReceiver()
        releaseNoInternetView()
    }

    private fun setupBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@BaseFragment.handleOnBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    open fun handleOnBackPressed() {}

    private fun registerInternetChangeReceiver() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(internetChangeReceiver, filter)
    }

    private fun unregisterInternetChangeReceiver() {
        requireActivity().unregisterReceiver(internetChangeReceiver)
    }

    private fun releaseNoInternetView() {
        noInternetView?.let {
            (view as? ViewGroup)?.removeView(it)
            noInternetView = null
        }
    }

    inner class InternetChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val isInternetEnabled = intent?.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                false
            )
            handleInternetConnectionChange(!isInternetEnabled!!)
        }

        private fun handleInternetConnectionChange(isConnected: Boolean) {
            if (isConnected) {
                hideNoInternetView()
            } else {
                showNoInternetView()
            }
        }

        private fun showNoInternetView() {
            noInternetView =
                layoutInflater.inflate(R.layout.fragment_no_internet, view as? ViewGroup, false)
            (view as? ViewGroup)?.addView(noInternetView)
        }

        private fun hideNoInternetView() {
            releaseNoInternetView()
        }
    }
}