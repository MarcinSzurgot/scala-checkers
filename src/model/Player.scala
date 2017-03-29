package main.model

class Player(board: Board, var _player: Int) {
  private var _runEnded = true;
  
  def runEnded = _runEnded;
  def runEnded_= (value: Boolean):Unit = _runEnded = value 
  def player = _player;
  def player_= (value: Int):Unit = _player = value  
  
  def runTurn() {
    
  }
  
  def checkMoves(): List[Position] {
    for()
  }
}