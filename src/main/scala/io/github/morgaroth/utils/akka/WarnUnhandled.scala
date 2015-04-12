package io.github.morgaroth.utils.akka

import akka.actor.{Actor, ActorLogging}

trait WarnUnhandled extends Actor with ActorLogging {

  abstract override def unhandled(message: Any): Unit = {
    log.warning(s"Unhandled message of type ${message.getClass.getCanonicalName} in actor ${context.self.toString()}")
    super.unhandled(message)
  }
}