package cs3500.freecell.model.hw04;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import org.junit.Test;


/**
 * Tests for MultiFreecellModel.
 */
public class MultiFreecellModelTest {

  MultiFreecellModel fcm = new MultiFreecellModel();
  Appendable in = new StringBuilder();
  FreecellView fcv = new FreecellTextView(fcm, in);

  @Test
  public void testForMovingFromCascadeToOpen() {
    fcm.startGame(fcm.getDeck(), 4, 1, false);
    System.out.println(in.toString());
    fcm.move(PileType.CASCADE, 3, 0, PileType.CASCADE, 0);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());

  }

  @Test
  public void testMovingLastCardFromCascadeToCascade() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testForMaximumCardsThatCanBeMovedBasedOnEmptyPiles() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForTheDestinationPileIsNotABuild() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 0);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForSourcePileIsNotABuild() {
    fcm.startGame(fcm.getDeck(), 4, 2, false);
    fcm.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 1);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testMoveToOpenPile() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.OPEN, 1);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testAceMoveToFoundationPile() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.FOUNDATION, 1);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testOtherMoveToFoundationPile() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 12, 0, PileType.FOUNDATION, 1);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testForOpenToFoundation() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.OPEN, 1);
    fcm.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 1);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testMultimoveToEmptyCascadePile() {
    fcm.startGame(fcm.getDeck(), 52, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fcm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 13);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testMoveToEmptyCascadePile() {
    fcm.startGame(fcm.getDeck(), 53, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 52);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testMultimoveFromCascadeToCascade() {
    fcm.startGame(fcm.getDeck(), 53, 2, false);
    fcm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fcm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 15);
    try {
      fcv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(in.toString());
    assertEquals("", toString());
  }

  @Test
  public void testForGetNumOpenPilesStarted() {
    fcm.startGame(fcm.getDeck(), 4, 4, true);
    assertEquals(4, fcm.getNumOpenPiles());
  }

}
