package main.scala.ai

import main.scala.logic.Game
import main.scala.model.PlayerAbstract
import main.scala.model.PlayerType
import main.scala.model.Board
import main.scala.model.Move
import main.scala.view.BoardScene

class CheckerAi(player: PlayerType.PlayerType, var board: Board, var depth: Int,
                _game: Game, _boardScene: BoardScene) extends PlayerAbstract {

  override def makeMove(row: Int, col: Int) {
    if (row == -1 && col == -1) {
      val move = testMoves();
      board.makeMove(move);
      //      _boardScene.clearSelected()
      _boardScene.clearBoard(board)
      _game.nextTurn()
    }
  }

  def makeMoveTest() {
    val move = testMoves();
    board.makeMove(move);
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
    var best = (Int.MinValue, 0);
    for (move <- 0 until moves._1.length) {
      board.makeMove(moves._1(move));
      val points = testMoves(depth - 1, false);
      board.undoMove();
      if (points > best._1) {
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
        val test = testMoves(depth - 1, !max);
        points = if (max) Math.max(points, test) else Math.min(points, test);
        board.undoMove();
      }
      return points;
    } else {
      return computePoints();
    }
  }
}

object CheckerAi {
  val DEFAULT_MAX_DEPTH = 5;

  type PlayerType = PlayerType.PlayerType;

  def apply(player: PlayerType.PlayerType, board: Board, _game: Game, _boardScene: BoardScene): CheckerAi = {
    return new CheckerAi(player, board, DEFAULT_MAX_DEPTH, _game, _boardScene);
  }

  def apply(player: PlayerType.PlayerType, board: Board, depth: Int,
            _game: Game, _boardScene: BoardScene): CheckerAi = {
    return new CheckerAi(player, board, depth, _game, _boardScene);
  }
}