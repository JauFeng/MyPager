import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal

val f = Future(1)

//  foreach
val x: Unit = f.foreach(print(_))

// map
val y: Future[Int] = f.map{ x => x + 1}

// flatMap
val z: Future[Int] = f.flatMap{ x => Future(0)}

val f1 = Future(2)
val f2 = Future(3)
val f3 = Future(4)

// zip
val zipFuture: Future[(Int, Int)] = f1 zip f2

// sequence
val seqFuture: Future[Seq[Int]] =  Future.sequence(Seq(f1, f2, f3))

// recover
val recover = f.recover{ case NonFatal(e) => ???}