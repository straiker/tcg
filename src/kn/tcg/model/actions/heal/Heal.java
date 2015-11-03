package kn.tcg.model.actions.heal;

import kn.tcg.model.actions.ActionsInterface;
import kn.tcg.model.exceptions.ActionException;
import kn.tcg.model.gamedata.data.GameData;

import java.util.List;

public class Heal implements ActionsInterface{
    @Override
    public String performAction(GameData gameData, int command) throws ActionException {
        List hand = gameData.getActivePlayer().cardset.getHand();
        int card = (Integer)hand.get(command);

        if (gameData.getActivePlayer().hasEnoughMana(card)){
            if (!gameData.getActivePlayer().isHealthFull()){
                gameData.getActivePlayer().reduceMana(card);
                gameData.getActivePlayer().cardset.playCard(card);
                gameData.getActivePlayer().increaseHealth(card);
                return "Health increased.";
            }else{
                return "Health already full!";
            }
        } else return "Not enough mana!";
    }
}
