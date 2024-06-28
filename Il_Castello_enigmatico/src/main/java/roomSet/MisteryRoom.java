/**
 * Definizione della stanza di gioco
 */
package roomSet;

import base.Room;
import base.Stobj;

public class MisteryRoom extends Room {
    
    public MisteryRoom(){
        Stobj hintOne = new Stobj();
        hintOne.setName("libro giallo");
        hintOne.setAlias(new String[]{"libro giallo", "giallo"});
        hintOne.setDescription("Il libro rosso dice la verità. Se il tempo ti da dubbi, tira la leva destra e poi la sinistra.");
        hintOne.setPickupable(false);
        this.addObject(hintOne);

        Stobj hintTwo = new Stobj();
        hintTwo.setName("libro verde");
        hintTwo.setAlias(new String[]{"libro verde", "verde"});
        hintTwo.setDescription("Il libro Giallo mente ed il rosso anche. Se il tempo ti da dubbi, tira la leva dal centro e poi la destra.");
        hintTwo.setPickupable(false);
        this.addObject(hintTwo);

        Stobj hintThree = new Stobj();
        hintThree.setName("libro rosso");
        hintThree.setAlias(new String[]{"libro rosso", "rosso"});
        hintThree.setDescription("Il libro Verde mente ed il Giallo anche. Se il tempo ti da dubbi, tira la leva dal destra e poi quella al centro.");
        hintThree.setPickupable(false);
        this.addObject(hintThree);
    }
}