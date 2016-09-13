package main.scala.view

import scalafx.scene.layout._
import scalafx.Includes._
import scalafx.geometry.{HPos, Point2D, VPos}
import scalafx.scene.Scene
import scalafx.scene.input.MouseEvent
import main.scala.logic.Game
import main.scala.model.PawnType
import main.scala.model.PawnType.PawnType


class BoardScene extends Scene
{
  final val BOARD_LENGTH = 8
  final val START_POS = 1

  var board = Array.ofDim[StackPane](BOARD_LENGTH, BOARD_LENGTH)
  val game = new Game(this)

  content = new GridPane {
    columnConstraints += new ColumnConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, HPos.Center, true).delegate
    rowConstraints += new RowConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, VPos.Center, true).delegate
    var tempBoard = game.initGame(8,8,3,2)
    for (i <- START_POS to BOARD_LENGTH) {
      columnConstraints += new ColumnConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, HPos.Center, true).delegate
      rowConstraints += new RowConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, VPos.Center, true).delegate
      for (a <- START_POS to BOARD_LENGTH) {
        val boardPiece = new StackPane {
          if (1 == (i + a) % 2) styleClass += "blackSquare" else styleClass += "whiteSquare"
          if (convertPawnTypeToCssClass(tempBoard.getPawn(a-1,i-1)) != "None") {
            styleClass += convertPawnTypeToCssClass(tempBoard.getPawn(a-1,i-1))
          }
          onMouseClicked = (me: MouseEvent) => {
            game.takeAction(a-1,i-1)
          }
        }
        board(i-1)(a-1) = boardPiece
        add(boardPiece.delegate, i, a)
      }
    }
  }

  def updatePosition(startPoint: Point2D, endPoint: Point2D): Unit = {
    clearAllPawns(board(endPoint.y.toInt)(endPoint.x.toInt))
    board(endPoint.y.toInt)(endPoint.x.toInt).styleClass += getStartingStyleClass(board(startPoint.y.toInt)(startPoint.x.toInt))
    clearAllPawns(board(startPoint.y.toInt)(startPoint.x.toInt))
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
    "None";
  }

  def promote(stackPane: StackPane): Unit = {
    if(stackPane.styleClass.contains("whitePawn")) {
      stackPane.styleClass -= "whitePawn"
      stackPane.styleClass += "whiteQueen"
    }
    stackPane.styleClass -= "blackPawn"
    stackPane.styleClass += "blackQueen"
  }

  def convertPawnTypeToCssClass(pawnType: PawnType): String = {
    if(pawnType == PawnType.BLACK) {
      return "blackPawn"
    }
    if(pawnType == PawnType.WHITE) {
      return "whitePawn"
    }
    if(pawnType == PawnType.BLACK_PROMOTED) {
      return "blackQueen"
    }
    if(pawnType == PawnType.WHITE_PROMOTED) {
      return "whiteQueen"
    }
    "None"
  }

  def markSelectedField(moves : List[Point2D]): Unit ={
    moves.foreach{e => board(e.y.toInt)(e.x.toInt).styleClass += "selected"}
  }
}
