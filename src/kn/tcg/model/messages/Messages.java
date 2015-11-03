package kn.tcg.model.messages;

import kn.tcg.model.gamedata.data.GameData;

public class Messages {
    public GameData gameData;

    public Messages(GameData gameData){
        this.gameData = gameData;
    }

    public String gameCanceled(){
        return "Game was canceled";
    }

    public String commandList(){
        String commandList = "\n|----------------------------------|\n";
        commandList += "| -- Commands -------------------- |\n";
        commandList += "| 0. Quit game ------------------- |\n";
        commandList += "| 1. End turn -------------------- |\n";
        commandList += "| 2. Attack ---------------------- |\n";
        commandList += "| 3. Heal ------------------------ |\n";
        commandList += "| 4. Minion control --- <WIP> ---- |\n";
        commandList += "|----------------------------------|\n";

        return commandList;
    }

    public String displayPlayerData(){
        String playerData = "\n|----------------------------------|";
        playerData += "\n| Round  "+  gameData.getRoundNumber() +" ------------------------|";
        playerData += "\n| Player " +  gameData.getActivePlayer().getName() + "  active: "+ gameData.isPlayerActive( gameData.getActivePlayer());
        playerData += "\n| Health: "+ gameData.getActivePlayer().getHealth()+" | Mana: "+ gameData.getActivePlayer().getManaCount()+"/"+ gameData.getActivePlayer().getMaxMana();
        playerData += "\n| Deck size: "+ gameData.getActivePlayer().cardset.getDeck().size()+" | Discard pile: "+ gameData.getActivePlayer().cardset.getDiscardPile().size();
        playerData += "\n| Player 1 hand: "+ gameData.getActivePlayer().cardset.getHand();
        playerData += "\n| Player 1 deck: "+ gameData.getActivePlayer().cardset.getDeck();
        playerData += "\n|----------------------------------|";
        playerData += "\n| Player " +  gameData.getInactivePlayer().getName() + " active: "+ gameData.isPlayerActive( gameData.getInactivePlayer());
        playerData += "\n| Health: "+ gameData.getInactivePlayer().getHealth()+" | Mana: "+ gameData.getInactivePlayer().getManaCount()+"/"+ gameData.getInactivePlayer().getMaxMana();
        playerData += "\n| Deck size: "+ gameData.getInactivePlayer().cardset.getDeck().size()+" | Discard pile: "+ gameData.getInactivePlayer().cardset.getDiscardPile().size();
        playerData += "\n| Player 2 hand: "+ gameData.getInactivePlayer().cardset.getHand();
        playerData += "\n| Player 2 deck: "+ gameData.getInactivePlayer().cardset.getDeck();
        playerData += "\n|----------------------------------|";

        return playerData;
    }

    public String printHand(){
        String hand = "\n----------------";
        hand += "\nHand of player "+ gameData.getActivePlayer().getName();
        hand += "\nMana: "+ gameData.getActivePlayer().getManaCount();
        hand += "\nSelect card:";
        for(int index = 0; index <  gameData.getActivePlayer().cardset.getHand().size(); index++){
            hand += "\n"+index + " > " +  gameData.getActivePlayer().cardset.getHand().get(index);
        }
        hand += "\n----------------";

        return hand;
    }

    public String invalidCommand(){
        return "The command you entered is not valid! Try again";
    }
}