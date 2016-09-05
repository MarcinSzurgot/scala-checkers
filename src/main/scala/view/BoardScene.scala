package main.scala.view

import scalafx.scene.layout._
import scalafx.Includes._
import scalafx.geometry.{HPos, Point2D, VPos}
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
            clearSelected()
          }
        }
        board(i-1)(a-1) = boardPiece
        add(boardPiece.delegate, i, a)
      }
    }
  }

  def updatePosition(startPoint: Point2D, endPoint: Point2D): Unit = {
    clearAllPawns(board(endPoint.x.toInt)(endPoint.y.toInt))
    board(endPoint.x.toInt)(endPoint.y.toInt).styleClass += getStartingStyleClass(board(startPoint.x.toInt)(startPoint.y.toInt));
  }

  def clearSelected(): Unit = {
    board.foreach{ x => x.foreach { x => x.styleClass -= "selected" } }
  }
  def clearAllPawns(stackPane: StackPane): Unit = {
    stackPane.styleClass -= "whitePawn"
    stackPane.styleClass -= "whiteQueen"
    stackPane.styleClass -= "blackPawn"
    stackPane.styleClass -= "blackQueen"
  }

  def getStartingStyleClass(stackPane: StackPane): String = {
    if(stackPane.styleClass.contains("whitePawn")) {
      stackPane.styleClass -= "whitePawn"
      return "whitePawn";
    }
    if(stackPane.styleClass.contains("blackPawn")) {
      stackPane.styleClass -= "blackPawn"
      return "blackPawn";
    }
    if(stackPane.styleClass.contains("whiteQueen")) {
      stackPane.styleClass -= "whiteQueen"
      return "whiteQueen";
    }
    if(stackPane.styleClass.contains("blackQueen")) {
      stackPane.styleClass -= "blackQueen"
      return "blackQueen";
    }
    return "None";
  }

  def promote(stackPane: StackPane): Unit = {
    if(stackPane.styleClass.contains("whitePawn")) {
      stackPane.styleClass -= "whitePawn"
      stackPane.styleClass += "whiteQueen"
    }
    stackPane.styleClass -= "blackPawn"
    stackPane.styleClass += "blackQueen"
  }
}
