import static org.junit.Assert.assertEquals;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.io.StringReader;
import org.junit.Test;

/**
 * tests for SimpleFreecellController.
 */
public class SimpleFreecellControllerTest {

  FreecellModel<Card> fcm = new SimpleFreecellModel();
  StringReader sr = new StringReader("C2 1 Q");
  StringBuilder sb = new StringBuilder();
  FreecellController<Card> fcc = new SimpleFreecellController(fcm, sr, sb);

  @Test
  public void testForQuitOnDestination() {
    fcc.playGame(fcm.getDeck(), 5, 4, false);
    assertEquals(sb.toString(), "F1: \n"
        + "F2: \n"
        + "F3: \n"
        + "F4: \n"
        + "O1: \n"
        + "O2: \n"
        + "O3: \n"
        + "O4: \n"
        + "C1: A♠, 6♠, J♠, 3♥, 8♥, K♥, 5♦, 10♦, 2♣, 7♣, Q♣\n"
        + "C2: 2♠, 7♠, Q♠, 4♥, 9♥, A♦, 6♦, J♦, 3♣, 8♣, K♣\n"
        + "C3: 3♠, 8♠, K♠, 5♥, 10♥, 2♦, 7♦, Q♦, 4♣, 9♣\n"
        + "C4: 4♠, 9♠, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♣, 10♣\n"
        + "C5: 5♠, 10♠, 2♥, 7♥, Q♥, 4♦, 9♦, A♣, 6♣, J♣\n"
        + "Enter your move\n"
        + "Game quit prematurely.");
  }

  @Test
  public void testForQuit() {
    fcc.playGame(fcm.getDeck(), 5, 4, false);
    assertEquals(sb.toString(), "F1: \n"
        + "F2: \n"
        + "F3: \n"
        + "F4: \n"
        + "O1: \n"
        + "O2: \n"
        + "O3: \n"
        + "O4: \n"
        + "C1: A♠, 6♠, J♠, 3♥, 8♥, K♥, 5♦, 10♦, 2♣, 7♣, Q♣\n"
        + "C2: 2♠, 7♠, Q♠, 4♥, 9♥, A♦, 6♦, J♦, 3♣, 8♣, K♣\n"
        + "C3: 3♠, 8♠, K♠, 5♥, 10♥, 2♦, 7♦, Q♦, 4♣, 9♣\n"
        + "C4: 4♠, 9♠, A♥, 6♥, J♥, 3♦, 8♦, K♦, 5♣, 10♣\n"
        + "C5: 5♠, 10♠, 2♥, 7♥, Q♥, 4♦, 9♦, A♣, 6♣, J♣\n"
        + "Enter your move\n"
        + "Game quit prematurely.");


  }


}


