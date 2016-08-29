package view

import scalafx.scene.layout._
import scalafx.Includes._
import scalafx.geometry.{HPos, Insets, VPos}
import scalafx.scene.Scene
import scalafx.scene.control.Control
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import scalafx.scene.paint.Color._

class BoardScene extends Scene
{
  final val BOARD_LENGTH = 8;
  final val START_POS = 1;

  content = new GridPane {
    hgap = 8
    vgap = 8
    columnConstraints += new ColumnConstraints(5, 10, Double.PositiveInfinity, Priority.ALWAYS, HPos.CENTER, true).delegate
//      5,
//      Control.USE_COMPUTED_SIZE,
//      Double.PositiveInfinity,
//      ,
//      HPos.CENTER,
//      true
    rowConstraints += new RowConstraints(5, 10, Double.PositiveInfinity, Priority.ALWAYS, VPos.CENTER, true).delegate
      for (i <- START_POS to BOARD_LENGTH) {
        for (a <- START_POS to BOARD_LENGTH) {
          add(new StackPane {
//            children +=
//              new Text {
//                text = "Hello "
//                style = "-fx-font-size: 48pt"
//                fill = new LinearGradient(
//                  endX = 0,
//                  stops = Stops(PaleGreen, SeaGreen))
//              }.delegate
            if (1 == (i+a) % 2) styleClass.add("blackSquare") else styleClass.add("whiteSquare")
          }.delegate, i, a)
        }
      }
  }
}
