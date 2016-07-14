package com.fratics.ecom.similarity.rest

import akka.actor._
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.Timeout
import com.fratics.ecom.similarity.rest.flow.{LoadFromDB, SaveToDB, VerifyOrder}
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
      pathPrefix("similarityservices" / "v1") {
        //Definitions of routes for CAP Session Services.
        pathSuffix("verifyorder") {
          optionalHeaderValueByName("X-REQ-OBJ") { inp =>
            complete {
              logger.info("Execting VerifyOrder() API Flow")
              VerifyOrder.processFlow(Array(inp), 1)
            }
          }
        } ~
          pathSuffix("savetodb") {
            extractRequest { req =>
              optionalHeaderValueByName("X-REQ-OBJ") { inp =>
                complete {
                  logger.info("Execting DumpToDB() API Flow")
                  SaveToDB.processFlow(Array(SimilarityUtils.getCredentials(req), inp), 2)
                }
              }
            }
          } ~
          pathSuffix("loadfromdb") {
            extractRequest { req =>
              parameter("pincodelist") { pincodelist =>
                complete {
                  logger.info("Execting loadFromDB() API Flow")
                  LoadFromDB.processFlow(Array(SimilarityUtils.getCredentials(req), pincodelist), 2)
                }
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