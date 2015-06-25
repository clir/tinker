package newProcessing

import newProcessing.buffers.BufferConfig
import newProcessing.buffers.file.FileBuffer

/**
 * Created by anie on 5/23/2015
 *
 * Printer works like scheduler
 * use it like
 * new Printer(...) with CSVOutput
 */
abstract class Printer(filePath: Option[String], fileAppend: Boolean = true)(implicit val bufferConfig: BufferConfig = BufferConfig()) extends FileBuffer{

  config.filePath = filePath
  config.fileAppend = fileAppend

  override val config: BufferConfig = bufferConfig
}