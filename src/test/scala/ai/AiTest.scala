package test.scala.ai

import org.scalatest.FunSuite

import main.scala.ai.CheckAi
import main.scala.model.PlayerType
import main.scala.model.Board
import main.scala.model.PawnType._

class AiTest extends FunSuite{
  type PlayerType = PlayerType.PlayerType;
  
  test("computePointsFor"){
    var ai = new CheckAi(Board(), PlayerType.WHITE);
    val white = ai.getPointsFor(PlayerType.WHITE);
    val black = ai.getPointsFor(PlayerType.BLACK);
    val expec = CheckAi.cols * CheckAi.pawnRows / 2;
    
    assertResult(expec)(white);
    assert(white.equals(black));
  }
  
  test("playerPawnCount"){
    var ai = new CheckAi(Board(), PlayerType.WHITE);
    val black = ai.board.blackCount;
    val white = ai.board.whiteCount;   
    
    assertResult(12)(white);
    assertResult(12)(black);
  }
  
  test("computePoints"){
    var ai = new CheckAi(Board(), PlayerType.WHITE);
  }
}