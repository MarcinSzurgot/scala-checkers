import PawnType._

import scala.collection.mutable.ListBuffer

/**
  * Created by bzielinski91 on 28.08.2016.
  */
class Board(val boardState: Array[Array[PawnType]]) {
  private var brdState = boardState

//  def move(x:Int, y:Int, toX:Int,toY:Int): Unit ={
//  }


  def getMoves(x:Int,y:Int): ListBuffer[Point] ={
    var listOfMoves = new ListBuffer[Point]()


      //dla Pionków
      if(brdState(x)(y)==WHITE){
        if(y+1<7) {
          if (x + 1 < 7 &&  brdState(x + 1)(y + 1) == EMPTY) {
            listOfMoves.+=:(new Point(x + 1, y + 1))
          }
          if (x - 1 < 7 && brdState(x - 1)(y + 1) == EMPTY) {
            listOfMoves.+=:(new Point(x - 1, y + 1))
          }
        }
      }
      if(brdState(x)(y)==BLACK){
        if(y-1>=0) {
          if (x + 1 < 7 && brdState(x + 1)(y - 1) == EMPTY) {
            listOfMoves.+=:(new Point(x + 1, y - 1))
          }
          if (x - 1 < 7 && brdState(x - 1)(y - 1) == EMPTY) {
            listOfMoves.+=:(new Point(x - 1, y - 1))
          }
        }
      }
      //dla damek
      if(brdState(x)(y) == BLACK_PROMOTED || brdState(x)(y) == WHITE_PROMOTED) {

      }


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
