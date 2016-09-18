import main.scala.view.{BoardScene, BoardStage}

import scalafx.application.{JFXApp, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.stage.Stage

object Draughts extends JFXApp {
  val playerChoice = new ComboBox[String] {
    minWidth = 215
    value = "One Player"
    items = ObservableBuffer(
      "One Player",
      "Two Players"
    )
  }
  val level = new ComboBox[String] {
    minWidth = 215
    value = "5"
    items = ObservableBuffer(
      "1",
      "2",
      "3",
      "4",
      "5",
      "6"
    )
  }
  stage = new JFXApp.PrimaryStage {
    title.value = "Draughts"
    width = 300
    height = 530
    scene = new Scene {
      stylesheets = List(getClass.getResource("styles.css").toExternalForm)
      content = new BorderPane {
        styleClass += "root"
        padding = Insets(50)
        top = new Label("Draughts") {
          id = "Title"
          alignment = Pos.Center
          maxWidth = Double.MaxValue
          minHeight = 200
        }
        center = new HBox() {
          vgrow = Priority.Always
          hgrow = Priority.Always
          spacing = 15
          children = Seq(
            new Button("Start") {
              onAction = handle {
                val boardStage = new BoardStage(
                  List(getClass.getResource("styles.css").toExternalForm),
                  playerChoice.value.value,
                  level.value.value.toInt
                )
                boardStage.show()
              }
            },
            new Button("Exit") {
              minWidth = 100
              onAction = handle {Platform.exit()}
            }
          )
          minHeight = 100
        }

        bottom = new VBox() {
          vgrow = Priority.Always
          hgrow = Priority.Always
          spacing = 15
          children = Seq(
            playerChoice,
            level
          )
          minHeight = 100
        }

      }
    }
  }
}
