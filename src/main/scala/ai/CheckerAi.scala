package main.scala.ai

import main.scala.logic.Game
import main.scala.model.PlayerAbstract
import main.scala.model.PlayerType
import main.scala.model.Board
import main.scala.model.Move
import main.scala.view.BoardScene

class CheckerAi(player: PlayerType.PlayerType, board: Board,  _game: Game, _boardScene : BoardScene) extends PlayerAbstract {
  import CheckerAi._;

  override def makeMove(row: Int, col: Int) {
    val move = testMoves();
    board.makeMove(move);
    _boardScene.clearSelected()
    _boardScene.updatePosition(move, board.getPawn(move.begin.row, move.begin.col))
    _game.nextTurn()
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

  def apply(player: PlayerType.PlayerType, board: Board,  _game: Game, _boardScene : BoardScene): CheckerAi = {
    return new CheckerAi(player, board, _game, _boardScene);
  }
}