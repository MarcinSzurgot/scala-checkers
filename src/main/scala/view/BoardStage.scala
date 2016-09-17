package main.scala.view

import main.scala.view.BoardScene

import scalafx.stage.Stage

class BoardStage(_cssPath: List[String]) extends Stage
{
  title.value = "Board"
  width = 600
  height = 650
  minWidth = 100
  scene = new BoardScene(this) {
    stylesheets = _cssPath
  }
}
