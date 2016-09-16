package main.scala.ai

import main.scala.ai.Player._
import main.scala.model.PawnType._
import main.scala.model.PawnType
import scalafx.geometry.Point2D
import scala.math._;

class CheckAi(var board: Board, val player: Player) {
  import CheckAi._;

  class Points(var black: Int, var white: Int);
  object Points { def zero(): Points = { return new Points(0, 0); } }

  var points: Points = null;
  var needUpdate: Boolean = true;

  def getPointsFor(player: Player): Int = player match {
    case Player.WHITE => getPoints().white;
    case Player.BLACK => getPoints().black;
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