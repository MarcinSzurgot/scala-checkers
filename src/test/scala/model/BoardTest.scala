package model

import main.scala.model.{PawnType, Board, BoardBuilder}
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scalafx.geometry.Point2D

/**
  * Created by Matik on 30.08.2016.
  */
class BoardTest extends FunSuite with BeforeAndAfterEach {
  var board = new BoardBuilder().createBoard(8,8,3)
  override def beforeEach() {

  }

  override def afterEach() {
    board = null
  }

  test("testMove") {
    val testPawn = board.getPawn(1,1)
    board.move(new Point2D(1,1), new Point2D(2,2))
    assert(testPawn == board.getPawn(2,2))
  }

  test("testBoardState") {

  }

  test("testPrintBoardState") {
    board.printBoardState()
  }

  test("testGetMoves") {

  }

  test("testGetMovesForWHITE") {
    println("Pawn: "+board.getPawn(2,1))
    val listOfMoves: List[Point2D] = board.getMovesForWHITE(2,1)
    var i=0
    for (move <- listOfMoves){
      println(move.x.toInt+", "+move.y.toInt)
      i+=1
    }
    assert(i==2)
  }

  test("testGetMovesForPROMOTED") {
    board.setPawn(5,6,PawnType.BLACK_PROMOTED)
    println("Pawn: "+board.getPawn(5,6))
    val listOfMoves: List[Point2D] = board.getMovesForPROMOTED(5,6)
    var i=0
    for (move <- listOfMoves) {
      println(move.x.toInt + ", " + move.y.toInt)
      i += 1
    }
    assert(i==3)
  }

  test("testGetBoardState") {

  }

  test("testGetPawn") {

  }

  test("testCreateBoard") {

  }

  test("testGetMovesForBLACK") {
    println("Pawn: "+board.getPawn(5,6))
    val listOfMoves: List[Point2D] = board.getMovesForBLACK(5,6)
    var i=0
    for (move <- listOfMoves) {
      println(move.x.toInt + ", " + move.y.toInt)
      i+=1
    }
    assert(i==2)
  }


}
