package model;

class Pawn(board: Board, x: Int, y: Int) {
  def move(inHeight, inWidth) {
    newX = x + inHeight;
    newY = y + inWidth;
    if(newX < 0 || newX >= board.height || newY < 0 || newY >= board.width) {
      throw new Exception("Out of board");
    }
    board.setPawn(x,y, null)
    board.setPawn(newX, newY, this);
    x = newX;
    y = newY;
  }
  
}