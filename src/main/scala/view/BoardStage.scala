package main.scala.view

import scalafx.stage.Stage

class BoardStage(_cssPath: List[String], playerChoice: String, _level: Int) extends Stage
{
  title.value = "Board"
  width = 620
  height = 660
  scene = new BoardScene(this , if (playerChoice == "One Player") 1 else 2, _level) {
    stylesheets = _cssPath
  }
}
