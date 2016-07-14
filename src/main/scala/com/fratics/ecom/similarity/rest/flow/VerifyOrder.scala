package com.fratics.ecom.similarity.rest.flow

import akka.event.Logging
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import com.fratics.ecom.similarity.exception.BadRequestException
import com.fratics.ecom.similarity.utils.SimilarityResponse._
import com.fratics.ecom.similarity.utils.SimilarityServerContext


object VerifyOrder extends FlowObject {

  override def processFlow(any: Array[Any], noArg: Int): ToResponseMarshallable = {
    try {

      lazy val logger = Logging(SimilarityServerContext.system, getClass)
      lazy val inp = any(0).asInstanceOf[Option[String]]

      logger.info(s"In VerifyOrder Flow --> ${inp}")

      unMarshallRequestToken(inp)

      val response = verifyOrder(request)

      //Send the UnMarshalled Token Back
      sendSuccess(request, response)

    } catch {
      case b: BadRequestException => sendBadRequest(request, b.getMessage)
      case e: Exception => handleError(request, e, "Unable to Verify Order")
    }
  }
}
