package com.fratics.ecom.similarity.utils

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

//Value Object used.
case class Credentials(user: String, pass: String)
case class Response(requests: List[Request])
case class Request(reqid: String, userid: String, name: String, address: String, pincode: String, email: String, phone: String, ipinfo: String, deviceid: String, category: String, subcategory: String)

object Request {

  implicit object SimilarityBSONReader extends BSONDocumentReader[Request] {

    def read(doc: BSONDocument): Request =
      Request(doc.getAs[String]("reqid").get,
        doc.getAs[String]("userid").get,
        doc.getAs[String]("name").get,
        doc.getAs[String]("address").get,
        doc.getAs[String]("pincode").get,
        doc.getAs[String]("email").get,
        doc.getAs[String]("phone").get,
        doc.getAs[String]("ipinfo").get,
        doc.getAs[String]("deviceid").get,
        doc.getAs[String]("category").get,
        doc.getAs[String]("subcategory").get)
  }

  implicit object SimilarityBSONWriter extends BSONDocumentWriter[Request] {
    def write(entity: Request): BSONDocument =
      BSONDocument(
        "reqid" -> entity.reqid,
        "userid" -> entity.userid,
        "name" -> entity.name,
        "address" -> entity.address,
        "pincode" -> entity.pincode,
        "email" -> entity.email,
        "phone" -> entity.phone,
        "ipinfo" -> entity.ipinfo,
        "deviceid" -> entity.deviceid,
        "category" -> entity.category,
        "subcategory" -> entity.subcategory)
  }
}