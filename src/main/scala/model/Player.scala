package main.scala.model

import main.scala.logic.Game
import main.scala.view.BoardScene

import scalafx.geometry.Point2D
import main.scala.model.Board

class Player(_board: Board, _player: Int, _game: Game, _boardScene : BoardScene) extends PlayerAbstract {

  private var selectedPawn = new Position(-1,-1)
  def player = _player

  override def makeMove(x: Int, y: Int) {
    if(x == -1 || y == -1) {
      return
    }
    if(selectedPawn.col != -1 || selectedPawn.row != -1) {
      val move = new Move(selectedPawn, new Position(x,y))
      if(_board.getMoves(selectedPawn.row,selectedPawn.col)._1.contains(move)){
        _boardScene.clearSelected()
        _board.makeMove(move)
        _boardScene.updatePosition(move)
        selectedPawn = new Position(-1,-1)
      }
    }
    else if(_board.getPawn(x,y) != PawnType.EMPTY){
      _boardScene.clearSelected()
      selectedPawn = new Position(x,y)
      _boardScene.markSelectedFields(_board.getMoves(x,y))
    }
  }
}
