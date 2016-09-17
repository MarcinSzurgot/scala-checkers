package main.scala.view

import main.scala.view.BoardScene

import scalafx.stage.Stage

class BoardStage(_cssPath: List[String]) extends Stage
{
  title.value = "Board"
  width = 620
  height = 660
  scene = new BoardScene(this) {
    stylesheets = _cssPath
  }
}
