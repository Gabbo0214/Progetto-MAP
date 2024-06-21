/**
 * Classe per la definizione delle stanze col negozio di spade e armature
 */
package roomSet;

import base.Room;
import base.Stobj;
import gameCore.Player;
import objectSet.Armor;
import objectSet.Weapon;

public class WeaponRoom extends Room {

    private boolean f = false;
    private boolean exc = false;

    public WeaponRoom(){
        Weapon lsword = new Weapon();
        Armor armor = new Armor();
        Stobj Armour = new Stobj("Armiere", "Sembra essere l'unica armeria del castello. Il suo negozio è pieno di armi lucenti e ha un forte odore di ferro e olio.");
        lsword.setName("Ascia");
        lsword.setDescription("Un'ascia con una lama affilata e lucente. Sicuramente sarà molto potente come arma da \n battaglia.");
        lsword.setPickupable(false);
        lsword.setAlias(new String[]{"arma"});
        lsword.setDamage(8);
        armor.setName("Armatura");
        armor.setDescription("Una armatura di cuoio realizzata a mano\nServirà per difenderti dai nemici sul tuo cammino. Costa 2 monete");
        armor.setPickupable(false);
        armor.setAlias(new String[]{"armatura, indumento, vestito, indumenti, vestiti"});
        armor.setDefense(3);
        this.addObject(Armour);
        this.addObject(lsword);
        this.addObject(armor);
    }
    
     /**
     * Acquisto e scambio di equipaggiamenti. Interazione basata sull'oggetto buy e aggiunta dell'acquisto nel'inventario del Player p. Interazione aggiuntiva nel caso di possesso lo Stobj con nome "Coltello"
     * @param p
     * @param buy 
     */
    @Override
    public void buy(Player p, Stobj buy){
        boolean c = false;
        int k = 0;
        if (buy.getName().equals("Armiere")){
            this.setMsg("Sarebbe comodo poterlo portare insieme durante la tua avventura ma purtroppo non puoi. Ha una famiglia da mantenere e deve tornare a lavoro");
        } else
        //boolean aq = false;
        if (this.getObjects().contains(buy)){
            if (p.getMoney()<2){
                  this.setMsg("Non hai abbastanza monete per poter acquistare");
            } else {
                if (buy.getName().equals("Ascia")){
                    if (!this.exc && this.f) {
                        for (int i = 0; i < p.getInventory().size(); i++) {
                            if (p.getInventory().get(i).getName().equals("Spada")){
                                c = true;
                                k = i;
                            }
                        }
                        if (c) {
                            p.removeFromInventory(k);
                            this.setMsg("Hai ottenuto un' ascia affilata. Lasci la spada e ben 2 monete");
                            buy.setDescription(buy.getDescription().substring(0, buy.getDescription().length() - 15));
                            p.addToInventory(buy);
                            p.setMoney(p.getMoney() - 2);
                            this.getObjects().remove(buy);
                            this.exc = true;
                        } else {
                            this.setMsg("Non hai abbastanza monete per acquistare l'ascia. \nProva a parlare col commesso, magari può offrirti uno scambio");
                        }
                    } else {
                        this.setMsg("Non hai abbastanza monete per acquistare l'ascia. \nProva a parlare col commesso, magari può offrirti uno scambio");
                    }
                } else {
                    this.setMsg("Hai ottenuto una " + buy.getName());
                    buy.setDescription(buy.getDescription().substring(0, buy.getDescription().length()-15));
                    p.addToInventory(buy);
                    p.setMoney(p.getMoney()-2);
                    this.getObjects().remove(buy);
                }
            }
        } else {
              this.setMsg("La " + buy.getName() + " non è più disponibile. Probabilmente l'hai già acquistata");
        }
    }

    /**
     * Interazione di dialogo conil fabbro. Interazione basata su Stobj person. Dialogo aggiuntivo nel caso di possesso lo Stobj con nome "Coltello" nell'inventario del Player p
     * @param p
     * @param person 
     */
    @Override
    public void talkTo(Player p, Stobj person){
        if (person.getName().equals("Armiere")){
            if (!this.exc){
                boolean c = false;
                this.setMsg("Dato che non hai abbastanza monete per acquistare l'ascia, il proprietario dell'armeria \nti propone un affare: otterresti l'ascia in cambio di sole 2 monete se riuscissi a \ndargli un'altra arma in cambio...");
                for (int i = 0; i < p.getInventory().size(); i++) {
                    if (p.getInventory().get(i).getName().equals("Spada")) {
                        c = true;
                        break;
                    }
                }
                if (c){
                    this.setMsg(this.getMsg() + "\nHai una spada nello zaino, potresti usarlo per lo scambio!");
                    this.f=true;
                }
            } else {
                this.setMsg("Ti da consigli su come maneggiare e fare manutenzione alla tua nuova ascia \nma il suo martellare sul legno  è tanto fastidioso da costringerti ad allontanarti");
            }
        }
    }
    
      /**
     * Interazione per lo scambio nel caso del possesso dello Stobj con nome "Coltello" nell'inventario del Player p. Viene aggiunta "Ascia" all'inventario in sostituzione di Coltello, oltre alla riduzione di 2 di Money in Player
     * @param p
     * @param obj 
     */
    @Override
    public void give(Player p, Stobj obj){
            if (obj.getName().equals("Spada")){
                 if (!this.exc && this.f){
                     if (p.getMoney()>=2){
                     int k = -1;
                     for (int i = 0; i < p.getInventory().size(); i++) {
                                 if (p.getInventory().get(i).getName().equals("Spada")) {
                                     k = i;
                                 }
                             }
                             if (k>-1) {
                                p.removeFromInventory(k);
                                this.setMsg("Hai ottenuto un'ascia affilata. Lasci la spada e 2 monete al proprietario dell'armeria");
                                 this.getObjects().get(1).setDescription( this.getObjects().get(1).getDescription().substring(0,  this.getObjects().get(1).getDescription().length() - 15));
                                 p.addToInventory( this.getObjects().get(1));
                                 this.getObjects().remove(1);
                                 p.setMoney(p.getMoney() - 2);
                                 this.exc = true;
                             } else {
                                  this.setMsg("Non hai nulla da scambiare.");
                             }
                 } else {
                          this.setMsg("Non hai abbastanza monete per completare lo scambio.");
                     }
                } else {
                    this.setMsg("Non sai se potrebbe interessargli.");
                }
            } else {
                 this.setMsg("Non credi che potrebbe interessargli.");
            }
    }
}
