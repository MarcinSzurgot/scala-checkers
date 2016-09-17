package main.scala.model

import main.scala.logic.Game
import main.scala.model.Board.PlayerType
import main.scala.view.BoardScene

class Player(_board: Board, _player: PlayerType, _game: Game, _boardScene : BoardScene) extends PlayerAbstract {

  private var selectedPawn = new Position(-1,-1)
  var _turnContinue = false;
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
          _boardScene.updatePosition(move, _board.getPawn(x,y))
          if (moves._2(i) != null) {
            _boardScene.clearPawn(moves._2(i)._1)
          }
          if (!checkAndSignalEndTurn(move.end)) {
            selectedPawn = new Position(-1,-1)
            _game.nextTurn()
          }
          return
        }
          i += 1
      }
    }
    if(!_turnContinue && _board.getPawn(x,y) != PawnType.EMPTY && _board.getMoves(x,y)._1.nonEmpty){
      _boardScene.clearSelected()
      selectedPawn = new Position(x,y)
      _boardScene.markSelectedFields(_board.getMoves(x,y))
    }
  }

  def checkAndSignalEndTurn(pawnPos: Position): Boolean = {
    val moves =_board.getMoves(pawnPos.row, pawnPos.col);
    if(moves._1.nonEmpty) {
      _turnContinue = true;
      selectedPawn = pawnPos
      _boardScene.markSelectedFields(_board.getMoves(pawnPos.row, pawnPos.col))

      return true
    }
    _turnContinue = false;
    return false
  }
}
