/**
 * Definizione di stanza del tesoro
 */
package roomSet;

import base.Room;
import base.Stobj;
import gameCore.Player;

public class TreasureRoom extends Room {

    // private boolean open = false;

    public TreasureRoom() {

        Stobj enigma = new Stobj();
        Stobj silverCoin = new Stobj();
        Stobj metalDoor = new Stobj();

        enigma.setName("Placca di metallo");
        enigma.setAlias(new String[] { "placca", "enigma", "indovinello", "fessura" });
        enigma.setDescription(
                "Una grossa placca di metallo sul lato ovest della stanza ha una fessura e questa iscrizione: \n\n\n Tra le fredde colline innevate si erige la maestosa creatura. \n Il bianco guardiano è la chiave.");
        this.addObject(enigma);

        silverCoin.setName("Moneta argentata");
        silverCoin.setAlias(
                new String[] { "monetaargentata", "monetad'argento", "monetadiargento", "monetaa", "monetadue" });
        silverCoin.setDescription(
                "Una moneta argentata raffigurante un drago dormiente /n I rilievi sembrano essere fatti a mano.");
        silverCoin.setPickupable(true);
        this.addObject(silverCoin);

        metalDoor.setName("Porta Chiusa");
        metalDoor.setDescription(
                "Una porta decorata da particolari motivi. /n I vari rilievi raffigurano delle costellazioni.");
        metalDoor.setPickupable(false);
        this.addObject(metalDoor);
    }

    public void riddle(Stobj coin, Player p) {
        if (coin.getName() == "Moneta dorata") {
            // Moneta giusta, apri la porta e rimuove la moneta
            this.getObjects().remove(coin);
            this.setMsg("Hai inserito la moneta dorata. La porta si apre con un suono sordo.");
        } else if (coin.getName() == "Moneta argentata") {
            // Moneta sbagliata, infligge danno
            this.getObjects().remove(coin); // Rimuove la moneta rotta
            p.setCurrentHp(p.getCurrentHp() - 10);
            this.setMsg(
                    "Hai inserito la moneta argentata. \n Senti un rumore molto acuto e la moneta viene sparata contro di te. \n Hai perso 10 HP per l'urto.");
        } else {
            this.setMsg("Non puoi usare questo oggetto qui.");
        }
    }

    /**
     * Interazione per l'inserimento dello Stobj con nome "Moneta dorata" se
     * presente nella lista Inventory del Player p. Al momento dell'inserimento,
     * viene aperto un passaggio
     * 
     * @param p
     */
    @Override
    public void insert(Player p) {
        boolean g = false;
        boolean h = false;
        int k = -1;
        for (int i = 0; i < p.getInventory().size(); i++) {
            if (p.getInventory().get(i).getName().equals("Moneta dorata")) {
                g = true;
                k = i;
            } else if (p.getInventory().get(i).getName().equals("Moneta argentata")) {
                g = true;
                h = true;
                k = i;
            }
        }
        if (g && !h) {
            this.setMsg("Hai inserito la moneta dorata. La porta si apre con un suono sordo.\nOra puoi andare a nord.");
            p.removeFromInventory(k);
            this.getObjects().removeIf(obj -> obj.getName().equals("Placca di Metallo"));
            this.getObjects().removeIf(obj -> obj.getName().equals("Porta Chiusa"));
            this.setNorth(this.getNextNorth());
        } else if (g && h) {
            // Moneta sbagliata, infligge danno
            p.removeFromInventory(k); // Rimuove la moneta rotta
            p.setCurrentHp(p.getCurrentHp() - 10);
            this.setMsg(
                    "Hai inserito la moneta argentata. \n Senti un rumore molto acuto e la moneta viene sparata contro di te. \n Hai perso 10 HP per l'urto.");
        } else {
            this.setMsg("Non hai nulla da inserire ne nulla in cui inserire :(");
        }
    }
}