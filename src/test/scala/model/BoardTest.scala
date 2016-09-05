package model

import main.scala.model.{PawnType, Board, BoardBuilder}
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.mutable.ListBuffer
import scalafx.geometry.Point2D

/**
  * Created by Matik on 30.08.2016.
  */
class BoardTest extends FunSuite with BeforeAndAfterEach {
  var board = new BoardBuilder().createBoard(8,8,3)
  override def beforeEach() {
    board = new BoardBuilder().createBoard(8,8,3)
  }

  override def afterEach() {
    board = null
  }

  test("testMove") {
    val testPawn = board.getPawn(1,1)
    board.move(new Point2D(1,1), new Point2D(2,2))
    assert(testPawn == board.getPawn(2,2))
  }

  test("testMoveForDifferentPawn") {
    val testPawn = board.getPawn(1,2)
    board.move(new Point2D(1,2), new Point2D(0,3))
    assert(testPawn == board.getPawn(0,3))
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
    for (move <- listOfMoves) println(move.x.toInt+", "+move.y.toInt)
    assert(listOfMoves.size==2)
  }

  test("testGetMovesForPROMOTED") {
    board.setPawn(5,6,PawnType.BLACK_PROMOTED)
    println("Pawn: "+board.getPawn(5,6))
    val listOfMoves: List[Point2D] = board.getMovesForPROMOTED(5,6)
    for (move <- listOfMoves) println(move.x.toInt + ", " + move.y.toInt)
    assert(listOfMoves.size==3)
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
    for (move <- listOfMoves) println(move.x.toInt + ", " + move.y.toInt)
    assert(listOfMoves.size==2)
  }

  test("getBeatingForPawn"){
    println("Pawn: "+board.getPawn(5,4))
    board.setPawn(4,5,PawnType.WHITE)
    board.setPawn(4,3,PawnType.WHITE)
    board.setPawn(1,4,PawnType.EMPTY)
    board.setPawn(2,5,PawnType.EMPTY)
    board.printBoardState()
    val listOfBeatings :scala.collection.mutable.Map[List[Point2D], Point2D] = board.getBeatingForPawn(5,4)
    for (beating <- listOfBeatings){
      println("End point: "+beating._2.x.toInt+", "+beating._2.y.toInt)
      for(pawnToBeating <- beating._1){
        println("Pawn to beating: "+pawnToBeating.x.toInt+", "+pawnToBeating.y.toInt)
      }
    }
    board.printBoardState()
    assert(listOfBeatings.size==2)
  }


  test("makeBeat"){
    println("Pawn: "+board.getPawn(5,4))
    val testPawn = board.getPawn(5,4)
    board.setPawn(4,5,PawnType.WHITE)
    board.setPawn(4,3,PawnType.WHITE)
    board.setPawn(1,4,PawnType.EMPTY)
    board.setPawn(2,5,PawnType.EMPTY)
    board.printBoardState()
    val listOfBeatings :scala.collection.mutable.Map[List[Point2D], Point2D] = board.getBeatingForPawn(5,4)
    val beating = listOfBeatings.head
    val listToRemove = beating._1
    val endingPoint = beating._2
    println("beating: "+beating)
    println("list to remove: "+listToRemove)
    board.makeBeat(new Point2D(5,4),listToRemove,endingPoint)
    board.printBoardState()
    assert(testPawn==board.getPawn(endingPoint.x.toInt,endingPoint.y.toInt))
  }


}
