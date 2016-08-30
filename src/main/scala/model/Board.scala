package main.scala.model

import scala.collection.mutable.ListBuffer
import scalafx.geometry.Point2D
import main.scala.model.PawnType._

/**
  * Created by bzielinski91 on 28.08.2016.
  */
class Board(var boardState: Array[Array[PawnType]]) {

  def move(startPoint: Point2D, endPoint: Point2D): Unit ={
    boardState(endPoint.x.toInt)(endPoint.y.toInt) = boardState(startPoint.x.toInt)(startPoint.y.toInt)
    boardState(startPoint.x.toInt)(startPoint.y.toInt) = EMPTY
  }

  def getMovesForWHITE(x:Int,y:Int): List[Point2D] ={
    var listOfMoves = new ListBuffer[Point2D]()
    if(boardState(x)(y)==WHITE) {
      if (y + 1 < 7) {
        if (x + 1 < 7 && boardState(x + 1)(y + 1) == EMPTY) {
          listOfMoves.+=(new Point2D(x + 1, y + 1))
        }
        if (x - 1 < 7 && boardState(x - 1)(y + 1) == EMPTY) {
          listOfMoves.+=(new Point2D(x - 1, y + 1))
        }
      }
    }
    listOfMoves.toList
  }

  def getMovesForBLACK(x:Int,y:Int): List[Point2D] ={
    var listOfMoves = new ListBuffer[Point2D]()
    if(boardState(x)(y)==BLACK){
      if(y-1>=0) {
        if (x + 1 < 7 && boardState(x + 1)(y - 1) == EMPTY) {
          listOfMoves.+=(new Point2D(x + 1, y - 1))
        }
        if (x - 1 < 7 && boardState(x - 1)(y - 1) == EMPTY) {
          listOfMoves.+=(new Point2D(x - 1, y - 1))
        }
      }
    }
    listOfMoves.toList
  }

  def getMovesForPROMOTED(x:Int,y:Int): List[Point2D] ={
    var listOfMoves = new ListBuffer[Point2D]()
    if(boardState(x)(y)==WHITE_PROMOTED || boardState(x)(y)==BLACK_PROMOTED) {

      var xTmp=x+1
      var yTmp=y+1
      while (xTmp<8 && yTmp<8 && boardState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point2D(xTmp,yTmp))
        xTmp +=1
        yTmp +=1
      }

      xTmp=x-1
      yTmp=y-1
      while (xTmp>=0 && yTmp>=0 && boardState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point2D(xTmp,yTmp))
        xTmp -=1
        yTmp -=1
      }

      xTmp=x+1
      yTmp=y-1
      while (xTmp<8 && yTmp>=0 && boardState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point2D(xTmp,yTmp))
        xTmp +=1
        yTmp -=1
      }

      xTmp=x-1
      yTmp=y+1
      while (xTmp>=0 && yTmp<8 && boardState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point2D(xTmp,yTmp))
        xTmp -=1
        yTmp +=1
      }
    }
    listOfMoves.toList
  }

  def getMoves(x:Int,y:Int): List[Point2D] ={
    val listOfMoves: List[Point2D] = getMovesForWHITE(x,y):::getMovesForBLACK(x,y):::getMovesForPROMOTED(x,y)
    listOfMoves
  }

  def getPawn(x:Int,y:Int):  PawnType ={
    boardState(x)(y)
  }

  def getBoardState = boardState

  def printBoardState(): Unit ={
    for (i <- 0 to 7) {
      for (j <- 0 to 7) {
        print(" " + boardState(i)(j));
      }
      println();
    }
  }
}
