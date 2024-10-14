package domain

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.rpc.RPCClient
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json

suspend fun initRpcClient(): RPCClient {
    return HttpClient {
        installRPC()
    }.rpc {
        url("ws://localhost:8080/rpc")

        rpcConfig {
            serialization {
                json()
            }
        }
    }
}