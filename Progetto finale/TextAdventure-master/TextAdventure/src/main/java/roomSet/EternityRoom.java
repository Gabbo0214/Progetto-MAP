/**
 * Definizione della stanza di gioco
 */
package roomSet;

import base.Stobj;
import gameCore.Player;
import objectSet.Door;



public class EternityRoom extends RoomWDoor{
    
    public EternityRoom(){
        Door door = new Door();
        door.setName("Porta");
        door.setDescription("Una semplice porta di legno. E' aperta.");
        door.setOpen(true);
        door.setDirection("s");
        this.addObject(door);
        Stobj obj = new Stobj();
        obj.setName("Serratura");
        obj.setAlias(new String[]{"buco"});
        obj.setDescription("Una semplice serattura , sarebbe bello avere la chiave corretta per poterla aprire...");
        this.addObject(obj);
    }

    /**
     * Interazione per l'inserimento dello Stobj con nome "Chiave" se presente nella lista Inventory del Player p. Al momento dell'inserimento, viene aperto un passaggio
     * @param p 
     */
    @Override
    public void insert(Player p){
        boolean g = false;
        int k = -1;
        for (int i =0; i<p.getInventory().size(); i++){
            if (p.getInventory().get(i).getName().equals("Chiave")){
                 g = true;
                 k = i;
            }
        }
        if (g){
              this.setMsg("Appena avvicini la chiave nella serratura, sembra inserirsi subito di colpo, come attratta dalla serratura.\n Non riesci a capire cosa stia succedendo." +
                "\nImprovvisamente la porta si apre permettendoti di proseguire a nord.");
             p.removeFromInventory(k);
            this.getObjects().removeIf(obj -> obj.getName().equals("Serratura"));
            this.setNorth(this.getNextNorth());
        } else {
            this.setMsg("Non hai nulla da inserire da alcuna parte.");
        }
        }
}

