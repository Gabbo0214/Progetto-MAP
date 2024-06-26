/**
 * Classe per la definizione delle stanze col negozio di spade e armature
 */
package room;

import base.Room;
import base.Stobj;

public class WeaponRoom extends Room {

    public WeaponRoom(){
        Stobj axe = new Stobj();
        axe.setName("Ascia");
        axe.setDescription("Una vecchia ascia è sdraiata sul tavolo dove un tempo un armiere conduceva i suoi affari.\nGli intagli sul manico dimostrano bravura e donano bellezza ed eleganza a qualcosa che sarebbe stato sporcato di sangue.");
        axe.setPickupable(true);
        axe.setAlias(new String[]{"Arma"});
        this.addObject(axe);

        Stobj dagger = new Stobj();
        dagger.setName("Pugnale");
        dagger.setDescription("Un pugnale è incastonato su una delle pareti della stanza. Solo il manico sporge dalla parete, non riesci a rimuoverlo da lì.");
        dagger.setPickupable(false);
        dagger.setAlias(new String[]{"Coltello"});
        this.addObject(dagger);    
    }
    
}
