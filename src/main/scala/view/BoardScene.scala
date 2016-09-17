package main.scala.view

import scalafx.scene.layout._
import scalafx.Includes._
import scalafx.geometry._
import scalafx.scene.Scene
import scalafx.scene.input.MouseEvent
import main.scala.logic.Game
import main.scala.model.Board.Action
import main.scala.model.{Move, PawnType, Position}
import main.scala.model.PawnType.PawnType

import scalafx.scene.control.{Button, Label}


class BoardScene(_boardStage: BoardStage) extends Scene
{
  final val BOARD_LENGTH = 8
  final val START_POS = 1

  var board = Array.ofDim[StackPane](BOARD_LENGTH, BOARD_LENGTH)
  val game = new Game(this)
  var tempBoard = game.initGame(8,8,3,2)
  var playerLabel = new Label(tempBoard.getCurrentPlayer().toString) {
    id = "playerName"
    alignment = Pos.Center
    maxWidth = Double.MaxValue
  }
  content =new VBox() {
    vgrow = Priority.Always
    hgrow = Priority.Always
    margin = Insets(0,0,0,0)
    children = Seq(
      playerLabel,
      new GridPane {
        columnConstraints += new ColumnConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, HPos.Center, true).delegate
        rowConstraints += new RowConstraints(60, Region.USE_COMPUTED_SIZE, Double.PositiveInfinity, Priority.Always, VPos.Center, true).delegate
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
    )
  }

  def updatePosition(move: Move, pawnType: PawnType): Unit = {
    board(move.end.col)(move.end.row).styleClass += convertPawnTypeToCssClass(pawnType)
    clearAllPawns(board(move.begin.col)(move.begin.row))
    clearSelected()
  }

  def clearSelected(): Unit = {
    board.foreach{ x => x.foreach { x => x.styleClass -= "selected"; x.styleClass -= "danger"} }
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

  def markSelectedFields(moves : Action): Unit ={
    board(moves._1.head.begin.col)(moves._1.head.begin.row).styleClass += "selected"
    moves._1.foreach{e => board(e.end.col)(e.end.row).styleClass += "selected"}
    moves._2.foreach{e => if(e != null) board(e._1.col)(e._1.row).styleClass += "danger"}
  }

  def clearPawn(pos: Position): Unit ={
    clearAllPawns(board(pos.col)(pos.row))
    clearSelected()
  }

  def close(): Unit =
  {
    _boardStage.close()
  }

  def setCurrentPlayer(playerName: String): Unit =
  {
    playerLabel.text = playerName
  }
}
