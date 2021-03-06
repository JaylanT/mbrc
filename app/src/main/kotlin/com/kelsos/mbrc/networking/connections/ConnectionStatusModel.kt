package com.kelsos.mbrc.networking.connections

import com.kelsos.mbrc.events.ConnectionStatusChangeEvent
import com.kelsos.mbrc.events.RequestConnectionStateEvent
import com.kelsos.mbrc.events.bus.RxBus
import com.kelsos.mbrc.networking.SocketStatusChangedEvent
import com.kelsos.mbrc.networking.connections.Connection.Status
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionStatusModel
@Inject
constructor(private val bus: RxBus) {

  private var onConnectedListener: OnConnectedListener? = null

  fun setOnConnectedListener(onConnectedListener: OnConnectedListener?) {
    this.onConnectedListener = onConnectedListener
  }

  var connected: Boolean = false
    private set(value) {
      field = value
      if (!field) {
        handshake = false
      } else {
        onConnectedListener?.invoke()
      }
    }
  var handshake: Boolean = false
    get
    set(value) {
      field = value
      notifyState()
    }

  init {
    connected = false
    handshake = false
    this.bus.register(this, RequestConnectionStateEvent::class.java, { notifyState() })
    this.bus.register(this, SocketStatusChangedEvent::class.java, { connected = it.connected })
  }

  val connection: Int
    @Status
    get() {
      when {
        connected && handshake -> return Connection.ACTIVE
        connected -> return Connection.ON
        else -> return Connection.OFF
      }
    }

  fun disconnected() {
    this.connected = false
    this.handshake = false
    notifyState()
  }

  private fun notifyState() {
    bus.post(ConnectionStatusChangeEvent(connection))
  }
}

typealias OnConnectedListener = () -> Unit