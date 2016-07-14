package com.fratics.ecom.similarity.utils

import spray.json.DefaultJsonProtocol

trait SimilarityProtocols extends DefaultJsonProtocol {
  implicit val requestFormat = jsonFormat11(Request.apply)
  implicit val weightsFormat = jsonFormat5(Weights.apply)
  implicit val responseEntityFormat = jsonFormat2(ResponseEntity.apply)
  implicit val responseFormat = jsonFormat3(Response.apply)
}

