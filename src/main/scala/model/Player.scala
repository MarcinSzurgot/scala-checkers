package main.scala.model

import main.scala.logic.Game
import main.scala.view.BoardScene

import scalafx.geometry.Point2D

class Player(_board: Board, _player: Int, _game: Game, _boardScene : BoardScene) extends PlayerAbstract {

  private var selectedPawn = new Position(-1,-1)
  def player = _player

  override def makeMove(x: Int, y: Int) {
    if(x == -1 || y == -1) {
      return
    }

    if(selectedPawn.col != -1 || selectedPawn.row != -1) {
      val move = new Move(selectedPawn, new Position(x,y))
      var i = 0;
      val moves = _board.getMoves(selectedPawn.row,selectedPawn.col)
      moves._1.foreach{
        e =>
          if(e.equals(move)) {
          _boardScene.clearSelected()
          _board.makeMove(move)
          _boardScene.updatePosition(move)
          if (moves._2(i) != null) {
            _boardScene.clearPawn(moves._2(i)._1)
          }
          selectedPawn = new Position(-1,-1)
          return
        }
          i += 1
      }
      if(_board.getMoves(selectedPawn.row,selectedPawn.col)._1.contains(move)){

      }
    }
    if(_board.getPawn(x,y) != PawnType.EMPTY && _board.getMoves(x,y)._1.nonEmpty){
      _boardScene.clearSelected()
      selectedPawn = new Position(x,y)
      _boardScene.markSelectedFields(_board.getMoves(x,y))
    }
  }
}
