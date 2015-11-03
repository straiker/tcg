package kn.tcg.model.actions.attack;

import kn.tcg.model.actions.ActionsInterface;
import kn.tcg.model.exceptions.ActionException;
import kn.tcg.model.gamedata.data.GameData;
import java.util.List;

public class Attack implements ActionsInterface{
    @Override
    public String performAction(GameData gameData, int command) throws ActionException {
        String message = "";
        List hand = gameData.getActivePlayer().cardset.getHand();
        if(hand.size() > 0){
            int card = (Integer)hand.get(command);

            if (gameData.getActivePlayer().hasEnoughMana(card)){
                gameData.getActivePlayer().reduceMana(card);
                gameData.getActivePlayer().cardset.playCard(card);
                gameData.getInactivePlayer().reduceHealth(card);
                message =  "Damage dealt: "+card;
            } else message = "Not enough mana!";
        }

        return message;
    }
}
