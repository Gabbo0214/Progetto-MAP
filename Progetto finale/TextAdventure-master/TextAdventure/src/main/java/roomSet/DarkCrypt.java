/**
 * Definizione di cripta oscura
 */
package roomSet;

import base.Room;
import base.Stobj;
import gameCore.Player;

public class DarkCrypt extends Room {

    public DarkCrypt() {
        Stobj weepingStatue = new Stobj();
        Stobj plaque = new Stobj();
        Stobj demonStatue = new Stobj();
        Stobj pulsatingHeart = new Stobj();

        weepingStatue.setName("Statua piangente");
        weepingStatue.setAlias(new String[] { "Statua", "Figura", "Scultura" });
        weepingStatue.setDescription("La statua di una donna piangente.\nUna larga fessura è presente nel mezzo del petto.");
        weepingStatue.setPickupable(false);
        this.addObject(weepingStatue);

        plaque.setName("Strana roccia");
        plaque.setAlias(new String[] { "Masso", "Sasso", "Roccia" });
        plaque.setDescription("Ispezionando più da vicino la roccia, è possibile scorgere una scritta incavata sulla superficie.\nRecita: Il malvagio si maschera con le pelli dei giusti e degli innocenti.\nTrafiggerai il suo cuore?");
        plaque.setPickupable(false);
        this.addObject(plaque);

        demonStatue.setName("Statua inquietante");
        demonStatue.setAlias(new String[] { "Statua", "Figura", "Scultura", "Demone" });
        demonStatue.setDescription("La figura irrequieta trafitta dalla spada fa accaponare la pelle.\nTi passa immediatamente la voglia di fissarla.");
        demonStatue.setPickupable(false);

        pulsatingHeart.setName("Cuore pulsante");
        pulsatingHeart.setAlias(new String[] { "Cuore" });
        pulsatingHeart.setDescription("Ciò che sembra essere un cuore pulsante fatto di pietra.\nLa bocca della statua lo ha lasciato cadere una volta che è stata trafitta.");
        pulsatingHeart.setPickupable(false);
    }

    /**
     * Interazione per l'inserimento della spada. Al momento dell'inserimento,
     * viene ottenuto un oggetto.
     * 
     * @param p
     */
    @Override
    public void insert(Player p) {

        boolean g = false;
        int k=-1;
        for (int i = 0; i < p.getInventory().size(); i++) {
            if (p.getInventory().get(i).getName().equals("Spada")) {
                g = true;
                k = i;
            } 
        }
        if (g) {
            this.setMsg("Hai trafitto la statua con la tua spada.\nImprovvisamente, la faccia della donna sembra trasformarsi\nnel ghigno di quello che pare essere un demone.\nDalla bocca della statua cade qualcosa.");
            p.removeFromInventory(k);
            this.getObjects().removeIf(obj -> obj.getName().equals("Statua piangente"));

            for (Stobj obj : this.getObjects()) {
                if (obj.getName().equals("Statua inquietante")) {
                    this.addObject(obj);
                    break;
                }
            }

            for (Stobj obj : this.getObjects()) {
                if (obj.getName().equals("Cuore pulsante")) {
                    obj.setPickupable(true);
                    this.addObject(obj);
                    break;
                }
            }
        }else {
            this.setMsg("Non hai nulla da inserire");
        }
    }
}