package com.shigure.lastfm_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.core.KoinExperimentalAPI

@KoinExperimentalAPI
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        setContentView(R.layout.activity_launch)
    }
}