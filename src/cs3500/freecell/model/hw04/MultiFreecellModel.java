package cs3500.freecell.model.hw04;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.Card.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * represents a model for a SimpleFreecellModel.
 */
public class MultiFreecellModel implements FreecellModel<Card> {

  private ArrayList<ArrayList<Card>> cascadePilesList;
  private Card[] openPilesList;
  private ArrayList<ArrayList<Card>> foundationPilesList;
  private boolean[] booleanPileList;


  public MultiFreecellModel() {
    cascadePilesList = new ArrayList<>();
    foundationPilesList = new ArrayList<>();
  }

  /**
   * Produces a deck of 52 cards.
   *
   * @return list of cards
   */
  public List<Card> getDeck() {
    ArrayList<Card> l1 = new ArrayList<>();
    for (Card.Suit s : Card.Suit.values()) {
      for (Card.Number n : Card.Number.values()) {

        l1.add(new Card(s, n));
      }
    }
    return l1;
  }


  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)

      throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("not valid:");
    }
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Invalid deck size");
    }
    if (!this.noDuplicates(deck)) {
      throw new IllegalArgumentException("There are duplicates!");
    }
    for (Card card : deck) {
      if (card.getSuit() == null || card.getCardNumber() == null) {
        throw new IllegalArgumentException("not valid card");
      }
    }
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Invalid number of cascade piles");
    }
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("invalid number of open piles");
    }
    if (shuffle) {
      Collections.shuffle(deck);
    }

    this.cascadePilesList = new ArrayList<ArrayList<Card>>(numCascadePiles);
    this.foundationPilesList = new ArrayList<ArrayList<Card>>(4);

    for (int i = 0; i < numCascadePiles; i++) {

      cascadePilesList.add(new ArrayList<Card>());
    }
    openPilesList = new Card[numOpenPiles];
    booleanPileList = new boolean[numOpenPiles];
    for (int i = 0; i < numOpenPiles; i++) {
      booleanPileList[i] = false;
    }

    for (int i = 0; i < 4; i++) {
      foundationPilesList.add(new ArrayList<Card>());
    }
    int i = 0;
    for (Card card : deck) {
      if (i < cascadePilesList.size()) {
        cascadePilesList.get(i).add(card);
        i++;
      } else {

        cascadePilesList.get(0).add(card);
        i = 1;
      }

    }

  }


  private boolean noDuplicates(List<Card> deck) {
    List<Card> seenAlready = new ArrayList<>();
    for (Card card : deck) {
      if (seenAlready.contains(card)) {
        return false;
      } else {
        seenAlready.add(card);
      }
    }
    return true;
  }

  /**
   * Takes two cards and returns true if the values of the cards are descending and alternating.
   *
   * @param c1 represents first card inputted
   * @param c2 represents second card inputted
   * @return true if card values are descending and alternating, false otherwise
   */
  public static boolean makeBuild(Card c1, Card c2) {
    if ((c1.getSuit() == Suit.CLUBS || c1.getSuit() == Suit.SPADES && c2.getSuit() == Suit.HEARTS
        || c2.getSuit() == Suit.DIAMONDS)
        || (c1.getSuit() == Suit.DIAMONDS || c1.getSuit() == Suit.HEARTS)
        && c2.getSuit() == Suit.CLUBS
        || c2.getSuit() == Suit.SPADES) {
      if (c1.getCardNumber() == Card.Number.TWO && c2.getCardNumber() == Card.Number.ACE) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.THREE && c2.getCardNumber() == Card.Number.TWO) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.FOUR
          && c2.getCardNumber() == Card.Number.THREE) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.FIVE && c2.getCardNumber() == Card.Number.FOUR) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.SIX && c2.getCardNumber() == Card.Number.FIVE) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.SEVEN && c2.getCardNumber() == Card.Number.SIX) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.EIGHT
          && c2.getCardNumber() == Card.Number.SEVEN) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.NINE
          && c2.getCardNumber() == Card.Number.EIGHT) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.TEN && c2.getCardNumber() == Card.Number.NINE) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.JACK && c2.getCardNumber() == Card.Number.TEN) {
        return true;
      } else if (c1.getCardNumber() == Card.Number.QUEEN
          && c2.getCardNumber() == Card.Number.JACK) {
        return true;
      } else {
        return (c1.getCardNumber() == Card.Number.KING
            && c2.getCardNumber() == Card.Number.QUEEN);
      }
    } else {
      return false;
    }

  }


  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    if (source == null || destination == null) {
      throw new IllegalArgumentException("invalid piletype");
    }
    if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException(
          "Invalid move because no card can be moved from foundation!");
    }
    if (cascadePilesList.isEmpty()) {
      throw new IllegalStateException("Game hasn't started!");
    }
    if (source == PileType.CASCADE) {
      if (cascadePilesList.get(pileNumber).isEmpty()) {
        throw new IllegalArgumentException("Pile can't be empty!");
      }
    }

    if (source == PileType.OPEN) {
      if (!booleanPileList[pileNumber]) {
        throw new IllegalArgumentException("Pile is empty!");
      }
    }

    if (destination == PileType.OPEN) {
      if (booleanPileList[destPileNumber]) {
        throw new IllegalArgumentException("not valid entry!");
      }
    }
    if (destination == source && pileNumber == destPileNumber) {
      throw new IllegalArgumentException("illegal move to put back in the same pile");
    }
    switch (source) {
      case CASCADE:
        switch (destination) {
          case OPEN:
            Card temp = cascadePilesList.get(pileNumber).get(cardIndex);
            cascadePilesList.get(pileNumber).remove(cardIndex);
            openPilesList[destPileNumber] = temp;
            booleanPileList[destPileNumber] = true;
            break;
          case FOUNDATION:
            foundationPilesList.get(destPileNumber).add(cascadePilesList.get(pileNumber)
                .remove(cascadePilesList.get(pileNumber).size() - 1));
            break;
          case CASCADE:
            Card currentCard = cascadePilesList.get(pileNumber).get(cardIndex);
            // if we are moving the last card alone then just move that card
            // to do apply make-build

            // otherwise we check to see if we can do multi-move given the parameters
            // meeting the three conditions given in the problem
            // one : valid source pile build
            // two: valid destination pile build
            // three: valid number of cards to be moved(n + 1 * 2^k)

            // set holder for temporary card index
            int holder = cardIndex + 1;
            // get number of empty open piles
            int n = 0;
            for (int i = 0; i < booleanPileList.length; i++) {
              if (!booleanPileList[i]) {
                n++;
              }
            }
            // get number of empty cascade piles
            int k = 0;
            for (int i = 0; i < cascadePilesList.size(); i++) {
              if (cascadePilesList.get(i).size() == 0) {
                k++;
              }
            }
            // get the maximum number of cards thar can be moved
            // given the number of empty open piles and empty cascade piles
            int maxMove = (n + 1) * (int) Math.pow(2, k);
            // loop until the end of cascade pile
            // checking to see if we make a valid build(condition 1)
            // if we make a build to the end of the source pile
            // check to see if the number of cards to be moved is valid(condition 3)
            // check to see if moving the card at cardIndex from the source pile to the last card
            // in the dest pile will make a valid build(condition 2)
            // if all the conditions are met we can make
            // a valid multimove from the source to the dest pile.
            // else if any conditions arent met, we throw illegal argument exceptions.

            while (true) {
              if (holder - 1 >= cascadePilesList.get(pileNumber).size() - 1) {
                if (maxMove < cascadePilesList.get(pileNumber).size() - cardIndex) {
                  throw new IllegalArgumentException(
                      "Invalid multimove, too many cards moved without an open pile");
                } else {
                  if (cascadePilesList.get(destPileNumber).size() == 0 || makeBuild(
                      cascadePilesList.get(destPileNumber)
                          .get(cascadePilesList.get(destPileNumber).size() - 1), currentCard)) {
                    while (cardIndex <= cascadePilesList.get(pileNumber).size() - 1) {
                      cascadePilesList.get(destPileNumber)
                          .add(cascadePilesList.get(pileNumber).get(cardIndex));
                      cascadePilesList.get(pileNumber).remove(cardIndex);

                    }
                    break;
                  } else {
                    throw new IllegalArgumentException(
                        "Invalid multimove, not a valid build on the destination pile");
                  }
                }

              } else {
                Card nextCard = cascadePilesList.get(pileNumber).get(holder++);
                // condition 1
                if (makeBuild(currentCard, nextCard)) {
                  // continue
                  currentCard = nextCard;
                }
                // cant make build
                else {
                  throw new IllegalArgumentException("Invalid multimove");
                }

              }
            }
            break;

          default:
            throw new IllegalArgumentException("not valid move");
        }

        break;
      case OPEN:
        switch (destination) {
          case CASCADE:
            cascadePilesList.get(destPileNumber).add(openPilesList[pileNumber]);
            booleanPileList[pileNumber] = false;
            break;
          case FOUNDATION:
            foundationPilesList.get(destPileNumber).add(openPilesList[pileNumber]);
            booleanPileList[pileNumber] = false;
            break;
          case OPEN:
            openPilesList[destPileNumber] = openPilesList[pileNumber];
            booleanPileList[destPileNumber] = true;
            booleanPileList[pileNumber] = false;
            break;
          default:
            throw new IllegalArgumentException("invalid move");

        }
        break;
      default:
        throw new IllegalArgumentException("invalid move");

    }

    return;
  }

  @Override
  public boolean isGameOver() {
    int count = 0;
    for (int i = 0; i < foundationPilesList.size(); i++) {
      count += foundationPilesList.get(i).size();
    }
    return count == 52;
  }


  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (cascadePilesList.size() == 0) {
      throw new IllegalStateException("game hasnt started");
    }
    if (index < 0 || index >= foundationPilesList.size()) {
      throw new IllegalArgumentException("Invalid foundation pile index");
    }
    return foundationPilesList.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (cascadePilesList.size() == 0) {
      return -1;
    }
    return this.cascadePilesList.size();
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (cascadePilesList.size() == 0) {
      throw new IllegalStateException("game hasnt started");
    }
    if (cascadePilesList.isEmpty()) {
      throw new IllegalStateException("game hasn't started!");
    }
    if (index > this.cascadePilesList.size() - 1 || index < 0) {
      throw new IllegalArgumentException(index + " not in bounds of the array!");
    }

    return this.cascadePilesList.get(index).size();

  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (openPilesList == null) {
      throw new IllegalStateException("Game hasnt started");
    }
    if (index > this.openPilesList.length - 1 || index < 0) {
      throw new IllegalArgumentException("not in bounds of the array!");
    }
    if (booleanPileList[index]) {
      return 1;
    } else {
      return 0;
    }

  }

  @Override
  public int getNumOpenPiles() {
    if (openPilesList == null) {
      return -1;
    } else {
      return openPilesList.length;
    }

  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (cascadePilesList.size() == 0) {
      throw new IllegalStateException("game hasnt started");
    }
    if (pileIndex < 0 || pileIndex > foundationPilesList.size() - 1) {
      throw new IllegalArgumentException("Invalid size");
    }
    if (cardIndex < 0 || cardIndex > foundationPilesList.get(pileIndex).size() - 1) {
      throw new IllegalArgumentException(cardIndex + "Invalid size");
    }
    return this.foundationPilesList.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (cascadePilesList.size() == 0) {
      throw new IllegalStateException("game hasnt started");
    }
    if (pileIndex < 0 || pileIndex > cascadePilesList.size() - 1) {
      throw new IllegalArgumentException("not valid size");
    }
    if (cardIndex < 0 || cardIndex > cascadePilesList.get(pileIndex).size() - 1) {
      throw new IllegalArgumentException(cardIndex + "Invalid size");
    }
    return this.cascadePilesList.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    if (openPilesList == null) {
      throw new IllegalStateException("game hasnt started");
    }
    if (pileIndex < 0 || pileIndex > openPilesList.length - 1) {
      throw new IllegalArgumentException("not valid size!");
    }
    if (!booleanPileList[pileIndex]) {
      return null;
    }
    return this.openPilesList[pileIndex];
  }
}



