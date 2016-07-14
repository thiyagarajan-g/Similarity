package com.fratics.ecom.similarity.utils

import com.fratics.ecom.similarity.func._
import com.typesafe.config.ConfigFactory


class SimilarityComparator {
  def compare( request1: Request, request2 : Similarity) : ResponseEntity = {
    val addressSimilarity = new AddressSimilarity(request1.address).similarTo(request2.addressSimilarity)
    val emailSimilarity = new EmailSimilarity(request1.email).similarTo(request2.emailSimilarity)
    val iPSimilarity = new IPSimilarity(request1.ipinfo).similarTo(request2.iPSimilarity)
    val nameSimilarity = new NameSimilarity(request1.name).similarTo(request2.nameSimilarity)
    val phoneSimilarity = new PhoneSimilarity(request1.phone).similarTo(request2.phoneSimilarity)
    ResponseEntity(request2.request,Weights(addressSimilarity,emailSimilarity,iPSimilarity,nameSimilarity,phoneSimilarity))
  }
}

object SimilarityComparator{
  val config = ConfigFactory.load()
  var threshold = config.getDouble("service.threshold")
  if(threshold == 0) threshold = 0.65;
}
