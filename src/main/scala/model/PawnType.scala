/**
 * Created by bzielinski91 on 28.08.2016.
 */
package main.scala.model

import scala.language.implicitConversions;

object PawnType extends Enumeration {
  type PawnType = PawnTypeVal;

  val EMPTY = new PawnTypeVal {
    override def isWhite: Boolean = return false;
    override def isBlack: Boolean = return false;
    override def isPromoted: Boolean = return false;
    override def isOpposite(other: PawnTypeVal): Boolean = return false;
  }

  val BLACK = new PawnTypeVal {
    override def isWhite: Boolean = return false;
    override def isBlack: Boolean = return true;
    override def isPromoted: Boolean = return false;
    override def isOpposite(other: PawnTypeVal): Boolean = {
      return other.isWhite;
    }
  }

  val WHITE = new PawnTypeVal {
    override def isWhite: Boolean = return true;
    override def isBlack: Boolean = return false;
    override def isPromoted: Boolean = return false;
    override def isOpposite(other: PawnTypeVal): Boolean = {
      return other.isBlack;
    }
  }

  val BLACK_PROMOTED = new PawnTypeVal {
    override def isWhite: Boolean = return false;
    override def isBlack: Boolean = return true;
    override def isPromoted: Boolean = return true;
    override def isOpposite(other: PawnTypeVal): Boolean = {
      return other.isWhite;
    }
  }

  val WHITE_PROMOTED = new PawnTypeVal {
    override def isWhite: Boolean = return true;
    override def isBlack: Boolean = return false;
    override def isPromoted: Boolean = return true;
    override def isOpposite(other: PawnTypeVal): Boolean = {
      return other.isBlack;
    }
  }

  protected abstract class PawnTypeVal extends Val() {
    def isWhite: Boolean;
    def isBlack: Boolean;
    def isPromoted: Boolean;
    def isOpposite(other: PawnTypeVal): Boolean;
  }
  
  def opposite(pawn: PawnType): PawnType = pawn match {
    case EMPTY => EMPTY;
    case WHITE => BLACK;
    case BLACK => WHITE;
    case WHITE_PROMOTED => BLACK_PROMOTED;
    case BLACK_PROMOTED => WHITE_PROMOTED;
  }
  
  def promote(pawn: PawnType): PawnType = pawn match{
    case EMPTY => EMPTY;
    case WHITE => WHITE_PROMOTED;
    case BLACK => BLACK_PROMOTED;
    case WHITE_PROMOTED => WHITE_PROMOTED;
    case BLACK_PROMOTED => BLACK_PROMOTED;
  }
  
  def depromote(pawn: PawnType): PawnType = pawn match {
    case EMPTY => EMPTY;
    case WHITE_PROMOTED => WHITE;
    case BLACK_PROMOTED => BLACK;
    case WHITE => WHITE;
    case BLACK => BLACK;
  }

  implicit def valueToPawnTypeVal(x: Value) = x.asInstanceOf[Val];
}

