package dals

import com.mongodb.client.model.WriteModel
import org.mongodb.scala._
import org.mongodb.scala.model._
import org.mongodb.scala.result.{DeleteResult, UpdateResult}

/**
 * Template for User modal database access library.
 *
 */
class UserDal {
  /** Configuration. */

  // Make a connection.
  val mongoClient: MongoClient = MongoClient("mongodb://localhost")

  // Get a database.
  val database: MongoDatabase = mongoClient.getDatabase("myDB")

  // Get a collection.
  val collection: MongoCollection[Document] = database.getCollection("test")


  /** Operations. */

  // Insert a document.
  val document = Document("_id" -> 0,
    "name" -> "MongoDB",
    "type" -> "database",
    "count" -> 1,
    "info" -> Document("x" -> 203, "y" -> 102))

  val insertOneObservable: Observable[Completed] = collection.insertOne(document)
  insertOneObservable.subscribe(new Observer[Completed] {
    override def onError(e: Throwable): Unit = println(s"onError: $e")

    override def onComplete(): Unit = println("Completed")

    override def onNext(result: Completed): Unit = println(s"onNext: $result")
  })

  // Insert multiple documents.
  val documents: Seq[Document] = (1 to 100) map { n => Document("n" -> n) }
  val insertManyObservable: Observable[Completed] = collection.insertMany(documents)

  // Query the collection.
  val allObservable: FindObservable[Document] = collection.find()
  val firstObservable: Observable[Document] = allObservable.first()

  // Get document with a Query Filter.
  import org.mongodb.scala.model.Filters._
  val equalObservable: Observable[Document] =
    collection.find(equal("n", 50)).first() // n = 50

  val andObservable: FindObservable[Document] =
    collection.find(and(gt("i", 50), lte("i", 100))) // 50 < n <= 100

  // Sorting document.
  import org.mongodb.scala.model.Sorts._
  val complexFilterObservable = collection.find(exists("i")).sort(descending("i"))

  // Projecting fields.
  import org.mongodb.scala.model.Projections._
  val idObservable: Observable[Document] = collection.find().projection(excludeId()).first()

  // Updating document.
  import org.mongodb.scala.model.Updates._
  val updateOneObservable: Observable[UpdateResult] = collection.updateOne(equal("i", 10), set("i", 100))
  val updateManyObservable: Observable[UpdateResult] = collection.updateMany(lt("i", 100), inc("i", 100))

  // Deleting document.
  val deleteOneResult: Observable[DeleteResult] = collection.deleteOne(equal("i", 10))
  val deleteManyResult: Observable[DeleteResult] = collection.deleteMany(gte("i", 10))

  // Bulk operations.
  val writes: List[WriteModel[_ <: Document]] = List(
    InsertOneModel(Document("_id" -> 4)),
    InsertOneModel(Document("_id" -> 5)),
    InsertOneModel(Document("_id" -> 6)),
    UpdateOneModel(Document("_id" -> 1), set("x", 2)),
    DeleteOneModel(Document("_id" -> 2)),
    ReplaceOneModel(Document("_id" -> 3), Document("_id" -> 3, "x" -> 4))
  )

  collection.bulkWrite(writes)
  collection.bulkWrite(writes, BulkWriteOptions().ordered(false))
}
