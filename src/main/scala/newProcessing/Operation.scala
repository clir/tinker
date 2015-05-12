package newProcessing

import newProcessing.buffers.{BufferConfig, Buffer}

import scala.collection.mutable.ArrayBuffer

/**
 * All modules such as parser
 */
trait Operation extends Buffer {

  //define what two files are being worked on
//  val opSequence: ArrayBuffer[(Int, Int)] = ArrayBuffer.empty[(Int, Int)]

  def exec()(implicit config: Option[BufferConfig] = None): Unit
  def save(config: BufferConfig): Unit = exec()(Some(config))

}