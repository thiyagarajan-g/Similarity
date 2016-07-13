package com.fratics.ecom.similarity.utils

import java.security.MessageDigest

object EncryptionUtil {

  //MD5 checksums for generation of Application Session Ids and User Session Ids.
  def md5(s: String): String = {
    Option(s) match {
      case Some(x) => MessageDigest.getInstance("MD5").digest(x.getBytes).map("%02x".format(_)).mkString
      case None => MessageDigest.getInstance("MD5").digest(System.currentTimeMillis.toString.getBytes).map("%02x".format(_)).mkString
    }
  }

  //SHA-256 checksums for Session Token Signature.
  def sha256(s: String): String = {
    Option(s) match {
      case Some(x) => MessageDigest.getInstance("SHA-256").digest(x.getBytes).map("%02x".format(_)).mkString
      case None => MessageDigest.getInstance("SHA-256").digest(System.currentTimeMillis.toString.getBytes).map("%02x".format(_)).mkString
    }
  }
}
