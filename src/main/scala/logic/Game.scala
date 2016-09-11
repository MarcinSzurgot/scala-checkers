package main.scala.logic

import main.scala.model.{Board, BoardBuilder, Player}
import main.scala.view.BoardScene


class Game(_boardScene: BoardScene) {
  private var _board = new BoardBuilder().createBoard(8,8,3)
//  private var _players: List[Player] = List();
//  private var currentPlayerIndex = 1;
//  private var _playersCount = 0;
//  private var _gameEnd = false;
//  private var _selectedPosition;
//
//  def gameEnd = _gameEnd;
//  def gameEnd_= (value: Boolean):Unit = _gameEnd = value
//
  def initGame(
                boardWidth: Int,
                boardLength: Int,
                pawnRows: Int,
                playersCount: Int
              ) : Board = {
    _board = new BoardBuilder().createBoard(8,8,3)
//    _players = List();
//    _playersCount = playersCount;
//    for( a <- 1 until _playersCount){
//      _players :+ new Player(_board, a);
//    }
    _board
  }
//
//  def runTurn(): Board = {
//    _players(currentPlayerIndex%_playersCount).runTurn();
//    if(_players(currentPlayerIndex%_playersCount).runEnded) {
//      currentPlayerIndex +=1;
//    }
//
//    return _board;
//  }
//
//  def getSelectedPosition = _selectedPosition

}