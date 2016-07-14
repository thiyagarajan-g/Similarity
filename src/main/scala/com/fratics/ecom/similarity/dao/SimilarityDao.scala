package com.fratics.ecom.similarity.dao

import com.fratics.ecom.similarity.exception.SimilarityException
import com.fratics.ecom.similarity.utils.Request
import com.fratics.ecom.similarity.utils.Request.SimilarityBSONWriter
import play.api.libs.iteratee.Iteratee
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.collection.mutable.MutableList;

trait SimilarityDao extends MongoDao {

  var requestMap: Map[String, MutableList[Request]] = Map()

  var collectionMap: Map[String, BSONCollection] = Map()

  def save(request: Request) : Boolean = {
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

  def loadData: Boolean = {
    try {
      val locations = config.getStringList("mongodb.locations").asScala
      locations.foreach { x => val collection = getCollectionHandle(x)
        val cursor = collection.find(BSONDocument()).cursor[Request]
        cursor.enumerate().apply(Iteratee.foreach { doc =>
          requestMap.get(doc.pincode) match {
            case Some(x) => x += doc
            case None => requestMap += (doc.pincode -> MutableList(doc))
          }
        })
      }
    }catch{
      case e: Exception => throw e;
    }
    true
  }
}
