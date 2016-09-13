package main.scala.logic

import main.scala.model._
import main.scala.view.BoardScene

import scala.collection.mutable.ListBuffer

class Game(_boardScene: BoardScene) {
  private var _board = new BoardBuilder().createBoard(8,8,3)
  private var _players: List[PlayerAbstract] = List()
  private var _currentPlayerIndex = 1
  private var _playersCount = 0

  def nextTurn(): Unit ={
    _currentPlayerIndex += 1
    _players(_currentPlayerIndex%_playersCount).makeMove(-1,-1)
  }

  def initGame(
                boardWidth: Int,
                boardLength: Int,
                pawnRows: Int,
                playersCount: Int
              ) : Board = {
    _board = new BoardBuilder().createBoard(boardWidth,boardLength,pawnRows)
    val playerListBuilder = ListBuffer[Player]()
    _playersCount = playersCount
    for( a <- 1 until _playersCount+1){
      playerListBuilder += new Player(_board, a, this, _boardScene)
    }
    _players = playerListBuilder.toList
    _board
  }

  def takeAction(x:Int, y:Int) : Unit ={
    _players(_currentPlayerIndex%_playersCount).makeMove(x,y)
  }
}