package main.scala.model

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scalafx.geometry.Point2D
import main.scala.model.PawnType._

/**
  * Created by bzielinski91 on 28.08.2016.
  */
class Board(var _boardState: Array[Array[PawnType]]) {




  def getBeatingForPawn(x:Int,y:Int): scala.collection.mutable.Map[List[Point2D], Point2D] ={
    var listOfBeatings = scala.collection.mutable.Map[List[Point2D], Point2D]()
    var listOfPawnToRemove = new ListBuffer[Point2D]
    var pawnToBeating = new ListBuffer[PawnType]

    if(_boardState(x)(y) == WHITE) {
      pawnToBeating += BLACK
      pawnToBeating += BLACK_PROMOTED
    }
    if(_boardState(x)(y) == BLACK) {
      pawnToBeating += WHITE
      pawnToBeating += WHITE_PROMOTED
    }
    def checkBeating(_boardStateTmp: Array[Array[PawnType]], xTmp:Int, yTmp:Int, listOfPawnToRemove:ListBuffer[Point2D]) {
      var isEndBeatings: Boolean = true

      def checkSingleBeating(vertical:Int,horizontal:Int): Unit ={
        var _boardStateTmpTmp: Array[Array[PawnType]] = _boardStateTmp
        _boardStateTmpTmp(xTmp + vertical)(yTmp + horizontal) = EMPTY
        _boardStateTmpTmp(xTmp + (vertical*2))(yTmp + (horizontal*2)) = _boardStateTmpTmp(xTmp)(yTmp)
        _boardStateTmpTmp(xTmp)(yTmp) = EMPTY
        var listOfPawnToRemoveTmp = listOfPawnToRemove
        listOfPawnToRemoveTmp.+=(new Point2D(xTmp + vertical, yTmp + horizontal))
        isEndBeatings = false
        checkBeating(_boardStateTmpTmp, xTmp + (vertical*2), yTmp + (horizontal*2), listOfPawnToRemoveTmp);
      }
      if (xTmp < 6 && yTmp < 6) {
        if (pawnToBeating.contains(_boardStateTmp(xTmp + 1)(yTmp + 1)) && _boardStateTmp(xTmp + 2)(yTmp + 2) == EMPTY) {
          checkSingleBeating(1,1)
        }
      }
      if (xTmp < 6 && yTmp > 1) {
        if (pawnToBeating.contains(_boardStateTmp(xTmp + 1)(yTmp - 1)) && _boardStateTmp(xTmp + 2)(yTmp - 2) == EMPTY) {
          checkSingleBeating(1,-1)
        }
      }
      if (xTmp > 1 && yTmp > 1) {
        if (pawnToBeating.contains(_boardStateTmp(xTmp - 1)(yTmp - 1)) && _boardStateTmp(xTmp - 2)(yTmp - 2) == EMPTY) {
          checkSingleBeating(-1,-1)
        }
      }
      if (xTmp > 1 && yTmp < 6) {
        if (pawnToBeating.contains(_boardStateTmp(xTmp - 1)(yTmp + 1)) && _boardStateTmp(xTmp - 2)(yTmp + 2) == EMPTY) {
          checkSingleBeating(-1,1)
        }
      }
      if(isEndBeatings){
        val endingPointOfPawn = new Point2D(xTmp,yTmp)
        listOfBeatings += listOfPawnToRemove.toList -> endingPointOfPawn
        listOfPawnToRemove.clear()
      }
    }
    checkBeating(_boardState,x,y,listOfPawnToRemove);
    listOfBeatings
  }


  def move(startPoint: Point2D, endPoint: Point2D): Unit ={
    _boardState(endPoint.x.toInt)(endPoint.y.toInt) = _boardState(startPoint.x.toInt)(startPoint.y.toInt)
    _boardState(startPoint.x.toInt)(startPoint.y.toInt) = EMPTY
  }

  def getMovesForWHITE(x:Int,y:Int): List[Point2D] ={
    var listOfMoves = new ListBuffer[Point2D]()

    if(_boardState(x)(y)==WHITE) {
      if (x + 1 < 8) {
        if ((y + 1 < 8) && (_boardState(x + 1)(y + 1) == EMPTY)) {
          listOfMoves.+=(new Point2D(x + 1, y + 1))
        }
        if ((y - 1 >= 0) && (_boardState(x + 1)(y - 1) == EMPTY)) {
          listOfMoves.+=(new Point2D(x + 1, y - 1))
        }
      }
    }
    listOfMoves.toList
  }

  def getMovesForBLACK(x:Int,y:Int): List[Point2D] ={

    var listOfMoves = new ListBuffer[Point2D]()
    if(_boardState(x)(y)==BLACK){
      if(x-1>=0) {
        if (y + 1 < 8 && _boardState(x - 1)(y + 1) == EMPTY) {
          listOfMoves.+=(new Point2D(x - 1, y + 1))
        }
        if (y - 1 >= 0 && _boardState(x - 1)(y - 1) == EMPTY) {
          listOfMoves.+=(new Point2D(x - 1, y - 1))
        }
      }
    }
    listOfMoves.toList
  }

  def getMovesForPROMOTED(x:Int,y:Int): List[Point2D] ={
    var listOfMoves = new ListBuffer[Point2D]()
    if(_boardState(x)(y)==WHITE_PROMOTED || _boardState(x)(y)==BLACK_PROMOTED) {

      var xTmp=x+1
      var yTmp=y+1
      while (xTmp<8 && yTmp<8 && _boardState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point2D(xTmp,yTmp))
        xTmp +=1
        yTmp +=1
      }

      xTmp=x-1
      yTmp=y-1
      while (xTmp>=0 && yTmp>=0 && _boardState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point2D(xTmp,yTmp))
        xTmp -=1
        yTmp -=1
      }

      xTmp=x+1
      yTmp=y-1
      while (xTmp<8 && yTmp>=0 && _boardState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point2D(xTmp,yTmp))
        xTmp +=1
        yTmp -=1
      }

      xTmp=x-1
      yTmp=y+1
      while (xTmp>=0 && yTmp<8 && _boardState(xTmp)(yTmp)==EMPTY){
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
    _boardState(x)(y)
  }

  def setPawn(x:Int,y:Int,tp:PawnType): Unit ={
    _boardState(x)(y)=tp
  }

  def boardState = _boardState

  def printBoardState(): Unit ={
    for (i <- 0 to 7) {
      for (j <- 0 to 7) {
        print(" " + _boardState(i)(j));
      }
      println();
    }
  }


}
