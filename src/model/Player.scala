package main.model

class Player(board: Board, var _player: Int) {
  def player = _player;
  
  def player_= (value: Int):Unit = _player = value  
  
  def runTurn() {
    
  }
}