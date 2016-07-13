package com.fratics.ecom.similarity.utils

//Value Object used.
case class Request(reqid: String, userid: String, name: String, address: String, pincode: String, email: String, phone: String, ipinfo: String, deviceid: String, category: String, subcategory: String)

case class Credentials(user: String, pass: String)

case class Response(requests: List[Request])