package test.scala.ai

import org.scalatest.FunSuite

import main.scala.ai.CheckAi
import main.scala.ai.Player._;
import main.scala.ai.Board;
import main.scala.ai.Player
import main.scala.model.PawnType._;
import main.scala.model.PawnType


class AiTest extends FunSuite{
  
  test("computePointsFor"){
    var ai = new CheckAi(Board.createDefaultBoard(), Player.WHITE);
    val white = ai.getPointsFor(Player.WHITE);
    val black = ai.getPointsFor(Player.BLACK);
    val expec = CheckAi.cols * CheckAi.pawnRows / 2;
    
    assertResult(expec)(white);
    assert(white.equals(black));
  }
  
  test("playerPawnCount"){
    var ai = new CheckAi(Board.createDefaultBoard(), Player.WHITE);
    val black = ai.board.blackCount;
    val white = ai.board.whiteCount;   
    
    assertResult(12)(white);
    assertResult(12)(black);
  }
  
  test("computePoints"){
    var ai = new CheckAi(Board.createDefaultBoard(), Player.WHITE);
  }
}