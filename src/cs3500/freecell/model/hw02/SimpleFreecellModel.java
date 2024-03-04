package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * represents a model for a SimpleFreecellModel.
 */
public class SimpleFreecellModel implements FreecellModel<Card> {

  private ArrayList<ArrayList<Card>> cascadePilesList;
  private Card[] openPilesList;
  private ArrayList<ArrayList<Card>> foundationPilesList;
  private boolean[] booleanPileList;


  public SimpleFreecellModel() {
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
            cascadePilesList.get(destPileNumber).add(cascadePilesList.get(pileNumber)
                .remove(cascadePilesList.get(pileNumber).size() - 1));
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


