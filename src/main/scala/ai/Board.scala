package main.scala.ai

import scala.collection.mutable.ListBuffer

import main.scala.model.PawnType.EMPTY
import main.scala.model.PawnType._;
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Stack

class Board(var state: Array[Array[PawnType]]) {

  import Board._;

  val blackCount = state.foreach(_.count { p => p == WHITE || p == WHITE_PROMOTED });
  val whiteCount = state.foreach(_.count { p => p == BLACK || p == BLACK_PROMOTED });

  var standingBlack = blackCount;
  var standingWhite = whiteCount;

  private var availBeat = false;
  private var currentPlayer = Player.WHITE;
  private var actionArray: ActionArray = null;
  private var allMoves: Action = null;
  private var previous: PreviousMoves = null;

  updateMoves();

  def this(rows: Int, cols: Int, pawnRows: Int) {
    this(Board.createBoardArray(cols, rows, pawnRows));
  }

  def getRowsCount(): Int = {
    return state.length;
  }

  def getColsCount(): Int = state.length match {
    case 0 => return 0;
    case _ => return state(0).length;
  }

  def getCurrentPlayer(): Player.Player = {
    return currentPlayer
  }

  def getMoves(row: Int, col: Int): Action = {
    return actionArray(row)(col);
  }

  def getAllMoves(): Action = {
    return allMoves;
  }

  def move(move: Move): Boolean = {
    val beg = getMoves(move.begin.row, move.begin.col);
    if (beg != null) {
      val i = beg._1.indexOf(move);

      if (i == -1) {
        return false;
      } else {
        println(beg);

        return true;
      }
    } else {
      return false;
    }
  }

  def updateMoves() {
    clear();

    for (row <- 0 to getRowsCount() - 1) {
      for (col <- 0 to getColsCount() - 1) {
        if ((isWhite(row, col) && currentPlayer == Player.WHITE) ||
          (isBlack(row, col) && currentPlayer == Player.BLACK)) {
          checkPawn(row, col);
        }
      }
    }
  }

  private def checkPawn(row: Int, col: Int) {
    val steps = getStepsCount(row, col);
    val up = currentPlayer == Player.WHITE;
    val pr = isPromoted(row, col);
    var beat: Beat = null;

    var upw = (x: Boolean, y: Boolean) => x || y; // if current pawn is white or promoted allow him move upward
    var bck = (x: Boolean, y: Boolean) => !x || y; // if current pawn is black or promoted allow him move backward
    var add = (x: Int, y: Int) => x + y;
    var sub = (x: Int, y: Int) => x - y;

    val funcs = List((add, add, upw), (add, sub, upw),
      (sub, add, bck), (sub, sub, bck));

    funcs.foreach { f =>
      beat = null;
      var cont = true;
      for (s <- 1 to steps) {
        if (cont) {
          var res = addMove(row, col, f._1(row, s), f._2(col, s), beat, f._3(up, pr));
          beat = res._1;
          cont = res._2;
        }
      }
    }
  }

  private def addMove(row: Int, col: Int, nrow: Int, ncol: Int, beat: Beat, up: Boolean): (Beat, Boolean) = {
    val rowc = getRowsCount();
    val colc = getColsCount();
    var pos: List[Int] = null;

    if (nrow >= 0 && nrow < rowc && ncol >= 0 && ncol < colc) {
      if (state(nrow)(ncol) == EMPTY && up) {
        if (beat != null) {
          pos = List(row, col, beat.row, beat.col, nrow, ncol);
        } else {
          pos = List(row, col, nrow, ncol);
        }
      } else if (isOpposite(nrow, ncol) && beat == null) {
        val (nnrow, nncol) = (2 * nrow - row, 2 * ncol - col);
        if (nnrow >= 0 && nnrow < rowc && nncol >= 0 &&
          nncol < colc && state(nnrow)(nncol) == EMPTY) {
          pos = List(row, col, nrow, ncol, nnrow, nncol);
        } else {
          return (null, false);
        }
      } else {
        return (null, false);
      }
      return (addMove(pos), true);
    } else {
      return (null, false);
    }
  }

  private def addMove(pos: List[Int]): Beat = {
    var beat: Beat = null;
    var move: Move = null;

    if (pos.length == 4) {
      move = new Move(
        new Position(pos(0), pos(1)),
        new Position(pos(2), pos(3)));
      beat = null;
    } else {
      setBeat();
      move = new Move(
        new Position(pos(0), pos(1)),
        new Position(pos(4), pos(5)));
      beat = new Beat(pos(2), pos(3));
    }

    actionArray(pos(0))(pos(1))._1.append(move);
    actionArray(pos(0))(pos(1))._2.append(beat);

    allMoves._1.append(move);
    allMoves._2.append(beat);
    return beat;
  }

  private def setBeat() {
    if (!availBeat) {
      clear();
      availBeat = true;
    }
  }

  private def clear() {
    actionArray = Array.ofDim[Action](getRowsCount(), getColsCount());
    actionArray.foreach { row =>
      for (col <- 0 until row.length) {
        row(col) = (ArrayBuffer[Move](), ArrayBuffer[Beat]());
      }
    };

    allMoves = (ArrayBuffer[Move](), ArrayBuffer[Beat]());
    if (previous == null) {
      previous = new PreviousMoves();
    }

    availBeat = false;
  }

  private def getStepsCount(row: Int, col: Int): Int = isPromoted(row, col) match {
    case true  => Math.max(getRowsCount(), getColsCount()) - 1;
    case false => 1;
  }

  private def isOpposite(row: Int, col: Int): Boolean = {
    return isWhite(row, col) && currentPlayer == Player.BLACK ||
      isBlack(row, col) && currentPlayer == Player.WHITE;
  }

  private def isPromoted(row: Int, col: Int): Boolean = state(row)(col) match {
    case WHITE_PROMOTED => true;
    case BLACK_PROMOTED => true;
    case _              => false;
  }

  private def isWhite(row: Int, col: Int): Boolean = state(row)(col) match {
    case WHITE          => true;
    case WHITE_PROMOTED => true;
    case _              => false;
  }

  private def isBlack(row: Int, col: Int): Boolean = state(row)(col) match {
    case BLACK          => true;
    case BLACK_PROMOTED => true;
    case _              => false;
  }
}

object Board {
  class Position(var row: Int, var col: Int) {
    override def toString(): String = {
      return "(" + row + "," + col + ")";
    }

    override def equals(a: Any): Boolean = a match {
      case that: Position => row == that.row && col == that.col;
      case _              => false;
    }
  }

  class Move(var begin: Position, var end: Position) {
    override def toString(): String = {
      return "Begin: " + begin + "\n" + "End: " + end;
    }

    override def equals(a: Any): Boolean = a match {
      case that: Move => begin == that.begin && end == that.end;
      case _          => false;
    }
  }

  type Beat = Position;
  type Action = (ArrayBuffer[Move], ArrayBuffer[Beat]);
  type ActionArray = Array[Array[Action]];
  type PreviousMoves = Stack[(Move, Beat)];

  val DEFAULT_ROWS = 8;
  val DEFAULT_COLS = 8;
  val DEFAULT_PAWN_ROWS = 3;

  def createDefaultBoard(): Board = {
    return new Board(DEFAULT_ROWS, DEFAULT_COLS, DEFAULT_PAWN_ROWS);
  }

  def createBoardArray(rows: Int, cols: Int, pawnRows: Int): Array[Array[PawnType]] = {
    var board = Array.fill(rows, cols)(EMPTY);
    val fill = (from: Int, to: Int, tp: PawnType) => {
      for (y <- from to to - 1) {
        for (x <- 0 to cols - 1) {
          if ((x + y) % 2 == 1) {
            board(y)(x) = tp;
          }
        }
      }
    }
    fill(0, pawnRows, WHITE);
    fill(rows - pawnRows, rows, BLACK);

    return board;
  }
}