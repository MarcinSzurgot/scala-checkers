package model

object Board(var lenght: Int, var height: Int) {
  def this() = this(8, 8)
  private var _board = Array.ofDim[Pawn](height, length);
  
  def getPawn(x,y) : Pawn {
    return board(x)(y);
  }
  
  def setPawn(x,y, pawn: Pawn) {
    board(x)(y) = pawn;
  }
  
  def board = _board;
  
  def board_= (value: Array):Unit = _board = value 
}