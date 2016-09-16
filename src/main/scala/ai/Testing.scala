package main.scala.ai

import Board._;

object Testing {

  def main(args: Array[String]) {
    var board = Board.createDefaultBoard();
    board.move(new Move(new Position(2, 1), new Position(3, 2)));
    
    var beg = System.currentTimeMillis();
    for(i <- 0 until 100000){
      board.updateMoves();
    }
    var end = System.currentTimeMillis();
    println(end - beg);
  }
}