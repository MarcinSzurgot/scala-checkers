package main.model;

class Pawn(var board: Board, var x: Int, var y: Int, player: Player) {
  def move(inHeight: Int, inWidth: Int) {
    var newX = x + inHeight;
    var newY = y + inWidth;
    if(newX < 0 || newX >= board.height || newY < 0 || newY >= board.width) {
      throw new Exception("Out of board");
    }
    board.setPawn(x,y, null)
    board.setPawn(newX, newY, this);
    x = newX;
    y = newY;
  }
  
}