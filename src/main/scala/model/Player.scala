package main.scala.model

import main.scala.logic.Game
import main.scala.view.BoardScene

import scalafx.geometry.Point2D

class Player(_board: Board, _player: Int, _game: Game, _boardScene : BoardScene) extends PlayerAbstract {

  private var selectedPawn = new Point2D(-1,-1)
  def player = _player

  override def makeMove(x: Int, y: Int) {
    if(x == -1 || y == -1) {
      return
    }
//    if(selectedPawn.x != -1 || selectedPawn.y != -1) {
//      var possibleEnd = new Point2D(x,y)
//      if(_board.getMoves(selectedPawn.x.toInt,selectedPawn.y.toInt).contains(possibleEnd)){
//        _boardScene.clearSelected()
//        _board.move(selectedPawn,possibleEnd)
//        _boardScene.updatePosition(selectedPawn,possibleEnd)
//        selectedPawn = new Point2D(-1,-1)
//      }
//    }
//    else if(_board.getPawn(x,y) != PawnType.EMPTY){
//      _boardScene.clearSelected()
//      selectedPawn = new Point2D(x,y)
//      _boardScene.markSelectedField(_board.getMoves(x,y) ::: List(new Point2D(x,y)))
//    }
  }
}
