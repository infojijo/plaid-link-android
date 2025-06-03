package com.plaid.linksample.network

/**
 * API calls to our localhost token server.
 */
interface LinkSampleApi {
  suspend fun getLinkToken(): LinkToken
}

data class LinkToken(
  val link_token: String
)
