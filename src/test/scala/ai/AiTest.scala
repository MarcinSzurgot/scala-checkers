package test.scala.ai

import org.scalatest.FunSuite

import main.scala.ai.CheckAi
import main.scala.ai.Player
import main.scala.model.Board
import main.scala.model.PawnType._


class AiTest extends FunSuite{
  
  val modelState = Array(
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
      Array(WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY),
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY),
      Array(EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK),
      Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY));
  
  test("computePointsFor"){
    var ai = new CheckAi(Board(), Player.WHITE);
    val white = ai.getPointsFor(Player.WHITE);
    val black = ai.getPointsFor(Player.BLACK);
    val expec = CheckAi.cols * CheckAi.pawnRows / 2;
    
    assertResult(expec)(white);
    assert(white.equals(black));
  }
  
  test("playerPawnCount"){
    var ai = new CheckAi(Board(), Player.WHITE);
    val black = ai.board.blackCount;
    val white = ai.board.whiteCount;   
    
    assertResult(12)(white);
    assertResult(12)(black);
  }
  
  test("computePoints"){
    var ai = new CheckAi(Board(), Player.WHITE);
  }
}