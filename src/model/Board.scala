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
  
  def initBoard(rows: Int, players: List[Player]) {
      for( i <- 1 until rows){
        addPawnsToRow(i, players(0));
      }
      for( i <- height-1 until height-4){
        addPawnsToRow(i, players(1));
      }
  }
  
  def addPawnsToRow(x: Int, player: Player) {
     for( j <- 0 until _width) {
       if ((x+j)%2 == 1) {
          _board(x)(j) = new Pawn(this, x, j, player);
       }
     }
  }
  
  def width = _width;
  
  def height = _height;

  def board = _board;
  
  def board_= (value: Array[Array[Pawn]]):Unit = _board = value  
}