package main.scala.ai

import main.scala.model.PlayerAbstract
import main.scala.model.PlayerType
import main.scala.model.Board


class CheckerAi(player: PlayerType.PlayerType, board: Board) extends PlayerAbstract{
	import CheckerAi._;
	
	override def makeMove(row: Int, col: Int){
	  
	}
	
//	private def computePoints
}

object CheckerAi{
  type PlayerType = PlayerType.PlayerType;
  
  def apply(player: PlayerType, board: Board): CheckerAi = {
    return new CheckerAi(player, board);
  }
}