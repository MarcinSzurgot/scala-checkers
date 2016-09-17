package main.scala.view

import main.scala.view.BoardScene

import scalafx.stage.Stage

class BoardStage(_cssPath: List[String]) extends Stage
{
  title.value = "Board"
  width = 700
  height = 700
  minWidth = 100
  scene = new BoardScene(this) {
    stylesheets = _cssPath
  }
}
