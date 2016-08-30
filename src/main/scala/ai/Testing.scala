package main.scala.ai

import main.scala.model.PawnType
import main.scala.model.PawnType._
import main.scala.model.Board;

object Testing {

  def createBoard(width: Int, height: Int, rows: Int): Array[Array[PawnType]] = {
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

  def main(args: Array[String]) {
    var board = new Board(createBoard(8, 8, 3));
    for(x <- 0 to 7){
      for(y <- 0 to 7){
        val p = board.getPawn(x, y);
        print("Position - x: " + x + ", y: " +  y +
            " Pawn Type: " + p + ", moves: ");
    	  if(p == WHITE){
    	    print(board.getMovesForWHITE(x, y) + ", ");
    	  }else{
    	    print(board.getMovesForBLACK(x, y) + ", ");
    	  }
    	  println();
      }
    }
  }
}