package newFiles

import newProcessing.Scheduler

import scala.annotation.tailrec
import scala.collection.AbstractIterator
import scala.collection.mutable

/**
 * Redesign of old DataContainer
 * emphasize on iterator operation
 *
 * The saving functionality is integrated with
 * Operation's exec() method. We do not offer
 * real save() method, but maybe an alias that maps
 * to exec()
 *
 * @param fuzzyMatch this gets passed to FileMapIterator, exclusive end
 * @param scheduler this is not mandatory for normal DataContainer class
 *                  but it is when you add FileOp module to it
 */
abstract class DataContainer(val f: Option[String] = None,
                              val header: Option[Boolean] = Some(true),
                              val fuzzyMatch: Option[Int] = None)(implicit val scheduler: Option[Scheduler] = None) {

  import RowTypes._

  def exec(): Unit = scheduler.foreach(e => e.exec())
  def save(): Unit = exec()

  //leave implementation details to Doc or other services
  def iteratorMap: mutable.HashMap[String, RowIterator]

  /**
   * incorporate the file info into a column
   * @param fileColName default value is "fileName"
   * @return
   */
  def flatten(fileColName: Option[String] = None): Iterator[NormalRow] = new AbstractIterator[NormalRow] {

    val itm = iteratorMap.iterator
    var cit = itm.next()

    override def hasNext: Boolean = itm.hasNext || cit._2.hasNext

    @tailrec
    override def next(): NormalRow = {
      if (!cit._2.hasNext && itm.hasNext) {
        cit = itm.next()
        next()
      }
      else cit._2.next() += (fileColName.getOrElse("fileName") -> cit._1)
    }
  }

  //discard the file information
  def unify: Iterator[NormalRow] = new AbstractIterator[NormalRow] {

    var it = iteratorMap.toIterator
    var currentIt = it.next()._2

    override def hasNext: Boolean = it.hasNext || currentIt.hasNext

    @tailrec
    override def next(): NormalRow = {
      if (!currentIt.hasNext && it.hasNext) {
        currentIt = it.next()._2
        next()
      }
      else currentIt.next()
    }
  }

}

object RowTypes {
  type NormalRow = mutable.HashMap[String, String] //enforced HashMap for it's eC performance
  type RowIterator = Iterator[NormalRow]
}