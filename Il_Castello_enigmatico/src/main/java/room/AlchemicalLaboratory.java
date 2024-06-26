/**
 * Classe per la definizione delle stanze col negozio di pozioni
 */
package room;

import base.Room;
import base.Stobj;
import objectgame.Potion;

public class AlchemicalLaboratory extends Room {

    
    public AlchemicalLaboratory(){
        Stobj potion = new Potion();
        potion.setName("Pozione");
        potion.setDescription("Una pozione creata da una delle alchimiste del villaggio in tempi antichi.\nRipristina un po' di salute.");
        potion.setUsable(true);
        potion.setPickupable(true);
        this.addObject(potion);

        Stobj instruments = new Stobj();
        instruments.setDescription("Vari apparecchi antiquati una volta  usati per la creazione di pozioni all'interno delle mura del castello.\nVarie fiasche, becker ed altri strumenti che non hai mai visto, pieni di polvere e quasi tutti evidentemente rotti.\nChissà cosa è accaduto a chi lavorava qui.");
        instruments.setName("Antichi Strumenti");
        instruments.setAlias(new String[]{"Strumenti", "Attrezzi"});
        instruments.setPickupable(false);
    }

}
