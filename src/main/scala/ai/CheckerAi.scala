package main.scala.ai

import main.scala.model.PlayerAbstract
import main.scala.model.PlayerType
import main.scala.model.Board
import main.scala.model.Move

class CheckerAi(player: PlayerType.PlayerType, board: Board) extends PlayerAbstract {
  import CheckerAi._;

  override def makeMove(row: Int, col: Int) {
    board.makeMove(testMoves());
  }

  def computePoints(): Int = {
    val white = if (player == PlayerType.WHITE)
      board.getWhiteLeft() else -board.getWhiteLeft();
    val black = if (player == PlayerType.BLACK)
      board.getBlackLeft() else -board.getBlackLeft();

    val points = white + black;
    return points;
  }

  private def testMoves(): Move = {
    board.updateMoves();
    val moves = board.getAllMoves();
    var best = (Int.MinValue, -1);
    for(move <- 0 until moves._1.length){
      board.makeMove(moves._1(move));
    	val points = testMoves(MAX_DEPTH - 1, false);
    	board.undoMove();
      if(points > best._1){
        best = (points, move);
      }
    }
    return moves._1(best._2);
  }

  private def testMoves(depth: Int, max: Boolean): Int = {
    if (depth != 0) {
    	board.updateMoves();
    	val moves = board.getAllMoves();
      var points = if (max) Int.MinValue else Int.MaxValue;
      for (move <- moves._1) {
        board.makeMove(move);
        points = if (max) Math.max(testMoves(depth - 1, !max), points)
        else Math.min(testMoves(depth - 1, !max), points);
        board.undoMove();
      }
      return points;
    } else {
      return computePoints();
    }
  } 
}

object CheckerAi {
  val MAX_DEPTH = 5;

  type PlayerType = PlayerType.PlayerType;

  def apply(player: PlayerType, board: Board): CheckerAi = {
    return new CheckerAi(player, board);
  }
}