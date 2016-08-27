import org.scalatest.FunSuite

/**
  * Created by Matik on 28.08.2016.
  */
class ExampleClassTest extends FunSuite {

  test("testExampleMethod") {
    val testClass = new ExampleClass();
    assert(testClass.exampleMethod == 5);
  }

}
