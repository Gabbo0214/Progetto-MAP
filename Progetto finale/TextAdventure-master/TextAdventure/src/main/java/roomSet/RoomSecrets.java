package roomSet;

import base.Room;
import base.Stobj;

public class RoomSecrets extends Room {

    public RoomSecrets() {
        Stobj goldCoin = new Stobj();
        goldCoin.setName("Moneta dorata");
        goldCoin.setAlias(new String[] { "monetadorata", "monetad'oro", "monetadioro", "monetad", "monetao", "moneta" });
        goldCoin.setDescription("Una moneta dorata raffigurante il profilo di una tigre imponente /n I rilievi sembrano essere fatti a mano.");
        goldCoin.setPickupable(true);
        this.addObject(goldCoin);
    }

}
