package main.scala.model

object PlayerType extends Enumeration {
    type PlayerType = Value;  
    val WHITE = Value;
    val BLACK = Value;
    
    def toggle(player: PlayerType): PlayerType = player match{
      case WHITE => BLACK;
      case BLACK => WHITE;
    }
}