/**
 * Created by bzielinski91 on 28.08.2016.
 */
package main.scala.model

import scala.language.implicitConversions; 

object PawnType extends Enumeration {
  type PawnType = Value;

  val EMPTY = new PawnTypeVal {
    override def isWhite: Boolean = return false;
    override def isBlack: Boolean = return false;
    override def isPromoted: Boolean = return false;
  }

  val BLACK = new PawnTypeVal {
    override def isWhite: Boolean = return false;
    override def isBlack: Boolean = return true;
    override def isPromoted: Boolean = return false;
  }

  val WHITE = new PawnTypeVal {
    override def isWhite: Boolean = return true;
    override def isBlack: Boolean = return false;
    override def isPromoted: Boolean = return false;
  }

  val BLACK_PROMOTED = new PawnTypeVal {
    override def isWhite: Boolean = return false;
    override def isBlack: Boolean = return true;
    override def isPromoted: Boolean = return true;
  }

  val WHITE_PROMOTED = new PawnTypeVal {
    override def isWhite: Boolean = return true;
    override def isBlack: Boolean = return true;
    override def isPromoted: Boolean = return false;
  }

  protected abstract class PawnTypeVal extends Val() {
    def isWhite: Boolean;
    def isBlack: Boolean;
    def isPromoted: Boolean;
  }
  
  implicit def valueToPawnTypeVal(x: Value) = x.asInstanceOf[Val];
}

