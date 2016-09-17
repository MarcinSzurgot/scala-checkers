package main.scala.ai

import main.scala.model.Board
import main.scala.model.PawnType
import main.scala.model.PlayerType

class CheckAi(var board: Board, val player: PlayerType.PlayerType) {
  type PlayerType = PlayerType.PlayerType;

  class Points(var black: Int, var white: Int);
  object Points { def zero(): Points = { return new Points(0, 0); } }

  var points: Points = null;
  var needUpdate: Boolean = true;

  def getPointsFor(player: PlayerType): Int = player match {
    case PlayerType.WHITE => getPoints().white;
    case PlayerType.BLACK => getPoints().black;
    case _            => -1;
  }

  def getPoints(): Points = {
    if (needUpdate) {
      needUpdate = false;
      points = computePoints(board);
    }
    return points;
  }

  def getBestMove() {
  }
  
  private def testMoves(state: Board, depth: Int){
  }

  private def computePoints(board: Board): Points = {
    var points = Points.zero();

    board.state.foreach { row =>
      row.foreach { pawn =>
        pawn match {
          case PawnType.BLACK          => points.black += 1;
          case PawnType.BLACK_PROMOTED => points.black += 1;
          case PawnType.WHITE          => points.white += 1;
          case PawnType.WHITE_PROMOTED => points.white += 1;
          case _                       => 0;
        }
      }
    }
    return points;
  }

  private def showBoardState() {
    board.state.foreach { row =>
      row.foreach { pawn => print(pawn + "\t"); };
      println();
    }
    println();
  }
}

object CheckAi {
  val rows = 8;
  val cols = 8;
  val pawnRows = 3;
}