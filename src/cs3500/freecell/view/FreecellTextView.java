package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModelState;
import java.io.IOException;

/**
 * Represents the game using strings.
 */
public class FreecellTextView implements FreecellView {

  FreecellModelState<?> model;
  Appendable out;

  public FreecellTextView(FreecellModelState<?> model) {
    this.model = model;

  }

  public FreecellTextView(FreecellModelState<?> model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  @Override
  public void renderBoard() throws IOException {

    out.append(this.toString());

  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (out == null) {
      System.out.println(message);
    } else {
      out.append(message);
    }


  }

  /**
   * Represents the board of a freecell game.
   * @return String of the freecell game board.
   */
  public String toString() {
    int k;
    StringBuilder sb = new StringBuilder();

    if (this.model.getNumCascadePiles() == -1 && this.model.getNumOpenPiles() == -1) {
      return "";
    }
    for (int i = 0; i < 4; i++) {
      k = i + 1;
      sb.append("F" + k + ":");
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
        if (j == model.getNumCardsInFoundationPile(i) - 1) {
          sb.append(model.getFoundationCardAt(i, j) + "");
        } else {
          sb.append(model.getFoundationCardAt(i, j) + ", ");
        }

      }
      sb.append("\n");
    }

    for (int i = 0; i < model.getNumOpenPiles(); i++) {
      k = i + 1;
      sb.append("O" + k + ":");
      if (model.getNumCardsInOpenPile(i) == 1) {
        sb.append(model.getOpenCardAt(i) + "\n");
      } else {
        sb.append("\n");
      }


    }
    for (int i = 0; i < model.getNumCascadePiles(); i++) {
      k = i + 1;
      sb.append("C" + k + ": ");
      for (int j = 0; j < model.getNumCardsInCascadePile(i); j++) {

        if (j == model.getNumCardsInCascadePile(i) - 1) {
          sb.append(model.getCascadeCardAt(i, j) + "");
        } else {
          sb.append(model.getCascadeCardAt(i, j) + ", ");
        }

      }
      if (i != model.getNumCascadePiles() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}



