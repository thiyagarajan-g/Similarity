package com.fratics.ecom.similarity.utils

import spray.json.DefaultJsonProtocol

trait SimilarityProtocols extends DefaultJsonProtocol {
  implicit val requestFormat = jsonFormat11(Request.apply)
  implicit val responseFormat = jsonFormat1(Response.apply)
}

