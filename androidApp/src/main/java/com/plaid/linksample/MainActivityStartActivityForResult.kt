/*
 * Copyright (c) 2020 Plaid Technologies, Inc. <support@plaid.com>
 */

package com.plaid.linksample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.plaid.link.Plaid
import com.plaid.link.PlaidHandler
import com.plaid.link.configuration.LinkTokenConfiguration
import com.plaid.link.result.LinkResultHandler
import com.plaid.linksample.network.LinkTokenRequester

/**
 * Old approach to opening Plaid Link, we recommend switching over to the
 * OpenPlaidLink ActivityResultContract instead.
 */
class MainActivityStartActivityForResult : AppCompatActivity() {

  private lateinit var result: TextView
  private lateinit var tokenResult: TextView
  private lateinit var prepareButton: MaterialButton
  private lateinit var openButton: MaterialButton
  private var plaidHandler: PlaidHandler? = null

  private val myPlaidResultHandler by lazy {
    LinkResultHandler(
      onSuccess = {
        tokenResult.text = getString(R.string.public_token_result, it.publicToken)
        result.text = getString(R.string.content_success)
      },
      onExit = {
        tokenResult.text = ""
        if (it.error != null) {
          result.text = getString(
            R.string.content_exit,
            it.error?.displayMessage,
            it.error?.errorCode
          )
        } else {
          result.text = getString(
            R.string.content_cancel,
            it.metadata.status?.jsonValue ?: "unknown"
          )
        }
      }
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    result = findViewById(R.id.result)
    tokenResult = findViewById(R.id.public_token_result)

    prepareButton = findViewById(R.id.prepare_link)
    prepareButton.setOnClickListener {
      setOptionalEventListener()
      prepareLink()
    }

    openButton = findViewById(R.id.open_link)
    openButton.setOnClickListener {
      setOptionalEventListener()
      openLink()
    }
  }

  private fun prepareLink() {
    LinkTokenRequester.token.subscribe(::onLinkTokenSuccess, ::onLinkTokenError)
  }
  /**
   * Optional, set an [event listener](https://plaid.com/docs/link/android/#handling-onevent).
   */
  private fun setOptionalEventListener() = Plaid.setLinkEventListener { event ->
    Log.i("Event", event.toString())
  }

  /**
   * For all Link configuration options, have a look at the
   * [parameter reference](https://plaid.com/docs/link/android/#parameter-reference).
   */
  private fun openLink() {
    prepareButton.isEnabled = true
    openButton.isEnabled = false
    plaidHandler?.open(this)
  }

  private fun onLinkTokenSuccess(linkToken: String) {
    val tokenConfiguration = LinkTokenConfiguration.Builder()
      .token(linkToken)
      .build()
    plaidHandler = Plaid.create(
      this.application,
      tokenConfiguration
    )
    prepareButton.isEnabled = false
    openButton.isEnabled = true
  }

  private fun onLinkTokenError(error: Throwable) {
    if (error is java.net.ConnectException) {
      Toast.makeText(this, "Please run `sh start_server.sh <client_id> <sandbox_secret>`", Toast.LENGTH_LONG).show()
      return
    }
    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
  }

  @Suppress("DEPRECATION")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, intent)
    if (!myPlaidResultHandler.onActivityResult(requestCode, resultCode, data)) {
      Log.i(MainActivity::class.java.simpleName, "Not handled")
    }
  }

}
