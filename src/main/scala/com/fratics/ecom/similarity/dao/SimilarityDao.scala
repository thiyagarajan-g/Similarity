package com.fratics.ecom.similarity.dao

import com.fratics.ecom.similarity.utils._
import com.fratics.ecom.similarity.utils.Request.SimilarityBSONWriter
import play.api.libs.iteratee.Iteratee
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import scala.collection.JavaConverters._
import scala.collection.mutable.MutableList
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import spray.json._

trait SimilarityDao extends MongoDao with SimilarityProtocols{

  var requestMap: Map[String, MutableList[Similarity]] = Map()
  var collectionMap: Map[String, BSONCollection] = Map()

  def save(request: Request): Boolean = {
    val future = getCollectionHandle(request.pincode).insert(request)
    future.onComplete {
      case Failure(e) => throw e
      case Success(writeResult) => //
    }
    true
  }

  private def getCollectionHandle(str: String): BSONCollection = {
    collectionMap.get(str) match {
      case Some(x) => x
      case None => {
        val x = getCollectionFromDB(str)
        collectionMap += (str -> x)
        x
      }
    }
  }

  private def getCollectionFromDB(str: String): BSONCollection = {
    db[BSONCollection](str)
  }


  def loadData : Boolean = {
    loadData(config.getStringList("mongodb.locations").asScala.toList)
  }

  def loadData(locations : List[String]): Boolean = {
    try {
      locations.foreach { x => val collection = getCollectionHandle(x)
        val cursor = collection.find(BSONDocument()).cursor[Request]
        cursor.enumerate().apply(Iteratee.foreach { doc =>
          requestMap.get(doc.pincode) match {
            case Some(x) => x += Similarity.convert(doc)
            case None => requestMap += (doc.pincode -> MutableList(Similarity.convert(doc)))
          }
        })
      }
    } catch {
      case e: Exception => throw e;
    }
    true
  }

  def verifyOrder(request: Request) : String = {
    var res = MutableList[ResponseEntity]()
    requestMap.get(request.pincode) match{
      case Some(x) => x.toSeq.foreach{ a =>
        val y = new SimilarityComparator().compare(request, a);
        res += y
      }
      case None => Response("Success", List()).toJson.compactPrint
    }
    Response("Success", res.toList).toJson.compactPrint
  }
}
