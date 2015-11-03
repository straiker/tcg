package kn.tcg.model.gamedata.data;

import kn.tcg.model.gamedata.stages.GameStage;
import kn.tcg.model.player.Player;

public class GameData {
    private Player inactivePlayer;
    private Player activePlayer;
    private Player winner;
    private GameStage gameStage = GameStage.START;

    private int roundNumber = 0;

    public void increaseRound(){
        roundNumber += 1;
    }

    public int getRoundNumber(){
        return roundNumber;
    }

    public Player getInactivePlayer(){
        return inactivePlayer;
    }

    public void setInactivePlayer(Player player){
        this.inactivePlayer = player;
    }

    public void setActivePlayer(Player player){
        this.activePlayer = player;
    }

    public Player getActivePlayer(){
        return activePlayer;
    }

    public void setWinner(Player winner){
        this.winner = winner;
    }

    public Player getWinner(){
        return winner;
    }

    public boolean isPlayerActive(Player player){
        return getActivePlayer().equals(player);
    }

    public void setGameStage(GameStage gameStage){
        this.gameStage = gameStage;
    }

    public Enum getGameStage(){
        return gameStage;
    }
}
