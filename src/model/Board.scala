package main.model

class Board(_width: Int, _height: Int) {
  def this() = this(8, 8)
  private var _board = Array.ofDim[Pawn](_height, _width);
  
  def getPawn(x: Int, y: Int) : Pawn = {
    return _board(x)(y);
  }
  
  def setPawn(x: Int, y: Int, pawn: Pawn) {
    board(x)(y) = pawn;
  }
  
  def width = _width;
  
  def height = _height;

  def board = _board;
  
  def board_= (value: Array[Array[Pawn]]):Unit = _board = value  
}