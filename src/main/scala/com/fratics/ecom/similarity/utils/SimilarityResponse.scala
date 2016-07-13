package com.fratics.ecom.similarity.utils

import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpResponse, StatusCode}
import com.typesafe.config.ConfigFactory
import spray.json._


object SimilarityResponse extends SimilarityProtocols {
  //Initialize Logger
  lazy val logger = Logging(SimilarityServerContext.system, getClass)
  lazy val config = ConfigFactory.load()
  lazy val reqidHeaderName = config.getString("service.reqid_header")


  //Handle Error and send Err Msg to Client
  def handleError(e: Exception, errMsg: String): HttpResponse = {
    //e.printStackTrace()
    logger.error(s"Error Occured --> ${errMsg} with Exception --> ${e.getMessage}")
    HttpResponse(status = InternalServerError, entity = s"${errMsg} : ${e}\n")
  }

  //Send Success to Client with status Msg.
  def sendSuccess(req: Request, statusMsg: String): HttpResponse = sendCustom(OK, req, statusMsg)

  //Send Custom Msg to Client
  def sendCustom(sc: StatusCode, req: Request, statusMsg: String): HttpResponse = {
    logger.info(s"Sending a Custom Msg to Client -> ${statusMsg} - Session Token --> ${req}")
    HttpResponse(status = sc,
      headers = List(RawHeader(reqidHeaderName, req.reqid.toJson.compactPrint)),
      entity = s"${statusMsg}\n")
  }

  //Send Bad Request to Client
  def sendBadRequest(statusMsg: String): HttpResponse = {
    logger.info(s"Sending BadRequest to Client -> ${statusMsg}")
    HttpResponse(BadRequest, entity = s"${statusMsg}\n")
  }

  //System Information Dump for Admin
  def sendSystemMsg(sc: StatusCode, statusMsg: String): HttpResponse = {
    logger.info(s"Sending System Msg to Client -> ${statusMsg}")
    HttpResponse(sc, entity = s"${statusMsg}\n")
  }

}
