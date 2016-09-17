package main.scala.logic

import main.scala.model._
import main.scala.view.BoardScene

import scala.collection.mutable.ListBuffer
import main.scala.model.Board

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class Game(_boardScene: BoardScene) {
  private var _board = Board();
  private var _players: List[PlayerAbstract] = List()
  private var _playersCount = 0

  def nextTurn(): Unit ={
    checkIfGameEnd()
    _players(getCurrentPlayerIndex()).makeMove(-1,-1)
  }

  def initGame(
                boardWidth: Int,
                boardLength: Int,
                pawnRows: Int,
                playersCount: Int
              ) : Board = {
    _board = Board();
    val playerListBuilder = ListBuffer[Player]()
    _playersCount = playersCount
    playerListBuilder += new Player(_board, PlayerType.WHITE, this, _boardScene)
    playerListBuilder += new Player(_board, PlayerType.BLACK, this, _boardScene)
    _players = playerListBuilder.toList
    _board
  }

  def takeAction(x:Int, y:Int) : Unit ={
    _players(getCurrentPlayerIndex()).makeMove(x,y)
  }

  def getCurrentPlayerIndex(): Int = {
    if(_board.getCurrentPlayer() == PlayerType.WHITE) {
      return 0;
    }
    return 1;
  }

  def checkIfGameEnd(): Unit =
  {
    if (_board.isGameEnd()) {
      var message = "";
      if (_board.currentPlayer == PlayerType.BLACK) {
        message = "White Player Won"
      }
      else if (_board.currentPlayer == PlayerType.WHITE) {
        message = "Black Player Won"
      }
      new Alert(AlertType.Information, message).showAndWait();
      _boardScene.close()
    }
  }
}