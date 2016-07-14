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
      lazy val pincodeList = any(1).asInstanceOf[String].split(",").toList

      logger.info(s"In LoadFromDB() Flow, Cred --> ${cred}")

      //Authenticate Using Server Credentials
      cred match {
        case Some(x) => {
          SimilarityUtils.validateServerAdmin(x) match {
            case true =>
            case false => sendSystemMsg(request, Unauthorized, "Authentication Failure")
          }
        }
        case None => throw new BadRequestException("Empty Credentials")
      }

      pincodeList.isEmpty match{
        case true => {
          loadData match {
            case true => sendSystemMsg(request, OK, "Successfully Loaded Data from DB")
            case false => sendBadRequest(request, "Failed to load Data from DB")
          }
        }
        case false => {
          loadData(pincodeList) match {
            case true => sendSystemMsg(request, OK, "Successfully Loaded Data from DB")
            case false => sendBadRequest(request, "Failed to load Data from DB")
          }
        }
      }

    } catch {
      case b: BadRequestException => sendBadRequest(request, b.getMessage)
      case e: Exception => handleError(request, e, "Unable to Load Data from DB")
    }
  }
}
