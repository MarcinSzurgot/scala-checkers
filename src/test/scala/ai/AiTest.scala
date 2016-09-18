package test.scala.ai

import main.scala.ai.CheckerAi
import main.scala.model.PlayerType
import main.scala.model.Board
import org.scalatest.FunSuite
import main.scala.model.Move
import main.scala.model.Position



class AiTest extends FunSuite{
  type PlayerType = PlayerType.PlayerType;
  
//  test("timeTest"){
//    var board = Board();
//    val iters = 400;
//    
//    val begin = System.currentTimeMillis();
//    var rnd = new Random();
//    var counter = 0;
//    for(i <- 0 to iters){
//      var subCounter = 0;
//      while(!board.isGameEnd()){
//        val moves = board.getAllMoves();
//        board.makeMove(moves._1(rnd.nextInt(moves._1.length)));
//        counter += 1;
//        subCounter += 1;
//      }
//      
//      for(s <- 0 to subCounter){
//        board.undoMove();
//      }
//    }
//    val end = System.currentTimeMillis();
//    val sec = (end - begin) / 1000.0;
//    val speed = counter / sec;
//    
//    println(speed);
//  }
//
  test("computePoints"){
    var board = Board();
    var cpu = CheckerAi(PlayerType.BLACK, board, null, null);

    assertResult(0)(cpu.computePoints());

    board.makeMove(Move(Position(2, 1), Position(3, 2)));
    board.makeMove(Move(Position(5, 0), Position(4, 1)));
    board.makeMove(Move(Position(3, 2), Position(5, 0)));

    assertResult(-1)(cpu.computePoints());
  }

  test("testMoves"){
    var board = Board();
    var cpu1 = CheckerAi(PlayerType.WHITE, board, null, null);
    var cpu2 = CheckerAi(PlayerType.BLACK, board, null, null);
    
    while(!board.isGameEnd()){
      cpu1.makeMoveTest();
      cpu2.makeMoveTest();
    }
  }
}