package gameCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import base.Stobj;

  
public class Player implements Serializable {

    private final int tothp = 30;
    private int currenthp = 30;
    private List<Stobj> inventory = new ArrayList<>();

    public int getTotHp(){
        return this.tothp;
    }

    public void setCurrentHp(int hp){
        this.currenthp = hp;
    }

    public int getCurrentHp(){
        return this.currenthp;
    }

    public void setInventory(List<Stobj> inventory){
        this.inventory = inventory;
    }

    public List<Stobj> getInventory(){
        return this.inventory;
    }

    public void addToInventory(Stobj obj) {
        this.inventory.add(obj);
    }

    public void removeFromInventory(Stobj obj) {
        this.inventory.remove(obj);
    }

    public void removeFromInventory(int i){this.inventory.remove(i);}
    
    public void setPlayer(Player p){
       this.setCurrentHp(p.getCurrentHp());
       this.setInventory(p.getInventory());
    }
}
