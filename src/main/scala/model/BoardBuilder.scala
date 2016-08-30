package main.scala.model

import main.scala.model.PawnType._

class BoardBuilder {
  def createBoardArray(width: Int, height: Int, rows: Int): Array[Array[PawnType]] = {
    var board = Array.fill(height, width)(EMPTY);
    val fill = (from: Int, to: Int, tp: PawnType) =>{
      for(y <- from to to - 1){
        val shift = y % 2;
        for(x <- 0 to width - 1){
          if((x + shift) % 2 == 1){
            board(y)(x) = tp;
          }
        }
      }
    }
    fill(0, rows, WHITE);
    fill(height - rows, height, BLACK);

    return board;
  }

  def createBoard(width: Int, height: Int, rows: Int): Board =
  {
    return new Board(createBoardArray(width,height,rows))
  }
}
