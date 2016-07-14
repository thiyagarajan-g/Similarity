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
  def handleError(request: Request, e: Exception, errMsg: String): HttpResponse = {
    //e.printStackTrace()
    logger.error(s"Error Occured --> ${errMsg} with Exception --> ${e.getMessage}")
    HttpResponse(status = InternalServerError,
      headers = List(RawHeader(reqidHeaderName, request.reqid.toJson.compactPrint)),
      entity = s"${errMsg} : ${e}\n")
  }

  //Send Success to Client with status Msg.
  def sendSuccess(request: Request, response: Response): HttpResponse = sendCustom(OK, request, response)

  //Send Custom Msg to Client
  def sendCustom(sc: StatusCode, request: Request, response: Response): HttpResponse = {
    val x = response.toJson.compactPrint
    logger.info(s"Sending a Custom Msg to Client -> ${response.status} - Response --> ${x}")
    HttpResponse(status = sc,
      headers = List(RawHeader(reqidHeaderName, request.reqid.toJson.compactPrint)),
      entity = s"${x}\n")
  }

  //Send Bad Request to Client
  def sendBadRequest(request: Request, statusMsg: String): HttpResponse = {
    logger.info(s"Sending BadRequest to Client -> ${statusMsg}")
    HttpResponse(BadRequest,
      headers = List(RawHeader(reqidHeaderName, request.reqid.toJson.compactPrint)),
      entity = Response(s"${statusMsg}", request, List()).toJson.compactPrint)
  }

  //System Information Dump for Admin
  def sendSystemMsg(request: Request, sc: StatusCode, statusMsg: String): HttpResponse = {
    logger.info(s"Sending System Msg to Client -> ${statusMsg}")
    HttpResponse(sc, entity = Response(s"${statusMsg}", request, List()).toJson.compactPrint)
  }

}
