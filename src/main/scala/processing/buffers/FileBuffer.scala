package processing.buffers

import java.nio.file.{Files, Path, Paths}

import files.filetypes.FileTypes
import utils.FailureHandle

/**
 * Created by anie on 3/31/2015.
 *
 * Functionality is similar to Doc
 */
trait FileBuffer extends FailureHandle with FileTypes {

//  override implicit val saveLoc: Option[Path] =

    def getSaveLoc(outputFile: Option[String] = None, outputOverride: Boolean = false): Option[Path] = {
      outputFile match {
      case Some(file) =>
        val permFile = Paths.get(file)
        if (Files.isDirectory(permFile)) fatal("outputfile cannot be a directory")
        if (Files.exists(permFile) && !outputOverride) fatal("output file already exist")
        Files.deleteIfExists(permFile)
        Files.createFile(permFile)
  //      if (data.header) printHeader()
        Some(permFile)
      case None =>
        val tempFile = Paths.get(System.getProperty("user.home")).resolve(".Tinker")
        if (Files.notExists(tempFile)) Files.createDirectory(tempFile)
        Files.createTempFile(tempFile, "parser", "." + typesuffix.head).toFile.deleteOnExit()
        Some(tempFile)
  }
}

}
