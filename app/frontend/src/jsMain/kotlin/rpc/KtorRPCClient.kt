/*
 * Copyright 2023-2024 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.rpc.krpc.ktor.client

import dev.limebeck.todo.rpc.KtorTransport
import io.ktor.websocket.*
import kotlinx.rpc.RPCClient
import kotlinx.rpc.internal.utils.InternalRPCApi
import kotlinx.rpc.krpc.RPCConfig
import kotlinx.rpc.krpc.client.KRPCClient

/**
 * [RPCClient] implementation for Ktor, containing [webSocketSession] object,
 * that is used to maintain connection.
 */
public interface KtorRPCClient : RPCClient {
    public val webSocketSession: WebSocketSession
}

@OptIn(InternalRPCApi::class)
internal class KtorRPCClientImpl(
    override val webSocketSession: WebSocketSession,
    config: RPCConfig.Client,
) : KRPCClient(config, KtorTransport(webSocketSession)), KtorRPCClient

