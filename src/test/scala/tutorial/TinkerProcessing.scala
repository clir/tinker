package tutorial

import files.DataContainer
import files.filetypes.input._
import files.filetypes.output.{TabOutput, CSVOutput}
import org.scalatest.FlatSpec
import processing.buffers.file.FileBuffer
import processing.{Scheduler, Sequential}
import utils.ParameterCallToOption.Implicits._

/**
 * Created by Aimingnie on 6/29/15
 */
class TinkerProcessing extends FlatSpec {

  "sequential processing" can "transform tab file into csv format" in {

    val scheduler = new Scheduler with FileBuffer with Sequential with CSVOutput
    val data = new DataContainer("./src/test/scala/tutorial/data/tabFile.tab", header = true)(scheduler) with Tab

    data.save("./src/test/scala/tutorial/data/generatedCSV.csv")
  }

  "sequential processing" can "transform csv file into tab format" in {
    val scheduler = new Scheduler with FileBuffer with Sequential with TabOutput
    val data = new DataContainer("./src/test/scala/tutorial/data/csvFile.csv", header = true)(scheduler) with CSV

    data.save("./src/test/scala/tutorial/data/generatedTab.tab")
  }

}
