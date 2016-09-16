import main.scala.view.BoardScene

import scalafx.application.{JFXApp, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.Includes._
import scalafx.stage.Stage

object Draughts extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Draughts"
    width = 300
    height = 500
    scene = new Scene {
      stylesheets = List(getClass.getResource("styles.css").toExternalForm)
      content = new BorderPane {
        padding = Insets(50)
        top = new Label("Draughts") {
          id = "Title"
          alignment = Pos.Center
          maxWidth = Double.MaxValue
          minHeight = 200
        }
        center = new HBox() {
          children = Seq(
            new Button("Start") {
              onAction = handle {
                val boardStage = new Stage {
                  title.value = "Board"
                  width = 700
                  height = 700
                  minWidth = 100
                  scene = new BoardScene {
                    stylesheets = List(getClass.getResource("styles.css").toExternalForm)
                  }
                }
                boardStage.show()
              }
            },
            new Button("Exit") {
              minWidth = 100
              onAction = handle {Platform.exit()}
            }
          )
        }
      }
    }
  }
}
