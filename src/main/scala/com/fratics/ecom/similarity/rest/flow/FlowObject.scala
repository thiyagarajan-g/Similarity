package com.fratics.ecom.similarity.rest.flow

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.util.Timeout
import com.fratics.ecom.similarity.dao.SimilarityDao
import com.fratics.ecom.similarity.exception.BadRequestException
import com.fratics.ecom.similarity.utils.{Request, SimilarityProtocols}
import spray.json._

import scala.concurrent.duration._

abstract class FlowObject extends SimilarityProtocols with SimilarityDao {
  //TimeOut Definition for Akka Connection Flow Object
  implicit val timeout: Timeout = Timeout(config.getInt("service.timeout") seconds)
  var request: Request = null;

  //Default Process Flow Definitions of the inherited Handler
  def processFlow(any: Array[Any], noArg: Int): ToResponseMarshallable {}

  //Utility Method to check & Marshall the Presented Request Object
  def unMarshallRequestToken(inp: Option[String]): Boolean = {
    inp match {
      case Some(req) => request = req.parseJson.convertTo[Request]
      case None => throw new BadRequestException("Empty Request: No Order Info")
    }
    true
  }
}
