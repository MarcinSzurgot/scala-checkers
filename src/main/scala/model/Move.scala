package main.scala.model

class Move(var begin: Position, var end: Position) {
  def this(){
    this(Position(), Position());
  }
  
  override def toString(): String = {
    return "Begin: " + begin + "\n" + "End: " + end;
  }

  override def equals(a: Any): Boolean = a match {
    case that: Move => begin == that.begin && end == that.end;
    case _          => false;
  }
}

object Move{
  def apply(begin: Position, end: Position): Move = {
    return new Move(begin, end);
  }
}