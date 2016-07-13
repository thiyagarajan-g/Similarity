package com.fratics.ecom.similarity.utils

import akka.actor.{ActorRef, ActorSystem}

//Creating a Context Object apart from the Akka to create abstraction

object SimilarityServerContext {
  var system: ActorSystem = null
  var sessionActorRouter: ActorRef = null

  def initialize() = {
    if (system == null) system = ActorSystem("SimilaritySystem")
    true
  }

  def unInitialize() = {}
}
