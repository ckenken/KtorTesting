package com.example.ktortesting.datamodel

import java.net.Proxy

interface ProxyProvider {
    fun getProxySettings(): Proxy?
}