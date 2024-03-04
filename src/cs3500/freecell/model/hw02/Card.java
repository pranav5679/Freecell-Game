package cs3500.freecell.model.hw02;

import java.util.Objects;

/**
 * Represents bibliographical information for a card.
 */
public class Card {

  /**
   * Represents different suits in a deck of cards.
   */
  public enum Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  /**
   * Represents different values of cards in a deck.
   */
  public enum Number {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
  }


  private Suit suit;
  private Number number;

  /** represents a card.
   * @param s represents suit of the card
   * @param n represents number of the card
   */
  public Card(Suit s, Number n) {
    this.suit = s;
    this.number = n;
  }

  public Number getCardNumber() {
    return this.number;
  }

  public Suit getSuit() {
    return this.suit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return suit == card.suit && number == card.number;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suit, number);
  }

  /**
   * Represents card as string.
   * @return string
   */
  public String toString() {
    String result = "";

    switch (number) {
      case JACK:
        result += "J";
        break;
      case QUEEN:
        result += "Q";
        break;
      case KING:
        result += "K";
        break;
      case ACE:
        result += "A";
        break;
      case TWO:
        result += "2";
        break;
      case THREE:
        result += "3";
        break;
      case FOUR:
        result += "4";
        break;
      case FIVE:
        result += "5";
        break;
      case SIX:
        result += "6";
        break;
      case SEVEN:
        result += "7";
        break;
      case EIGHT:
        result += "8";
        break;
      case NINE:
        result += "9";
        break;
      case TEN:
        result += "10";
        break;
      default:
        return "";

    }
    switch (suit) {
      case HEARTS:
        result += "♥";
        break;
      case SPADES:
        result += "♠";
        break;
      case CLUBS:
        result += "♣";
        break;
      case DIAMONDS:
        result += "♦";
        break;
      default:
        return "";
    }
    return result;
  }

}







