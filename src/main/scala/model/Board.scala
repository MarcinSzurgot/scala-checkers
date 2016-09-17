package main.scala.model

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Stack

import main.scala.model.PawnType.BLACK
import main.scala.model.PawnType.BLACK_PROMOTED
import main.scala.model.PawnType.EMPTY
import main.scala.model.PawnType.PawnType
import main.scala.model.PawnType.WHITE
import main.scala.model.PawnType.WHITE_PROMOTED

class Board(var currentPlayer: PlayerType.PlayerType, var state: Array[Array[PawnType]]) {

  import Board._;

  val blackCount = state.foldLeft(0)((l, r) => l + r.count(_.isBlack));
  val whiteCount = state.foldLeft(0)((l, r) => l + r.count(_.isWhite));

  private var blackLeft = blackCount;
  private var whiteLeft = whiteCount;

  private var availBeat = false;
  private var actionArray: ActionArray = null;
  private var allMoves: Action = null;
  private var previous: PreviousMoves = null;

  updateMoves();

  def this(rows: Int, cols: Int, pawnRows: Int) {
    this(PlayerType.WHITE, Board.createBoardArray(cols, rows, pawnRows));
  }
  
  def this(state: Array[Array[PawnType]]){
    this(PlayerType.WHITE, state);
  }

  def getRowsCount(): Int = {
    return state.length;
  }

  def getColsCount(): Int = state.length match {
    case 0 => return 0;
    case _ => return state(0).length;
  }

  def getCurrentPlayer(): PlayerType = {
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
      state(beg.row)(beg.col) = PawnType.depromote(tmp);
    }

    if (beat != null) {
      state(beat._1.row)(beat._1.col) = beat._2;
      changeState(beat._1, beat._2);
    }

    if(previous.nonEmpty){
      val prev = previous.top._2;
      if(prev == null || (prev != null && beat != null && prev._2 != beat._2)){
        togglePlayer();
      }
    }else{
      togglePlayer();
    }

    if (update) {
      updateMoves();
    }
  }

  def undoMove() {
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
        val pawn = state(row)(col);
        if (pawn.isWhite && currentPlayer == PlayerType.WHITE ||
          (pawn.isBlack && currentPlayer == PlayerType.BLACK)) {
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

  def isGameEnd(): Boolean = {
    return getBlackLeft() == 0 || getWhiteLeft() == 0 || getAllMoves()._1.isEmpty;
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
    case _           => false;
  }

  def reset() {
    while (previous.nonEmpty) {
      undoMove(false);
    }
    updateMoves();
  }
  
  def setCurrentPlayer(player: PlayerType){
    currentPlayer = player;
  }

  private def changeState(pow: Position, pawn: PawnType) {
    val tmp = state(pow.row)(pow.col);
    state(pow.row)(pow.col) = pawn;

    if (pawn == EMPTY) {
      if (tmp.isWhite) {
        whiteLeft -= 1;
      } else {
        blackLeft -= 1;
      }
    } else {
      if (pawn.isWhite) {
        whiteLeft += 1;
      } else {
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
    
    if(update){
      val beforeBeat = availBeat;
      updateMoves();
      
      if(!(availBeat && beforeBeat)){
    	  togglePlayer();        
    	  updateMoves();
      }      
    }
  }

  private def checkPromotion(pos: Position): Boolean = {
    if (state(pos.row)(pos.col) == WHITE && pos.row == getRowsCount() - 1) {
      state(pos.row)(pos.col) = PawnType.promote(WHITE);
      return true;
    } else if (state(pos.row)(pos.col) == BLACK && pos.row == 0) {
      state(pos.row)(pos.col) = PawnType.promote(BLACK);
      return true;
    } else {
      return false;
    }
  }

  private def checkPawn(row: Int, col: Int) {
    val steps = getStepsCount(row, col);
    val up = currentPlayer == PlayerType.WHITE;
    val pr = state(row)(col).isPromoted;
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
      var s = 1;
      while(s <= steps) {
        var res = addMove(row, col, f._1(row, s), f._2(col, s), beat, f._3(up, pr));
        if(beat == null && res != null){
          beat = res;
          s += 1;
        }
        s += 1;
      }
    }
  }

  private def addMove(row: Int, col: Int, nrow: Int, ncol: Int, beat: Beat, up: Boolean): Beat = {
    val rowc = getRowsCount();
    val colc = getColsCount();
    var pos: List[Int] = null;

    if (nrow >= 0 && nrow < rowc && ncol >= 0 && ncol < colc) {
      if (state(nrow)(ncol) == EMPTY && up) {
        if (beat != null) {
          pos = List(row, col, beat._1.row, beat._1.col, nrow, ncol);
        } else if(!availBeat){
          pos = List(row, col, nrow, ncol);
        }else{
          return null;
        }
      } else if (isOpposite(nrow, ncol) && beat == null) {
        val nnrow = if(nrow > row) nrow + 1 else nrow - 1;
        val nncol = if(ncol > col) ncol + 1 else ncol - 1;
        if (nnrow >= 0 && nnrow < rowc && nncol >= 0 &&
          nncol < colc && state(nnrow)(nncol) == EMPTY) {
          pos = List(row, col, nrow, ncol, nnrow, nncol);
        } else {
          return null;
        }
      } else {
        return null;
      }
      return addMove(pos);
    } else {
      return null;
    }
  }

  private def addMove(pos: List[Int]): Beat = {
    var beat: Beat = null;
    var move: Move = null;

    if (pos.length == 4) {
      move = Move(
        Position(pos(0), pos(1)),
        Position(pos(2), pos(3)));
      beat = null;
    } else {
      setBeat();
      move = Move(
        Position(pos(0), pos(1)),
        Position(pos(4), pos(5)));
      beat = (Position(pos(2), pos(3)), state(pos(2))(pos(3)));
    }

    actionArray(pos(0))(pos(1))._1.append(move);
    actionArray(pos(0))(pos(1))._2.append(beat);
    
    allMoves._1.append(move);
    allMoves._2.append(beat);
    return beat;
  }
  
  var counter = 0;

  private def setBeat() {
    if (!availBeat) {
      clear();
      availBeat = true;
    }
  }

  private def clear() {
    actionArray = Array.fill(getRowsCount(), getColsCount())(
        (ArrayBuffer[Move](), ArrayBuffer[Beat]()));

    allMoves = (ArrayBuffer[Move](), ArrayBuffer[Beat]());
    if (previous == null) {
      previous = new PreviousMoves();
    }
    availBeat = false;
  }

  private def togglePlayer() {
    currentPlayer = PlayerType.toggle(currentPlayer);
  }

  private def getStepsCount(row: Int, col: Int): Int = state(row)(col).isPromoted match {
    case true  => Math.max(getRowsCount(), getColsCount()) - 1;
    case false => 1;
  }

  private def isOpposite(row: Int, col: Int): Boolean = {
    val pawn = state(row)(col);
    return pawn.isWhite && currentPlayer == PlayerType.BLACK ||
      pawn.isBlack && currentPlayer == PlayerType.WHITE;
  }
}

object Board {
  type PlayerType = PlayerType.PlayerType;
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
    return new Board(PlayerType.WHITE, createBoardArray(rows, cols, pawnRows));
  }
  
  def apply(player: PlayerType, state: Array[Array[PawnType]]): Board = {
    return new Board(player, state);
  }

  def createBoardArray(rows: Int, cols: Int, pawnRows: Int): Array[Array[PawnType]] = {
    var board = Array.fill(rows, cols)(EMPTY);
    val fill = (from: Int, to: Int, tp: PawnType) => {
      for (y <- from until to) {
        for (x <- 0 until cols) {
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