package model

import main.scala.model.{ Board }
import main.scala.model.PawnType
import main.scala.model.Board
import org.scalatest.{ BeforeAndAfterEach, FunSuite }
import main.scala.model.PlayerType
import main.scala.model.PawnType._;
import scala.util.Random

/**
 * Created by Matik on 30.08.2016.
 */
class BoardTest extends FunSuite with BeforeAndAfterEach {

  type PlayerType = PlayerType.PlayerType;

  val modelState = Array(
    Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
    Array(WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY),
    Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
    Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
    Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
    Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY),
    Array(EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK),
    Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY));

  test("boardStateCorrectness") {
    val board = Board();
    assertResult(modelState)(board.state);
  }

  test("makeMoveAndReset") {
    var board = Board();

    while (!board.isGameEnd()) {
      val moves = board.getAllMoves();
      val move = moves._1.head;
      board.makeMove(move);
    }

    assert(modelState.deep != board.state.deep);

    board.reset();

    assertResult(modelState)(board.state);
  }

  test("makeMoveAndResetRandom") {
    var board = Board();
    var rnd = new Random();

    for(i <- 0 until 100){
      while (!board.isGameEnd()) {
        val moves = board.getAllMoves();
        val move = moves._1(rnd.nextInt(moves._1.length));
        board.makeMove(move);
      }
      
      assert(modelState.deep != board.state.deep);
      board.reset();
      assertResult(modelState)(board.state);  
    }
  }

}
