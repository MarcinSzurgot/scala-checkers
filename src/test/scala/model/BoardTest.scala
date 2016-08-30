package model

import main.scala.model.{Board, BoardBuilder}
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

  }

  test("testGetMoves") {

  }

  test("testGetMovesForWHITE") {

  }

  test("testGetMovesForPROMOTED") {

  }

  test("testGetBoardState") {

  }

  test("testGetPawn") {

  }

  test("testCreateBoard") {

  }

  test("testGetMovesForBLACK") {

  }

}
