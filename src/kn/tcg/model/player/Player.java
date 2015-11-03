package kn.tcg.model.player;

import kn.tcg.model.cardset.Cardset;

public class Player {
    private int health = 30;
    private int maxHealth = 30;
    private int manaCount = 0;
    private int maxMana = 10;
    public Cardset cardset;
    private String name;

    public Player(String name){
        this.cardset = new Cardset();
        this.name = name;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public int getMaxMana(){
        return maxMana;
    }

    public void increaseMana(){
        if(manaCount < maxMana){
            this.manaCount += 1;
        }
    }

    public int getManaCount(){
        return manaCount;
    }

    public String getName(){
        return name;
    }

    public void reduceHealth(int damage){
        health = health-damage;
    }

    public void increaseHealth(int amount){
        if ((health+amount) <= getMaxHealth()){
            health += amount;
        }else if((health+amount) >= getMaxHealth()){
            setHealth(getMaxHealth());
        }
    }

    public void reduceMana(int amount){
        manaCount = manaCount-amount;
    }

    public boolean hasEnoughMana(int input){
        return getManaCount() >= input;
    }

    public boolean isHealthFull(){
        return getHealth() == getMaxHealth();
    }
}
