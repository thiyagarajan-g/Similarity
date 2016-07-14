package com.fratics.ecom.similarity.rest.flow

import akka.event.Logging
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import com.fratics.ecom.similarity.exception.BadRequestException
import com.fratics.ecom.similarity.utils.SimilarityResponse._
import com.fratics.ecom.similarity.utils._

object SaveToDB extends FlowObject{

  override def processFlow(any: Array[Any], noArg: Int): ToResponseMarshallable = {

    try {
      lazy val logger = Logging(SimilarityServerContext.system, getClass)
      lazy val cred = any(0).asInstanceOf[Option[Credentials]]
      lazy val inp = any(1).asInstanceOf[Option[String]]

      logger.info(s"In SaveToDB() Flow, Cred --> ${cred}")

      //Authenticate Using Server Credentials
      cred match {
        case Some(x) => {
          SimilarityUtils.validateServerAdmin(x) match {
            case true =>
            case false => sendSystemMsg(Unauthorized, "Authentication Failure")
          }
        }
        case None => throw new BadRequestException("Empty Credentials")
      }

      unMarshallRequestToken(inp)

      save(request) match {
        case true => sendSystemMsg(OK, "Successfully Saved Data to DB")
        case false => sendBadRequest("Failed to Save Data to DB")
      }

    } catch {
      case b: BadRequestException => sendBadRequest(b.getMessage)
      case e: Exception => handleError(e, "Unable to Save Data To DB")
    }
  }
}
