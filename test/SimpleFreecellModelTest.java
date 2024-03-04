import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing the SimpleFreecellModel class.
 */

public class SimpleFreecellModelTest {

  private SimpleFreecellModel fcm;

  @Before
  public void setup() {
    fcm = new SimpleFreecellModel();

  }

  @Test
  public void testForCascadeCardDisribution() {
    fcm.startGame(fcm.getDeck(), 4, 1, false);
    assertEquals(13, fcm.getNumCardsInCascadePile(0));
    assertEquals(13, fcm.getNumCardsInCascadePile(1));
    assertEquals(13, fcm.getNumCardsInCascadePile(2));
    assertEquals(13, fcm.getNumCardsInCascadePile(3));

  }

  @Test
  public void testConstructor() {
    assertEquals(-1, fcm.getNumCascadePiles());
    assertEquals(-1, fcm.getNumOpenPiles());
  }

  @Test
  public void testGetDeck() {
    List<Card> deck = fcm.getDeck();
    assertEquals(52, deck.size());

  }

  @Test(expected = IllegalStateException.class)
  public void testForCardsInFoundationPile() {
    fcm.getNumCardsInFoundationPile(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testForCardsInCascadePile() {
    fcm.getNumCardsInCascadePile(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testForCardsInOpenPile() {
    fcm.getNumCardsInOpenPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForCardsInFoundationPileForBadIndex() {
    fcm.startGame(fcm.getDeck(), 4, 1, false);
    fcm.getNumCardsInFoundationPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForCardsInCascadePileForBadIndex() {
    fcm.startGame(fcm.getDeck(), 4, 1, false);
    fcm.getNumCardsInCascadePile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForCardsInOpenPileForBadIndex() {
    fcm.startGame(fcm.getDeck(), 4, 1, false);
    fcm.getNumCardsInCascadePile(-1);
  }

  @Test
  public void testForMovingFromCascadeToOpen() {
    fcm.startGame(fcm.getDeck(), 4, 1, false);
    Card tempcard = fcm.getCascadeCardAt(3, 2);
    fcm.move(PileType.CASCADE, 3, 2, PileType.OPEN, 0);
    assertEquals(tempcard, fcm.getOpenCardAt(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForStartGameInvalidCascade() {
    fcm.startGame(fcm.getDeck(), 0, 5, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForStartGameInvalidOpen() {
    fcm.startGame(fcm.getDeck(), 6, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForStartGameInvalidDeck() {
    List<Card> invalidDeck = fcm.getDeck();
    invalidDeck.remove(0);
    fcm.startGame(invalidDeck, 6, 0, true);
  }

  @Test(expected = IllegalStateException.class)
  public void testForGetOpenCardAtStateException() {
    fcm.getOpenCardAt(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testForGetCascadeCardAtStateException() {
    fcm.getCascadeCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testForGetFoundationCardAtStateException() {
    fcm.getFoundationCardAt(0, 0);
  }

  @Test
  public void testForGetNumOpenPilesNotStarted() {
    assertEquals(-1, fcm.getNumOpenPiles());
  }

  @Test
  public void testForGetNumOpenPilesStarted() {
    fcm.startGame(fcm.getDeck(), 4, 4, true);
    assertEquals(4, fcm.getNumOpenPiles());
  }

  @Test(expected = IllegalStateException.class)
  public void testForGetNumCardsInOpenPileStateException() {
    fcm.getNumCardsInOpenPile(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testForGetNumCardsInFoundationPileStateException() {
    fcm.getNumCardsInFoundationPile(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testForGetNumCardsInCascadePileStateException() {
    fcm.getNumCardsInCascadePile(0);
  }


}