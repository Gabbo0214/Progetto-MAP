/**
 * Definizione della casa come partenza e fine del gioco
 */
package roomSet;

import base.Stobj;
import gameCore.Player;
import objectSet.Door;
import objectSet.Weapon;

public class CastleEntrance extends RoomWDoor{
    

    public CastleEntrance(){
        Weapon knife = new Weapon();
        Stobj obj = new Stobj();
        Door door = new Door();
        Stobj Guardian = new Stobj("Guardiano", "Un guardiano imponente in armatura scintillante ti blocca, con occhi luminosi e una spada incantata pronta a difendere il luogo sacro.");

        knife.setName("Spada");
        knife.setAlias(new String[]{"arma"});
        knife.setDescription("Una spada lunga e affilata, con una lama lucente e un'elsa intarsiata di gemme scintillanti, ma non particolarmente potente.");
        knife.setDamage(3);
        knife.setPickupable(true);
        this.addObject(knife);

        obj.setName("Ciondolo");
        obj.setDescription("Un ciondolo a forma di luna in argento lucente, con una gemma blu al centro che emana \n un bagliore misterioso. Sarà forse un antico artefatto magico o solo un bell'ornamento?");
        obj.setPickupable(true);
        this.addObject(obj);

        door.setName("Porta");
        door.setDescription("Una porta di legno con intarsi intricati. Si affaccia proprio su una stanza chiamata  \nSala della Luna. Cosa potrebbe nascondere al suo interno?");
        door.setDirection("n");
        door.setOpen(false);
        this.addObject(door);

        this.addObject(Guardian);
    }

    /**
     * Interazione di dalogo con la mamma. Interazione aggiuntiva e finale in caso di possesso dello Stobj con nome "Pozione Millecure"
     * @param p
     * @param person 
     */
    @Override
    public void talkTo(Player p, Stobj person){
        if (person.getName().equals("Guardiano")){
            int pm = -1;
            for (int i = 0; i<p.getInventory().size(); i++)
                    if (p.getInventory().get(i).getName().equals("Pozione Millecure")) {
                        pm = i;
                    }
            if (pm > -1){
                p.removeFromInventory(pm);
               this.setMsg ("FINE");
            } else {
                this.setMsg("\"Benvenuto, Sono Elias, custode di queste mura millenarie. Prima di proseguire, sappi che il pericolo e i segreti si nascondono dietro ogni angolo di questo luogo misterioso. Se hai \nbisogno di saggezza o consigli, non esitare a chiedere. Ma ricorda, il destino di questo \ncastello è nelle tue mani ora.\"");
            }
        }
    }
    
    /**
     * Interazione di cessione dello Stobj con nome "Pozione Millecure". In caso la pozione, indicata da obj, fosse presente nella lista Inventory del Player p, trigger dell dialogo finale del gioco
     * @param p
     * @param obj 
     */
    @Override
    public void give(Player p, Stobj obj){
        if (obj.getName().equals("Pozione") || obj.getName().equals("Pozione Millecure")){
        int pm = -1;
            for (int i = 0; i<p.getInventory().size(); i++)
                    if (p.getInventory().get(i).getName().equals("Pozione Millecure"))
                        pm = i;
            if (pm > -1){
                p.removeFromInventory(pm);
               this.setMsg ("FINE");
            } else {
                for (int i = 0; i<p.getInventory().size(); i++)
                    if (p.getInventory().get(i).getName().equals("Pozione"))
                        pm = i;
                 if (pm > -1)
                     this.setMsg("Nessun'altra pozione se non quella dello sciamano può curarla,\nnon ha senso consumarne inutilmente");
                 
            }
        } else {
             this.setMsg("Non succede niente");
        }
    }
}
