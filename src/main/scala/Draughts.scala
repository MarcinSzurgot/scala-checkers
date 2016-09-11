import main.scala.view.BoardScene
import scalafx.application.JFXApp

object Draughts extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Draughts"
    width = 700
    height = 700
    scene = new BoardScene {
      stylesheets = List(getClass.getResource("styles.css").toExternalForm)
    }
  }
}
