package main.scala.model

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Stack
import main.scala.ai.Player
import main.scala.model.PawnType._
import main.scala.model.Move
import main.scala.model.Position

class Board(var state: Array[Array[PawnType]]) {

  import Board._;

  val blackCount = state.foldLeft(0)((l, r) => l + r.count(isBlack(_)));
  val whiteCount = state.foldLeft(0)((l, r) => l + r.count(isWhite(_)));

  private var blackLeft = blackCount;
  private var whiteLeft = whiteCount;
  private var currentPlayer = Player.WHITE;

  private var availBeat = false;
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
  
  def getPawn(row: Int, col: Int): PawnType = {
    return state(row)(col);
  }
  
  def getPawn(pos: Position): PawnType = {
    return getPawn(pos.row, pos.col);
  }

  def undoMove(update: Boolean) {
    val undo = previous.pop();
    val (beg, end) = (undo._1.begin, undo._1.end);
    val beat = undo._2;

    val tmp = state(end.row)(end.col);
    state(end.row)(end.col) = EMPTY;
    if (!undo._3) {
      state(beg.row)(beg.col) = tmp;
    } else {
      tmp match {
        case WHITE_PROMOTED => state(beg.row)(beg.col) = WHITE;
        case BLACK_PROMOTED => state(beg.row)(beg.col) = BLACK;
      }
    }

    if (beat != null) {
      state(beat._1.row)(beat._1.col) = beat._2;
      changeState(beat._1, beat._2);
    }

    togglePlayer();
    
    if(update){
      updateMoves();
    }
  }
  
  def undoMove(){
    undoMove(true);
  }

  def makeMove(move: Move): Boolean = {
    return makeMove(move, true);
  }

  def makeMove(move: Move, update: Boolean): Boolean = {
    val beg = getMoves(move.begin.row, move.begin.col);
    if (beg != null) {
      val i = beg._1.indexOf(move);
      if (i == -1) {
        return false;
      } else {
        makeMoveUnchecked(move, beg._2(i), update);
        return true;
      }
    } else {
      return false;
    }
  }

  def updateMoves() {
    clear();

    for (row <- 0 until getRowsCount()) {
      for (col <- 0 until getColsCount()) {
        if ((isWhite(row, col) && currentPlayer == Player.WHITE) ||
          (isBlack(row, col) && currentPlayer == Player.BLACK)) {
          checkPawn(row, col);
        }
      }
    }
  }
  
  def getBlackLeft(): Int = {
    return blackLeft;
  }

  def getWhiteLeft(): Int = {
    return whiteLeft;
  }

  override def toString(): String = {
    var builder = StringBuilder.newBuilder;
    state.foreach { row =>
      row.foreach { pawn =>
        builder.append(pawn);
        builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }
  
  override def equals(a: Any): Boolean = a match {
    case that: Board => that.state.deep == state.deep;
    case _ => false;
  }
  
  def reset(){
    while(previous.nonEmpty){
      undoMove(false);
    }
  }

  private def changeState(pow: Position, pawn: PawnType) {
    val tmp = state(pow.row)(pow.col);
    state(pow.row)(pow.col) = pawn;
    
    if(pawn == EMPTY){
      if(isWhite(tmp)){
        whiteLeft -= 1;
      }else{
        blackLeft -= 1;
      }
    }else{
      if(isWhite(pawn)){
        whiteLeft += 1;
      }else{
        blackLeft += 1;
      }
    }
  }

  private def makeMoveUnchecked(move: Move, beat: Beat, update: Boolean) {
    val (b, e) = (move.begin, move.end);
    val tmp = state(b.row)(b.col);

    state(b.row)(b.col) = EMPTY;
    state(e.row)(e.col) = tmp;

    if (beat != null) {
      changeState(beat._1, EMPTY);
    }

    previous.push((move, beat, checkPromotion(e)));
    togglePlayer();

    if (update) {
      updateMoves();
    }
  }

  private def checkPromotion(pos: Position): Boolean = {
    if (state(pos.row)(pos.col) == WHITE && pos.row == getRowsCount() - 1) {
      state(pos.row)(pos.col) = WHITE_PROMOTED;
      return true;
    } else if (state(pos.row)(pos.col) == BLACK && pos.row == 0) {
      state(pos.row)(pos.col) = BLACK_PROMOTED;
      return true;
    } else {
      return false;
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
          pos = List(row, col, beat._1.row, beat._1.col, nrow, ncol);
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
      beat = (new Position(pos(2), pos(3)), state(pos(2))(pos(3)));
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

  private def togglePlayer() {
    currentPlayer match {
      case Player.BLACK => currentPlayer = Player.WHITE;
      case Player.WHITE => currentPlayer = Player.BLACK;
    }
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

  private def isBlack(pawn: PawnType): Boolean = pawn match {
    case BLACK          => true;
    case BLACK_PROMOTED => true;
    case _              => false;
  }

  private def isWhite(pawn: PawnType): Boolean = pawn match {
    case WHITE          => true;
    case WHITE_PROMOTED => true;
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
  type Beat = (Position, PawnType);
  type Action = (ArrayBuffer[Move], ArrayBuffer[Beat]);
  type ActionArray = Array[Array[Action]];
  type PreviousMoves = Stack[(Move, Beat, Boolean)];

  val DEFAULT_ROWS = 8;
  val DEFAULT_COLS = 8;
  val DEFAULT_PAWN_ROWS = 3;

  def apply(): Board = {
    return Board(DEFAULT_ROWS, DEFAULT_COLS, DEFAULT_PAWN_ROWS);
  }
  
  def apply(rows: Int, cols: Int, pawnRows: Int): Board = {
    return new Board(createBoardArray(rows, cols, pawnRows));
  }

  def createBoardArray(rows: Int, cols: Int, pawnRows: Int): Array[Array[PawnType]] = {
    var board = Array.fill(rows, cols)(EMPTY);
    val fill = (from: Int, to: Int, tp: PawnType) => {
      for (y <- from until to) {
        for (x <- 0 until cols) {
          if ((x + y) % 2 == 1) {
            board(y)(x) = null;
          }
        }
      }
    }
    fill(0, pawnRows, WHITE);
    fill(rows - pawnRows, rows, BLACK);

    return null;
  }
}