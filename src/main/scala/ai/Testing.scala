package main.scala.ai

import scala.util.Random
import main.scala.model.Move
import main.scala.model.Position
import main.scala.model.Board
import main.scala.model.PawnType

object Testing {

  def main(args: Array[String]) {
    val p = PawnType.BLACK;
    println(p);
    
    var board = Board();
    println(board.makeMove(Move(Position(2, 1), Position(3, 0))));
    println(board.makeMove(Move(Position(5, 6), Position(4, 5))));
    
    println(board.makeMove(Move(Position(3, 0), Position(4, 1))));
    println(board.makeMove(Move(Position(5, 2), Position(3, 0))));
    val test = Move(Position(1, 2), Position(2, 3));

    println(board);
    println(board.getBlackLeft());
    println(board.getWhiteLeft());
    
    board.reset();
    
    println(board);

    for (i <- 0 until 1) {
      var rnd = new Random;
      var count = 0;
      var moves = board.getAllMoves()._1;
      println(moves.length);
      while (board.getWhiteLeft() > 0 &&
        board.getBlackLeft() > 0 && !moves.isEmpty) {
        board.makeMove(moves(rnd.nextInt(moves.length)));
        count += 1;
        moves = board.getAllMoves()._1;
      }

      for (i <- 0 until count) {
        board.undoMove();
      }

      println(board.toString());
      println(board.getBlackLeft());
      println(board.getWhiteLeft());
      println("Moves count: " + count);
    }
  }
}