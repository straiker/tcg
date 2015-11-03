package kn.tcg.model.cardset;

import java.util.*;

public class Cardset {

    public Cardset(){}

    private Random random = new Random();
    private int maxHandSize = 5;

    private Integer[] cardList = {0,0,1,1,2,2,2,3,3,3,4,4,4,5,5,6,6,7,8};

    private List<Integer> cardDeck = new LinkedList<Integer>(Arrays.asList(cardList));
    private List<Integer> discardPile = new ArrayList<Integer>();
    private List<Integer> playerHand = new ArrayList<Integer>();

    public List getDeck(){
        return cardDeck;
    }

    public List getHand(){
        return playerHand;
    }

    public int getMaxHandSize(){
        return maxHandSize;
    }

    public List getDiscardPile(){
        return discardPile;
    }

    public void drawInitialHand(){
        for(int count = 0, maxCards = 3; count < maxCards; count++){
            drawNewCard();
        }
    }

    public void addCardToHand(int card){
        getHand().add(card);
    }

    private void removeCardFromDeck(int index){
        cardDeck.remove(index);
    }

    public void drawNewCard(){
        int index = random.nextInt(cardDeck.size());
        int card = cardDeck.get(index);

        addCardToHand(card);
        removeCardFromDeck(index);
    }

    public void moveCardToDiscardPile(){
        int index = random.nextInt(cardDeck.size());
        int card = cardDeck.get(index);

        discardPile.add(card);
        removeCardFromDeck(index);
    }

    public void playCard(int card){
        discardPile.add(card);
        getHand().remove(getHand().indexOf(card));
    }
}
