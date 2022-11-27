package com.example.ktortesting.view

import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.ktortesting.R
import com.example.ktortesting.databinding.ActivitySimpleBinding
import com.example.ktortesting.datamodel.CustomPlugin
import com.example.ktortesting.struct.SchoolResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.serialization.json.Json
import java.io.File


class SimpleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleBinding

    val client: HttpClient = HttpClient(CIO) {
        install(WebSockets) {

        }

        install(HttpTimeout) {
            requestTimeoutMillis = 600000
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("ckenken", "message = $message")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivitySimpleBinding.inflate(
            layoutInflater,
            findViewById(android.R.id.content),
            false,
        ).also {
            binding = it
            setContentView(it.root)
        }

        binding.retryButton.isVisible = false
        binding.resultImage.setOnClickListener {
            foo()
        }
    }

    override fun onStart() {
        super.onStart()
//        foo()
//        startSocket()
//        downloadFile("https://miro.medium.com/fit/c/96/96/1*-fqqVleaCFx8YMjhgf973w.jpeg")
//        downloadFile("http://sddl.b9good.com/2022/10/pocket130.mp4")
    }

    fun downloadFile(url: String) {
        lifecycleScope.launch {
            val file = File("${this@SimpleActivity.cacheDir}${File.separator}temp4.mp4")

            client.get(url)
            client.request(url)

            client.prepareGet(url).execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.body()
                while (channel.isClosedForRead.not()) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.isEmpty) {
                        val bytes = packet.readBytes()
                        file.appendBytes(bytes)
                    }
                }
                Log.d("ckenken", "A file saved to ${file.path}")
            }

            val response = client.get(url)

            val channel: ByteReadChannel = response.body()
            while (channel.isClosedForRead.not()) {
                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()
                    file.appendBytes(bytes)
                    withContext(Dispatchers.Main) {
                        Log.d(
                            "ckenken",
                            "((file.length().toDouble() / response.contentLength()!!.toDouble()) * 100) = ${
                                ((file.length().toDouble() / response.contentLength()!!
                                    .toDouble()) * 100)
                            }"
                        )
                        binding.progressBar.progress =
                            ((file.length().toDouble() / response.contentLength()!!
                                .toDouble()) * 100).toInt()
                    }
                }
            }

            withContext(Dispatchers.Main) {
                binding.resultVideo.setOnPreparedListener { mp ->
                    mp.start()
                }
                binding.resultVideo.setVideoURI(file.toUri())
            }
        }
    }


    fun startSocket() {
        val sendChannel = Channel<String>()
        val webSocketConnectionJob = lifecycleScope.launch {
            client.webSocket(
                method = HttpMethod.Post,
                host = "10.14.4.33",
                port = 10000,
                path = "/server.php",
            ) {
                launch {
                    for (message in incoming) {
                        message as? Frame.Text ?: continue
                        //Received message
                    }
                }
                launch {
                    while (true) {
                        val message = sendChannel.receive()
                        // Send new message
                        send(message)
                    }
                }
            }
        }
        lifecycleScope.launch {
            // Whenever you want to close connection
            webSocketConnectionJob.cancel()
        }
    }

    fun foo() {
        lifecycleScope.launch {
            val client = HttpClient(CIO) {
                expectSuccess = true
                install(DefaultRequest) {
                    url("https://api.dtto.com/")
                }

                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d("ckenken", "message = $message")
                        }
                    }
                }

                install(CustomPlugin)

                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    })
                }

                install(HttpRequestRetry) {
                    this.maxRetries = 5
                    retryIf { httpRequest, httpResponse ->
                        httpResponse.status.isSuccess().not()
                    }
                    retryOnExceptionIf { httpRequestBuilder, throwable ->
                        throwable is HttpRequestTimeoutException
                    }
                    delayMillis { retry ->
                        retry * 3000L
                    } // retries in 3, 6, 9, etc. seconds

                    // Retry conditions
                    modifyRequest { request ->
                        request.headers.append("x-retry-count", retryCount.toString())
                    }
                }
            }

            client.plugin(HttpSend).intercept { request ->
                Log.d("ckenken", "外部註冊 interceptor")
                execute(request)
            }

            val country = "JP"
//            val response = client.get(
//                "v3/countries/$country/schools"
//            ) {
//                /* url config setting */
//            }.body<SchoolResponse>()
//            Log.d("ckenken", "response = $response")
            val response = client.get(
                "v3/countries/$country/schools"
            ) {

            }
        }
    }
}