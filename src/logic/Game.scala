package main.logic;

import main.model._;

object Game {
  private var _board = new Board(8,8);
  private var _players: List[Player] = List();
  private var currentPlayerIndex = 1;
  private var _playersCount = 0;
  private var _gameEnd = false;
  
  def gameEnd = _gameEnd;
  def gameEnd_= (value: Boolean):Unit = _gameEnd = value 
  
  def initGame(
  boardWidth: Int, 
      boardLength: Int,
      playersCount: Int
  ) : Board = {
    _board = new Board(boardWidth, boardLength);
    _players = List();
    _playersCount = playersCount;
    for( a <- 1 until _playersCount){
        _players :+ new Player(_board, a);
    }
    
    return _board;
  }
  
  def runTurn(): Board = {
    _players(currentPlayerIndex%_playersCount).runTurn();
    if(_players(currentPlayerIndex%_playersCount).runEnded) {
      currentPlayerIndex +=1;
    }
    
    return _board;
  }
  
}