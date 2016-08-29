package view

import scalafx.scene.layout._
import scalafx.Includes._
import scalafx.geometry.{HPos, VPos}
import scalafx.scene.Scene


class BoardScene extends Scene
{
  final val BOARD_LENGTH = 8;
  final val START_POS = 1;

  content = new GridPane {
    columnConstraints += new ColumnConstraints(5, 60, Double.PositiveInfinity, Priority.ALWAYS, HPos.CENTER, true).delegate
    rowConstraints += new RowConstraints(5, 60, Double.PositiveInfinity, Priority.ALWAYS, VPos.CENTER, true).delegate
    for (i <- START_POS to BOARD_LENGTH) {
      columnConstraints += new ColumnConstraints(5, 60, Double.PositiveInfinity, Priority.ALWAYS, HPos.CENTER, true).delegate
      rowConstraints += new RowConstraints(5, 60, Double.PositiveInfinity, Priority.ALWAYS, VPos.CENTER, true).delegate
      for (a <- START_POS to BOARD_LENGTH) {
        add(new StackPane {
          if (1 == (i+a) % 2) styleClass += "blackSquare" else styleClass += "whiteSquare"
        }.delegate, i, a)
      }
    }
  }
}
