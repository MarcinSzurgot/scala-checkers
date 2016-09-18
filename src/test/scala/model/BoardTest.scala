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
    var player = PlayerType.BLACK;
    var blackProm = Position(5, 2);
    var whitePawn = Position(3, 4);
    var pawns = List((blackProm, BLACK_PROMOTED), (whitePawn, WHITE));
    var beat1 = (Move(blackProm, Position(whitePawn.row - 1, whitePawn.col + 1)), whitePawn, WHITE);
    var beat2 = (Move(blackProm, Position(whitePawn.row - 2, whitePawn.col + 2)), whitePawn, WHITE);
    var beat3 = (Move(blackProm, Position(whitePawn.row - 3, whitePawn.col + 3)), whitePawn, WHITE);
    var beats = List(beat1, beat2, beat3);

    testBeats(player, pawns, beats);

    var blackPawn = Position(2, 3);
    whitePawn = Position(3, 4);
    beat1 = (Move(whitePawn, Position(blackPawn.row - 1, blackPawn.col - 1)), blackPawn, BLACK);

    player = PlayerType.WHITE;
    pawns = List((blackPawn, BLACK), (whitePawn, WHITE));
    beats = List(beat1);
    testBeats(player, pawns, beats);

    player = PlayerType.WHITE;
    blackPawn = Position(4, 3);
    whitePawn = Position(5, 2);
    var whiteProm = Position(6, 5);
    pawns = List((blackPawn, BLACK), (whitePawn, WHITE), (whiteProm, WHITE_PROMOTED));
    var beatWhite = (Move(whitePawn, Position(blackPawn.row - 1, blackPawn.col + 1)), blackPawn, BLACK);
    var beatProm1 = (Move(whiteProm, Position(blackPawn.row - 1, blackPawn.col - 1)), blackPawn, BLACK);
    var beatProm2 = (Move(whiteProm, Position(blackPawn.row - 2, blackPawn.col - 2)), blackPawn, BLACK);
    var beatProm3 = (Move(whiteProm, Position(blackPawn.row - 3, blackPawn.col - 3)), blackPawn, BLACK);
    beats = List(beatWhite, beatProm1, beatProm2, beatProm3);

    testBeats(player, pawns, beats);
  }

  def testBeats(current: PlayerType, pawns: List[(Position, PawnType)],
                beats: List[(Move, Position, PawnType)]) {
    val state = Array.fill(Board.DEFAULT_ROWS, Board.DEFAULT_COLS)(EMPTY);
    pawns.foreach { pawn => state(pawn._1.row)(pawn._1.col) = pawn._2; }
    var board = Board(current, state);
    var moves = board.getAllMoves();

    assert(moves._1.length == beats.length);

    for (beat <- beats) {
      assert(moves._1.contains(beat._1));
      if(beat._2 != null) {
        assert(moves._2.contains((beat._2, beat._3)));
      }
    }
  }

  test("isGameEndTrue"){
    val modelSt = Array(
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
      Array(WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, WHITE_PROMOTED, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
      var boardTest = Board(PlayerType.WHITE,modelSt);
      assert(boardTest.isGameEnd()==true)
  }

  test("isGameEndFalse"){
    var boardTest = Board(PlayerType.WHITE,modelState);
    assert(boardTest.isGameEnd()==false)
  }

  test("isGameEndTrueForBlockedBLACK"){
    val modelBlocked = Array(
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
      Array(WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY),
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, EMPTY),
      Array(BLACK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WHITE, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BLACK),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
    var boardTest = Board(PlayerType.BLACK,modelBlocked);
    assert(boardTest.isGameEnd()==true)
  }

  test("isGameEndTrueForBlockedWHITE"){
    val modelBlockedWhite = Array(
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(WHITE, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, BLACK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WHITE),
      Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY),
      Array(EMPTY, BLACK, EMPTY, EMPTY, EMPTY, BLACK, EMPTY, BLACK),
      Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY));
    var boardTest = Board(PlayerType.WHITE,modelBlockedWhite);
    assert(boardTest.isGameEnd()==true)
  }

  test("isGameEndTrueForBlockedWHITE_PROMOTED"){
    val modelBlockedWhite = Array(
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, BLACK_PROMOTED, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, BLACK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(WHITE_PROMOTED, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, BLACK, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WHITE),
      Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY),
      Array(EMPTY, BLACK, EMPTY, EMPTY, EMPTY, BLACK, EMPTY, BLACK),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
    var boardTest = Board(PlayerType.WHITE,modelBlockedWhite);
    assert(boardTest.isGameEnd()==true)
  }

  test("isGameEndTrueForBlockedBLACK_PROMOTED"){
    val modelBlocked = Array(
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY),
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, EMPTY),
      Array(BLACK_PROMOTED, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WHITE, EMPTY),
      Array(EMPTY, WHITE, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BLACK),
      Array(EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
    var boardTest = Board(PlayerType.BLACK,modelBlocked);
    assert(boardTest.isGameEnd()==true)
  }


  test("makeMoveBeatingForMultipleBeating") {
    var player = PlayerType.BLACK;
    var blackPawn1 = Position(0, 3);
    var blackPawn2 = Position(6, 7);
    var whitePawn1 = Position(1, 2);
    var whitePawn2 = Position(1, 4);
    var whitePawn3 = Position(1, 6);
    var whitePawn4 = Position(5, 4);
    var whitePawn5 = Position(5, 6);
    var whitePawn6 = Position(7, 6);
    var pawns = List((blackPawn1, BLACK),(blackPawn2, BLACK), (whitePawn1, WHITE),(whitePawn2, WHITE),(whitePawn3, WHITE),(whitePawn4, WHITE),(whitePawn5, WHITE),(whitePawn6, WHITE));
    var beat1 = (Move(blackPawn1, Position(whitePawn1.row + 1, whitePawn1.col - 1)),whitePawn1,WHITE);
    var beat2 = (Move(blackPawn1, Position(whitePawn2.row + 1, whitePawn2.col + 1)),whitePawn2,WHITE);
    var beat3 = (Move(blackPawn2, Position(whitePawn5.row - 1, whitePawn5.col - 1)),whitePawn5,WHITE);
    var beats = List(beat1, beat2, beat3);

    testBeats(player, pawns, beats);
  }

  test("makeMultipleBeating"){
    val modelForBeating = Array(
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
      Array(EMPTY, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY),
      Array(EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE, EMPTY, WHITE),
      Array(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
      Array(EMPTY, WHITE, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY),
      Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY),
      Array(EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK),
      Array(BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY, BLACK, EMPTY));
      var boardTest = Board(PlayerType.BLACK,modelForBeating);
      boardTest.makeMove(Move(Position(5,0),Position(3,2)))
      boardTest.makeMove(Move(Position(3,2),Position(1,0)))
      assert(boardTest.getCurrentPlayer()==PlayerType.WHITE)
  }

}
