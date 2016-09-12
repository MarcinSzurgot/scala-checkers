package main.scala.logic

import main.scala.model.{PawnType, Board, BoardBuilder, Player}
import main.scala.view.BoardScene

import scalafx.geometry.Point2D


class Game(_boardScene: BoardScene) {
  private var _board = new BoardBuilder().createBoard(8,8,3)
  private var selectedPawn = new Point2D(-1,-1)
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

  def takeAction(x:Int, y:Int) : Unit ={
    if(selectedPawn.x != -1 || selectedPawn.y != -1) {
      var possibleEnd = new Point2D(x,y)
      if(_board.getMoves(selectedPawn.x.toInt,selectedPawn.y.toInt).contains(possibleEnd)){
        _boardScene.clearSelected()
        print("asdasdsas")
        _board.move(selectedPawn,possibleEnd)
        _boardScene.updatePosition(selectedPawn,possibleEnd)
        selectedPawn = new Point2D(-1,-1)
      }
    }
    else if(_board.getPawn(x,y) != PawnType.EMPTY){
      _boardScene.clearSelected()
      selectedPawn = new Point2D(x,y)
      _boardScene.markSelectedField(_board.getMoves(x,y) ::: List(new Point2D(x,y)))
    }

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