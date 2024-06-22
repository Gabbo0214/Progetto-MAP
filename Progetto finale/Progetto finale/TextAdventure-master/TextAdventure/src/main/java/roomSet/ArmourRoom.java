package roomSet;

import base.Stobj;


public class ArmourRoom extends RoomWDoor {

    public ArmourRoom() {
        Stobj obj = new Stobj();
        obj.setName("Libro con enigma");
        obj.setAlias(new String[]{"enigma", "indovinello"});
        obj.setDescription("Non fa rumore, non emette suoni,\n" +
                "Eppure senza di lui non dormono i leoni.\n" +
                "Che cos'è?");
        this.addObject(obj);
    }

    /**
     * Interazione per la risoluzione dell'enigma
     */
    public void riddle(){
        this.setMsg("Nel momento in cui pronunci la risposta, il libro si chiude svanendo! Il castello sta iniziando a mettere paura");
        for (int i = 0; i < this.getObjects().size(); i++) {
            if (this.getObjects().get(i).getName().equals("Libro con enigma")) {
                this.getObjects().remove(i);
                break;
            }
        }
        this.setDescription("Hai risolto correttamente l'enigma complimenti! Purtroppo questo enigma ha semplicemnete messo alla prova le tue conoscenze senza darti nulla in cambio!!");
    }
}