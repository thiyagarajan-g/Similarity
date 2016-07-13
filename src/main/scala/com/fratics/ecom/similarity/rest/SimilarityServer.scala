package com.fratics.ecom.similarity.rest

import akka.actor._
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.Timeout
import com.fratics.ecom.similarity.rest.flow.{DumpToDB, LoadFromDB, VerifyOrder}
import com.fratics.ecom.similarity.utils.{SimilarityServerContext, SimilarityUtils}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object SimilarityServer extends App {

  //System Configs & Loggers
  lazy val config = ConfigFactory.load()
  lazy val logger = Logging(system, getClass)
  //Akka Actor Env
  implicit val system = ActorSystem("SimilaritySystem")
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer(ActorMaterializerSettings.create(system))
  implicit val timeout: Timeout = Timeout(config.getInt("service.timeout") seconds)

  //Initialize Context
  SimilarityServerContext.system = system
  //Server Routes Definition
  val routes = {
    logRequestResult("similarity-rest-server") {
      pathPrefix("similarityServices" / "v1") {
        //Definitions of routes for CAP Session Services.
        pathSuffix("verifyOrder") {
          optionalHeaderValueByName("X-REQ-OBJ") { ost =>
            complete {
              logger.info("Execting Verify Order API Flow")
              VerifyOrder.processFlow(Array(ost), 1)
            }
          }
        } ~
          pathSuffix("dumpToDB") {
            extractRequest { req =>
              complete {
                logger.info("Execting DumpToDB() API Flow")
                DumpToDB.processFlow(Array(SimilarityUtils.getCredentials(req)), 1)
              }
            }
          } ~
          pathSuffix("loadFromDB") {
            extractRequest { req =>
              complete {
                logger.info("Execting loadFromDB() API Flow")
                LoadFromDB.processFlow(Array(SimilarityUtils.getCredentials(req)), 1)
              }
            }
          }

        //Definition of routes ends.
      }
    }
  }
  //Start the Server
  Http().bindAndHandle(routes, config.getString("standalone.interface"), config.getInt("standalone.port"))
}