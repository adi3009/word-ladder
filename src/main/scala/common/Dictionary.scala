package common

import scala.io.Source
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object Dictionary {    
  val path = "sgb-words.txt"
  
  private def streamFromPath(): Option[InputStream] = {
    val classesDir = new File(getClass.getResource(".").toURI)
    val projectDir = classesDir.getParentFile.getParentFile.getParentFile.getParentFile
    val dictionaryFile = new File(projectDir + "/src/main/resources/" + path)    
    if (dictionaryFile.exists)
      Some(new FileInputStream(dictionaryFile))
    else
      None    
  }  
  
  val wordStream = Option {
    getClass.getClassLoader.getResourceAsStream(path)
  } orElse{
    streamFromPath
  } getOrElse {
    sys.error("Unable to load dictionary")
  }
  
  val load = Source.fromInputStream(wordStream)
  
  val words = load.getLines.toList
  
  def exists(word: String) = words.exists(_ == word)
}