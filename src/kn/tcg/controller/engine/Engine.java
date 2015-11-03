package kn.tcg.controller.engine;

import kn.tcg.model.actions.attack.Attack;
import kn.tcg.model.actions.heal.Heal;
import kn.tcg.model.command.CommandData;
import kn.tcg.model.gamedata.data.GameData;
import kn.tcg.model.gamedata.stages.GameStage;
import kn.tcg.model.messages.Messages;
import kn.tcg.model.player.Player;

import java.util.Observable;
import java.util.Random;

import static kn.tcg.model.gamedata.stages.GameStage.*;

public class Engine extends Observable {
    private GameData gameData;
    private Attack attack;
    private Heal heal;
    private Player player1;
    private Player player2;
    private Messages messages;
    private CommandData commands;

    public Engine(){
        gameData = new GameData();
        attack = new Attack();
        heal = new Heal();
        messages = new Messages(gameData);
        commands = new CommandData();
    }

    public GameData getGameData(){ return gameData; }

    public void initGame(){
        player1 = new Player("Player1");
        player2 = new Player("Player2");

        boolean randomPlayer = new Random().nextBoolean();
        if (randomPlayer){
            getGameData().setActivePlayer(player1);
            getGameData().setInactivePlayer(player2);
        }else{
            getGameData().setActivePlayer(player2);
            getGameData().setInactivePlayer(player1);
        }

        getGameData().getActivePlayer().cardset.drawInitialHand();
        getGameData().getInactivePlayer().cardset.drawInitialHand();

        getGameData().getActivePlayer().increaseMana();
        getGameData().getActivePlayer().cardset.drawNewCard();
        getGameData().getInactivePlayer().cardset.drawNewCard();

        getGameData().increaseRound();
        getGameData().setGameStage(STANDBY);
    }

    public void nextRound(){
        changeActiveInactivePlayers();
        getGameData().increaseRound();
        getGameData().getActivePlayer().increaseMana();


        if (getGameData().getActivePlayer().cardset.getHand().size() < getGameData().getActivePlayer().cardset.getMaxHandSize()) {
            getGameData().getActivePlayer().cardset.drawNewCard();
        } else {
            getGameData().getActivePlayer().reduceHealth(1);
            if(getGameData().getActivePlayer().cardset.getDeck().size() > 0) {
                getGameData().getActivePlayer().cardset.moveCardToDiscardPile();
            }
        }
    }

    public String endGame(Player winner){
        getGameData().setWinner(winner);
        return "The winner is: "+ getGameData().getWinner().getName();
    }

    public void changeActiveInactivePlayers(){
        if (getGameData().getInactivePlayer().equals(player1)){
            getGameData().setActivePlayer(player1);
            getGameData().setInactivePlayer(player2);
        }else{
            getGameData().setActivePlayer(player2);
            getGameData().setInactivePlayer(player1);
        }
    }

    public String performCommand(String input){
        Enum stage = getGameData().getGameStage();
        String command = parseInput(input, stage);
        String message = "";

        switch (GameStage.valueOf(stage.name())){
            case STANDBY:
                message = processStandby(command);
                break;
            case START:
                message = processStart(command);
                break;
            case HEAL:
                message = processHeal(command);
                break;
            case ATTACK:
                message = processAttack(command);
                break;
        }
        return message;
    }

    private String processStart(String command){
        String message;
        if ("start".equals(command)){
            initGame();
            message = messages.commandList() +"\n"+ messages.displayPlayerData();
        }else if ("error".equals(command)){
            message = messages.invalidCommand();
        } else {
            message = messages.gameCanceled();
        }
        return message;
    }

    private String processStandby(String command){
        String message = "";
        if ("attack".equals(command)){
            message = messages.printHand();
            getGameData().setGameStage(ATTACK);
        } else if("heal".equals(command)){
            message = messages.printHand();
            getGameData().setGameStage(HEAL);
        } else if("end".equals(command)){
            if ((getGameData().getInactivePlayer().cardset.getDeck().size() == 0) && (getGameData().getInactivePlayer().cardset.getHand().size() == 0)) {
                message = endGame(getGameData().getActivePlayer());
            }
            if (getGameData().getActivePlayer().getHealth() <= 0){
                message = endGame(getGameData().getInactivePlayer());
            }else{
                nextRound();
                message = messages.commandList() +"\n"+ messages.displayPlayerData();
            }
        } else if("quit".equals(command)){
            message = messages.gameCanceled();
        } else if ("error".equals(command)){
            message = messages.invalidCommand();
        }
        return message;
    }

    private String processAttack(String command){
        String message;
        if (command.equals("exit")){
            message = messages.commandList() +"\n"+ messages.displayPlayerData();
            getGameData().setGameStage(STANDBY);
        } else if (command.equals("error")){
            message = messages.invalidCommand();
        } else{
            message = attack.performAction(gameData, commandToInt(command));
            if (getGameData().getInactivePlayer().getHealth() <= 0){
                message += "\n"+endGame(getGameData().getActivePlayer());
            }else message += "\n"+messages.printHand();
        }
        return message;
    }

    private String processHeal(String command){
        String message;
        if (command.equals("exit")){
            message = messages.commandList() +"\n"+ messages.displayPlayerData();
            getGameData().setGameStage(STANDBY);
        } else if (command.equals("error")){
            message = messages.invalidCommand();
        } else{
            message = heal.performAction(gameData, commandToInt(command));
            message += messages.printHand();
        }
        return message;
    }

    public int commandToInt(String command){
        int intCommand = 0;

        try{
            intCommand = Integer.parseInt(command);
        }catch (NumberFormatException e){
            e.getMessage();
        }

        return intCommand;
    }

    public String parseInput(String input, Enum stage){
        String finalCommand = "";
        if (stage.equals(STANDBY)) {
            if (commands.getStandbyCommands().contains(input)) {
                if (input.equals("0") || input.equals("quit") || input.equals("q")|| input.equals("q")) finalCommand = "quit";
                if (input.equals("1") || input.equals("end")) finalCommand = "end";
                if (input.equals("2") || input.equals("a") || input.equals("attack")) finalCommand = "attack";
                if (input.equals("3") || input.equals("h") || input.equals("heal")) finalCommand = "heal";
            } else finalCommand = "error";

        } else if (stage.equals(ATTACK)) {
            if (commands.getActionCommands().contains(input)) {
                if (input.equals("6")) finalCommand = "exit";
                else finalCommand = input;
            } else finalCommand = "error";

        } else if (stage.equals(HEAL)) {
            if (commands.getActionCommands().contains(input)) {
                if (input.equals("6")) finalCommand = "exit";
                else finalCommand = input;
            } else finalCommand = "error";

        } else if (stage.equals(START)) {
            if(commands.getStartCommands().contains(input)){
                if (input.equals("1") || input.equals("start") || input.equals("s")) finalCommand = "start";
                else if (input.equals("0") || input.equals("quit") || input.equals("q")) finalCommand = "quit";
            } else finalCommand = "error";
        }
        return finalCommand;
    }
}