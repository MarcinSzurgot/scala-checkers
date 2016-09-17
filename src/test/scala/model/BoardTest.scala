package model

import main.scala.model.{ Board }
import main.scala.model.PawnType
import main.scala.model.Board
import org.scalatest.{ BeforeAndAfterEach, FunSuite }
import main.scala.model.PlayerType
import main.scala.model.PawnType._;
import scala.util.Random
import main.scala.model.Position
import main.scala.model.Move

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

    for (i <- 0 until 100) {
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

  test("makeMoveBeating") {
    var blackProm = Position(5, 2);
    var whitePawn = Position(3, 4);
    var beat1 = (Move(blackProm, Position(whitePawn.row - 1, whitePawn.col + 1)), whitePawn);
    var beat2 = (Move(blackProm, Position(whitePawn.row - 2, whitePawn.col + 2)), whitePawn);
    var beat3 = (Move(blackProm, Position(whitePawn.row - 3, whitePawn.col + 3)), whitePawn);

    var state = Array.fill(Board.DEFAULT_ROWS, Board.DEFAULT_COLS)(EMPTY);
    state(blackProm.row)(blackProm.col) = BLACK_PROMOTED;
    state(whitePawn.row)(whitePawn.col) = WHITE;

    var board = Board(PlayerType.BLACK, state);
    var moves = board.getAllMoves();

    assert(moves._1.length == 3);
    assert(moves._1.contains(beat1._1));
    assert(moves._1.contains(beat2._1));
    assert(moves._1.contains(beat3._1));
    assert(moves._2.contains((beat1._2, WHITE)));
    assert(moves._2.contains((beat2._2, WHITE)));
    assert(moves._2.contains((beat3._2, WHITE)));
    
    var blackPawn = Position(2, 3);
    whitePawn = Position(3, 4);
    beat1 = (Move(whitePawn, Position(blackPawn.row - 1, blackPawn.col - 1)), blackPawn);
    state = Array.fill(Board.DEFAULT_ROWS, Board.DEFAULT_COLS)(EMPTY);
    state(blackPawn.row)(blackPawn.col) = BLACK;
    state(whitePawn.row)(whitePawn.col) = WHITE;
    
    board = Board(PlayerType.WHITE, state);
    moves = board.getAllMoves();
    
    assert(moves._1.length == 1);
    assert(moves._1.contains(beat1._1));
    assert(moves._2.contains((beat1._2, BLACK)));
  }

}
