package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiFreecellModel;

/**
 * Represents a model for reecellModelCreator and determines type of move.
 */
public class FreecellModelCreator {

  /**
   * Represents the GameType/type of move.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Creates a FreecellModel based on type of move.
   * @param val represents the type of move
   * @return SimpleFreecellModel if Single move, otherwise MultiFreecellModel
   */
  public static FreecellModel create(GameType val) {
    if (val.equals(GameType.SINGLEMOVE)) {
      return new SimpleFreecellModel();


    } else {
      return new MultiFreecellModel();
    }


  }
}
