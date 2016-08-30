import main.scala.view.BoardScene
import scalafx.application.JFXApp

object Draughts extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Draughts"
    width = 600
    height = 450
    scene = new BoardScene {
      stylesheets = List(getClass.getResource("styles.css").toExternalForm)
    }
  }
}
