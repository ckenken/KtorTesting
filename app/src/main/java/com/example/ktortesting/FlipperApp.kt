package com.example.ktortesting

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.OkHttpClient

interface IFlipper {
    fun init(app: Application)
    fun initFlipperNetwork(flipperClient: FlipperClient)
    fun addNetworkInterceptor(builder: OkHttpClient.Builder)
}

object FlipperApp: IFlipper {
    val networkFlipperPlugin = NetworkFlipperPlugin()
    override fun init(app: Application) {
        SoLoader.init(app, false)
//        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(app)) {
            val client: FlipperClient = AndroidFlipperClient.getInstance(app)
            client.addPlugin(InspectorFlipperPlugin(app, DescriptorMapping.withDefaults()))
            initFlipperNetwork(client)
            client.start()
//        }
    }

    override fun initFlipperNetwork(flipperClient: FlipperClient) {
        flipperClient.addPlugin(networkFlipperPlugin)
    }

    override fun addNetworkInterceptor(builder: OkHttpClient.Builder) {
        builder.addInterceptor(FlipperOkhttpInterceptor(FlipperApp.networkFlipperPlugin, true))
    }
}
