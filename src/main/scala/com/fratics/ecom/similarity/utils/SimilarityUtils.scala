package com.fratics.ecom.similarity.utils

import akka.event.Logging
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.{Authorization, BasicHttpCredentials}
import com.typesafe.config.ConfigFactory

object SimilarityUtils {
  //Initialize Configs & Loggers
  lazy val logger = Logging(SimilarityServerContext.system, getClass)
  lazy val config = ConfigFactory.load()

  //Get user credentials
  def getCredentials(req: HttpRequest): Option[Credentials] = {
    for {
      Authorization(BasicHttpCredentials(user, pass)) <- req.header[Authorization]
    } yield Credentials(user, pass)
  }

  //Authenticate the Server Admin User
  def validateServerAdmin(credentials: Credentials): Boolean = {
    config.getString("service.admin_user").equalsIgnoreCase(credentials.user) && config.getString("service.admin_pass").equalsIgnoreCase(credentials.pass)
  }

  def validateServerAdmin(user: String, pass: String): Boolean = {
    config.getString("service.admin_user").equalsIgnoreCase(user) && config.getString("service.admin_pass").equalsIgnoreCase(pass)
  }
}
