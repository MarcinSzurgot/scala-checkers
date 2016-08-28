import PawnType._

import scala.collection.mutable.ListBuffer

/**
  * Created by bzielinski91 on 28.08.2016.
  */
class Board(val boardState: Array[Array[PawnType]]) {
  private var brdState = boardState

//  def move(x:Int, y:Int, toX:Int,toY:Int): Unit ={
//  }

  def getMovesForWHITE(x:Int,y:Int): List[Point] ={
    var listOfMoves = new ListBuffer[Point]()
    if(brdState(x)(y)==WHITE) {
      if (y + 1 < 7) {
        if (x + 1 < 7 && brdState(x + 1)(y + 1) == EMPTY) {
          listOfMoves.+=(new Point(x + 1, y + 1))
        }
        if (x - 1 < 7 && brdState(x - 1)(y + 1) == EMPTY) {
          listOfMoves.+=(new Point(x - 1, y + 1))
        }
      }
    }
    listOfMoves.toList
  }

  def getMovesForBLACK(x:Int,y:Int): List[Point] ={
    var listOfMoves = new ListBuffer[Point]()
    if(brdState(x)(y)==BLACK){
      if(y-1>=0) {
        if (x + 1 < 7 && brdState(x + 1)(y - 1) == EMPTY) {
          listOfMoves.+=(new Point(x + 1, y - 1))
        }
        if (x - 1 < 7 && brdState(x - 1)(y - 1) == EMPTY) {
          listOfMoves.+=(new Point(x - 1, y - 1))
        }
      }
    }
    listOfMoves.toList
  }

  def getMovesForPROMOTED(x:Int,y:Int): List[Point] ={
    var listOfMoves = new ListBuffer[Point]()
    if(brdState(x)(y)==WHITE_PROMOTED || brdState(x)(y)==BLACK_PROMOTED) {

      var xTmp=x+1
      var yTmp=y+1
      while (xTmp<8 && yTmp<8 && brdState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point(xTmp,yTmp))
        xTmp +=1
        yTmp +=1
      }

      xTmp=x-1
      yTmp=y-1
      while (xTmp>=0 && yTmp>=0 && brdState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point(xTmp,yTmp))
        xTmp -=1
        yTmp -=1
      }

      xTmp=x+1
      yTmp=y-1
      while (xTmp<8 && yTmp>=0 && brdState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point(xTmp,yTmp))
        xTmp +=1
        yTmp -=1
      }

      xTmp=x-1
      yTmp=y+1
      while (xTmp>=0 && yTmp<8 && brdState(xTmp)(yTmp)==EMPTY){
        listOfMoves.+=(new Point(xTmp,yTmp))
        xTmp -=1
        yTmp +=1
      }
    }
    listOfMoves.toList
  }

  def getMoves(x:Int,y:Int): List[Point] ={
    val listOfMoves: List[Point] = getMovesForWHITE(x,y):::getMovesForBLACK(x,y):::getMovesForPROMOTED(x,y)
    listOfMoves
  }

  def getPawn(x:Int,y:Int):  PawnType ={
    brdState(x)(y)
  }

  def getBoardState = brdState

  def printBoardState(): Unit ={
    for (i <- 0 to 7) {
      for (j <- 0 to 7) {
        print(" " + brdState(i)(j));
      }
      println();
    }
  }
}







/*
object Demo {
  def main(args: Array[String]) {
    var myMatrix = Array.ofDim[PawnType](8, 8)
    for (i <- 0 to 7) {
      for (j <- 0 to 7) {
        myMatrix(i)(j) = BLACK_PROMOTED
      }
    }

    val brd = new Board(myMatrix)
    brd.printBoardState()

    val pt = new Point(3,4)

    print("pkt: "+pt.getX)

  }
}
*/
