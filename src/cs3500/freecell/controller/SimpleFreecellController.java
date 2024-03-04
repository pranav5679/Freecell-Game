package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

/**
 * represents a controller for a SimpleFreecellController.
 */
public class SimpleFreecellController implements FreecellController<Card> {

  private final Readable in;
  private final Appendable out;
  private final FreecellModel<Card> fcm;

  /**
   * Runs the program using the console.
   *
   * @param args arguments taken by the controller
   */

  public static void main(String[] args) {
    SimpleFreecellController myController = new SimpleFreecellController(new SimpleFreecellModel(),
        new InputStreamReader(System.in), System.out);
    Scanner myScanner = new Scanner(myController.in);
    int numberOfCascadePiles;
    int numberOfOpenPiles;

    try {
      myController.out.append("enter the number of cascade piles that you would like:");

      numberOfCascadePiles = myScanner.nextInt();
      if (numberOfCascadePiles < 4) {
        myController.out.append("Could not start game.");
        return;
      }
      myController.out.append("enter number of open piles you would like:");
      numberOfOpenPiles = myScanner.nextInt();
      if (numberOfOpenPiles < 1) {
        myController.out.append("Could not start game.");
        return;
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("we don't have an out");
    }

    List<Card> myDeck = myController.fcm.getDeck();
    boolean shuffled = true;
    myController.playGame(myDeck, numberOfCascadePiles, numberOfOpenPiles, shuffled);
  }

  /**
   * Takes a model, readable and appendable to convert into a SimpleFreecellController.
   *
   * @param fcm represents the FreecellModel
   * @param in  represents the readable input
   * @param out represents the appendable input
   */
  public SimpleFreecellController(FreecellModel<Card> fcm, Readable in, Appendable out) {
    if (in == null) {
      throw new IllegalArgumentException("can't be null");
    }
    this.in = in;
    if (out == null) {
      throw new IllegalArgumentException("can't be null");
    }
    this.out = out;
    if (fcm == null) {
      throw new IllegalArgumentException("can't be null");
    }
    this.fcm = fcm;
  }


  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("illegal deck");
    }

    FreecellView view = new FreecellTextView(fcm, out);

    try {
      fcm.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      try {
        out.append("Could not start game.");
        return;
      } catch (IOException f) {
        throw new IllegalStateException("Output failed");
      }
    }
    Scanner myScanner = new Scanner(in);
    int pIndex;
    int cIndex;
    int dIndex;
    char sourcePile;
    char destinationPile;
    PileType sourceType;
    PileType destType;
    while (true) {
      try {
        view.renderBoard();
        if (fcm.isGameOver()) {
          view.renderBoard();
          out.append("\nGame over.");
          return;

        }
        out.append("Enter your move\n");
        String first = "";
        String second = "";
        String third = "";

        if (myScanner.hasNext()) {
          first = myScanner.next();
        } else {
          throw new IllegalStateException("No first argument exists");
        }
        if (first.charAt(0) == 'Q' || first.charAt(0) == 'q') {
          out.append("Game quit prematurely.");
          break;
        }
        if (myScanner.hasNext()) {
          second = myScanner.next();
        } else {
          throw new IllegalArgumentException("No second argument exists");
        }
        if (second.charAt(0) == 'Q' || second.charAt(0) == 'q') {
          out.append("Game quit prematurely.");
          break;
        }
        if (myScanner.hasNext()) {
          third = myScanner.next();
        } else {
          throw new IllegalArgumentException("No third argument exists");
        }
        if (third.charAt(0) == 'Q' || third.charAt(0) == 'q') {
          out.append("Game quit prematurely.");
          break;
        }

        while (true) {

          if (first.charAt(0) != 'C'
              && first.charAt(0) != 'F' && first.charAt(0) != 'O') {
            out.append("please enter the source pile again");
            if (myScanner.hasNext()) {
              first = myScanner.next();
            } else {
              throw new IllegalArgumentException("No input detected");
            }

          } else {
            try {
              sourcePile = first.charAt(0);
              pIndex = Integer.parseInt(first.substring(1)) - 1;
              break;
            } catch (NumberFormatException nfe) {
              out.append("please enter the source pile again");
              if (myScanner.hasNext()) {
                first = myScanner.next();
              } else {
                throw new IllegalArgumentException("No first argument exists");
              }
            }
          }
        }

        while (true) {
          try {
            cIndex = Integer.parseInt(second) - 1;
            break;
          } catch (NumberFormatException nfe) {
            out.append(second + "please enter the card Index\n");
            if (myScanner.hasNext()) {
              second = myScanner.next();
            } else {
              throw new IllegalArgumentException("No second argument exists");
            }
          }

        }

        while (true) {
          if (third.charAt(0) != 'C'
              && third.charAt(0) != 'F' && third.charAt(0) != 'O') {
            if (third.charAt(0) != 'Q' || third.charAt(0) != 'q') {
              out.append("Game quit prematurely.");
              return;
            }

            out.append("please enter the destination pile again");
            if (myScanner.hasNext()) {
              third = myScanner.next();
            } else {
              throw new IllegalArgumentException("No third argument exists");
            }

          }
          try {
            destinationPile = third.charAt(0);
            dIndex = Integer.parseInt(third.substring(1)) - 1;
            break;
          } catch (NumberFormatException nfe) {
            out.append("please enter the valid destination pile");
          }
        }

        if (sourcePile == 'C') {
          sourceType = PileType.CASCADE;
          if (fcm.getNumCascadePiles() <= pIndex || pIndex < 0) {
            out.append("Invalid input, try again.\n");
          } else if (fcm.getNumCardsInCascadePile(pIndex) <= cIndex || cIndex < 0) {
            out.append("Invalid input, try again.\n");
          } else {
            Card sourceCard = fcm.getCascadeCardAt(pIndex, cIndex);
            destType = getDestType(destinationPile);
            if (destType == PileType.FOUNDATION) {
              if (4 <= dIndex || dIndex < 0) {
                throw new IllegalArgumentException("Invalid foundation index");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            } else if (destType == PileType.OPEN) {
              if (fcm.getNumOpenPiles() <= dIndex || dIndex < 0) {
                out.append("Invalid input, try again.\n");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            } else if (destType == PileType.CASCADE) {
              if (fcm.getNumCascadePiles() <= dIndex || dIndex < 0) {
                out.append("Invalid input, try again.\n");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            }
          }


        } else if (sourcePile == 'F') {
          sourceType = PileType.FOUNDATION;
          if (4 <= pIndex || pIndex < 0) {
            throw new IllegalArgumentException("Invalid foundation index.");
          } else if (fcm.getNumCardsInFoundationPile(pIndex) <= cIndex || cIndex < 0) {
            out.append("Invalid input, try again.\n");
          } else {
            Card sourceCard = fcm.getFoundationCardAt(pIndex, cIndex);
            destType = getDestType(destinationPile);
            if (destType == PileType.FOUNDATION) {
              if (4 <= dIndex || dIndex < 0) {
                throw new IllegalArgumentException("Invalid foundation index");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            } else if (destType == PileType.OPEN) {
              if (fcm.getNumOpenPiles() <= dIndex || dIndex < 0) {
                out.append("Invalid input, try again.\n");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            } else if (destType == PileType.CASCADE) {
              if (fcm.getNumCascadePiles() <= dIndex || dIndex < 0) {
                out.append("Invalid input, try again.\n");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            }
          }


        } else if (sourcePile == 'O') {
          sourceType = PileType.OPEN;
          if (fcm.getNumOpenPiles() <= pIndex || pIndex < 0) {
            out.append("Invalid input, Try again.\n");
          } else if (fcm.getNumCardsInOpenPile(pIndex) <= cIndex || cIndex < 0) {
            out.append("Invalid input, try again.\n");
          } else {
            Card sourceCard = fcm.getOpenCardAt(pIndex);
            destType = getDestType(destinationPile);
            if (destType == PileType.FOUNDATION) {
              if (4 <= dIndex || dIndex < 0) {
                throw new IllegalArgumentException("Invalid foundation index");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            } else if (destType == PileType.OPEN) {
              if (fcm.getNumOpenPiles() <= dIndex || dIndex < 0) {
                out.append("Invalid input, try again.\n");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            } else if (destType == PileType.CASCADE) {
              if (fcm.getNumCascadePiles() <= dIndex || dIndex < 0) {
                out.append("Invalid input, try again.\n");
              } else {
                fcm.move(sourceType, pIndex, cIndex, destType, dIndex);
              }

            }
          }


        } else {
          throw new IllegalArgumentException("Invalid input");
        }


      } catch (IOException e) {
        throw new IllegalStateException("not valid");
      }


    }
  }


  private PileType getDestType(char destinationPile) {
    PileType destType;

    if (destinationPile == 'C') {
      destType = PileType.CASCADE;


    } else if (destinationPile == 'F') {
      destType = PileType.FOUNDATION;


    } else if (destinationPile == 'O') {
      destType = PileType.OPEN;


    } else {
      throw new IllegalArgumentException("Invalid destination chosen");

    }
    return destType;
  }

}

