package main.scala.view

import scalafx.scene.layout._
import scalafx.Includes._
import scalafx.geometry.{HPos, VPos}
import scalafx.scene.Scene
import scalafx.scene.input.MouseEvent
import main.scala.logic.Game


class BoardScene extends Scene
{
  final val BOARD_LENGTH = 8;
  final val START_POS = 1;

  //val gameController = new Game();
  var board = Array.ofDim[StackPane](BOARD_LENGTH, BOARD_LENGTH)

  content = new GridPane {
    columnConstraints += new ColumnConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, HPos.Center, true).delegate
    rowConstraints += new RowConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, VPos.Center, true).delegate
    for (i <- START_POS to BOARD_LENGTH) {
      columnConstraints += new ColumnConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, HPos.Center, true).delegate
      rowConstraints += new RowConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, VPos.Center, true).delegate
      for (a <- START_POS to BOARD_LENGTH) {
        val boardPiece = new StackPane {
          if (1 == (i + a) % 2) styleClass += "blackSquare" else styleClass += "whiteSquare"
          onMouseClicked = (me: MouseEvent) => {
            if(styleClass.contains("selected")) {
              styleClass -= "selected"
            } else {
              styleClass += "selected"
            }
          }
        }
        board(i-1)(a-1) = boardPiece
        add(boardPiece.delegate, i, a)
      }
    }
  }

  def updatePosition(): Unit = {

  }
}
