/**
 * Definizione di camera delle profezie
 */
package room;

import base.Room;
import base.Stobj;
import gameCore.Player;

public class ProphecyChamber extends Room {

    private boolean s = false;
    private boolean c = false;
    private boolean d = false;

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
     * @param lever
     * @param p
     */
    public void activate(String lever, Player p) {
        boolean leverFound = false;

        if (this.getObjects().get(2).getAlias().contains(lever)) {
            leverFound = true;
            s = !s;
            if (!s)
                this.setMsg("Senti un suono di ingranaggi in movimento.\nLa leva è nel suo stato normale.\n");
            else
                this.setMsg("Senti un suono di ingranaggi in movimento.\nLa leva è stata tirata.\n");
        } else if (this.getObjects().get(1).getAlias().contains(lever)) {
            leverFound = true;
            c = !c;
            if (d)
                d = !d;
            if (!c)
                this.setMsg("Senti un suono di ingranaggi in movimento.\nLa leva è nel suo stato normale.\n");
            else
                this.setMsg("Senti un suono di ingranaggi in movimento.\nLa leva è stata tirata.\n");
        } else if (this.getObjects().get(0).getAlias().contains(lever)) {
            leverFound = true;
            d = !d;
            if (!d)
                this.setMsg("Senti un suono di ingranaggi in movimento.\nLa leva è nel suo stato normale.\n");
            else
                this.setMsg("Senti un suono di ingranaggi in movimento.\nLa leva è stata tirata.\n");
        }

        // Controlla la condizione dopo aver aggiornato tutti gli stati
        if (!s && c && d) {
            Stobj memory = new Stobj("Memoria del passato", "Un ricordo lontano.");
            memory.setVisible(false);
            p.addToInventory(memory);
            this.setMsg(
                    "La tua visione scompare per un momento, e i ricordi di eroi del passato invadono la tua mente per un breve istante.\nQuando la tua vista ritorna, ti pare di udire un forte rumore dalla stanza a nord.");
        }

        // Controlla se nessuna leva è stata trovata
        if (!leverFound) {
            this.setMsg("Non hai menzionato una leva valida.\n");
        }
    }
}