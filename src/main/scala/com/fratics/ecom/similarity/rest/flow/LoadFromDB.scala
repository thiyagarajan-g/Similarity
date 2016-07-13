package com.fratics.ecom.similarity.rest.flow

import akka.event.Logging
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import com.fratics.ecom.similarity.exception.BadRequestException
import com.fratics.ecom.similarity.utils.SimilarityResponse._
import com.fratics.ecom.similarity.utils._


object LoadFromDB extends FlowObject {

  override def processFlow(any: Array[Any], noArg: Int): ToResponseMarshallable = {

    try {
      lazy val logger = Logging(SimilarityServerContext.system, getClass)
      lazy val cred = any(0).asInstanceOf[Option[Credentials]]

      logger.info(s"In LoadFromDB() Flow, Cred --> ${cred}")

      //Authenticate Using Server Credentials
      cred match {
        case Some(x) => {
          SimilarityUtils.validateServerAdmin(x) match {
            case true => sendSystemMsg(OK, "Successfully Dumped Data to DB")
            case false => sendSystemMsg(Unauthorized, "Authentication Failure")
          }
        }
        case None => throw new BadRequestException("Empty Credentials")
      }

    } catch {
      case b: BadRequestException => sendBadRequest(b.getMessage)
      case e: Exception => handleError(e, "Unable to Load Data from DB")
    }
  }
}
