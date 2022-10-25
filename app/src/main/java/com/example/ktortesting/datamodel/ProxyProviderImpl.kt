package com.example.ktortesting.datamodel

import java.net.InetSocketAddress
import java.net.Proxy

object ProxyProviderImpl : ProxyProvider {
    override fun getProxySettings(): Proxy? {
        val proxyHost = System.getProperty("http.proxyHost")
        val proxyPort = System.getProperty("http.proxyPort")?.toIntOrNull()
        return if (!proxyHost.isNullOrEmpty() && proxyPort != null) {
            Proxy(Proxy.Type.HTTP, InetSocketAddress(proxyHost, proxyPort))
        } else null
    }
}