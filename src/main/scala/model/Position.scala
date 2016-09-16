package main.scala.model

class Position(var row: Int, var col: Int) {
  def this(){
    this(0, 0);
  }
  
  override def toString(): String = {
    return "(" + row + "," + col + ")";
  }

  override def equals(a: Any): Boolean = a match {
    case that: Position => row == that.row && col == that.col;
    case _              => false;
  }
}

object Position{
  def apply(row: Int, col: Int): Position = {
    return new Position(row, col);
  }
  
  def apply(): Position = {
    return Position(0, 0);
  }
}