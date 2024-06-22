/**
 * Definizione di stanza del tesoro
 */
package roomSet;

import base.Room;
import base.Stobj;
import gameCore.Player;

public class ProphecyChamber extends Room {

    // private boolean open = false;

    public ProphecyChamber() {

        Stobj dxlever = new Stobj("Leva destra",
                "Una semplice leva. Finchè non l'azioni non sai cosa attiva. Si trova alla tua destra.");
        Stobj ctlever = new Stobj("Leva centrale",
                "Una semplice leva. Finchè non l'azioni non sai cosa attiva. Si trova di fronte a te, nel mezzo della stanza.");
        Stobj sxlever = new Stobj("Leva sinistra",
                "Una semplice leva. Finchè non l'azioni non sai cosa attiva. Si trova alla tua sinistra.");
        dxlever.setAlias(new String[] { "destra" });
        dxlever.setUsable(true);
        ctlever.setAlias(new String[] { "centro", "centrale" });
        ctlever.setUsable(true);
        sxlever.setAlias(new String[] { "sinistra" });
        sxlever.setUsable(true);
        this.addObject(dxlever);
        this.addObject(ctlever);
        this.addObject(sxlever);
    }

    /**
     * Interazione con le leve.
     * 
     * @param p
     */

    public void activate(String lever, Player p) {
        if (this.getObjects().get(0).getAlias().contains(lever)) {
            this.setMsg("Senti un suono di ingranaggi in movimento.");
        } else if (this.getObjects().get(1).getAlias().contains(lever)) {
            this.setMsg("Senti un suono di ingranaggi in movimento.");
        } else if (this.getObjects().get(2).getAlias().contains(lever)) {
            this.setMsg("Senti un suono di ngranaggi in movimento.");
        }
    }
}