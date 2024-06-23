/**
 * Classe utile alla definizione della mappa di gioco
 */
package gameCore;

import java.io.Serializable;

import base.Room;
import base.Stobj;
import objectSet.Door;
import roomSet.AlchemicalLaboratory;
import roomSet.ArmourRoom;
import roomSet.CastleEntrance;
import roomSet.DarkCrypt;
import roomSet.DestinyRoom;
import roomSet.EternityRoom;
import roomSet.MisteryRoom;
import roomSet.ProphecyChamber;
import roomSet.RoomSecrets;
import roomSet.RoomWDoor;
import roomSet.TimeRoom;
import roomSet.TreasureRoom;
import roomSet.WeaponRoom;

public class Map implements Serializable {
    private Room castle = new CastleEntrance();
    private Room moonRoom = new RoomWDoor();
    private Room laboratory = new AlchemicalLaboratory();
    private Room armour = new WeaponRoom();
    private Room rSecrets = new RoomSecrets();
    private Room armourRoom = new ArmourRoom();
    private Room treasureRoom = new TreasureRoom();
    private Room prophecyChamber = new ProphecyChamber();
    private Room timeRoom = new TimeRoom();
    private Room darkCrypt = new DarkCrypt();
    private Room illusionRoom = new Room();
    private Room oblivionRoom = new Room();
    private Room misteryRoom = new MisteryRoom();
    private Room wyvernLair = new Room();
    private Room storage = new RoomWDoor();
    private Room memoryRoom = new RoomWDoor();
    private Room eternityRoom = new EternityRoom();
    private Room end = new DestinyRoom();

    private Room currentRoom = new Room();
    private Room previousRoom = null;

    /**
     * Costruzione della mappa di gioco
     */
    public Map() {

        Door door = new Door();
        // Creazione delle stanze e assegnazione degli oggetti interagibili

        // Entrata del castello
        castle.setName("Entrata del castello ");
        castle.setDescription("Ti trovi nell'entrata del Castello Enigmatico.\nLe pareti di pietra sono adornate da antichi stemmi e affreschi sbiaditi,\ntestimoni del passato glorioso del castello.");
        castle.setNextNorth(moonRoom);

        // Sala della Luna
        moonRoom.setName("Sala della luna");
        moonRoom.setDescription("Ti trovi nella stanza della luna, una stanza rotonda con un soffitto a cupola che mostra le fasi lunari.\nUn lucernario al centro lascia entrare una luce argentata.\nAl centro della sala c'è un altare di pietra bianca decorato da rune brillanti.\nNon sembra ci sia nulla di utile.");
        door.setName("Porta");
        door.setDescription("E' la porta che conduce all' entrata  del castello.");
        door.setOpen(true);
        door.setDirection("s");
        moonRoom.addObject(door);
        moonRoom.setNorth(rSecrets);
        moonRoom.setEast(armour);
        moonRoom.setSouth(castle);
        moonRoom.setNextSouth(castle);
        moonRoom.setWest(laboratory);

        // Laboratorio Alchemico
        laboratory.setName("Laboratorio Alchemico");
        laboratory.setDescription("Ti trovi nell'antico laboratorio alchemico, nonostante tutte le attrezzature siano cadute in disuso, forse potresti trovare qualcosa di utile.");
        laboratory.setEast(moonRoom);

        // Armeria
        armour.setName("Armeria");
        armour.setDescription("Sei nella piccola Armeria. Sembra ci siano i rimasugli di un vecchio negozio. Davvero il castello permetteva la presenza di mercati e l'accesso alle persone normali?");
        armour.setWest(moonRoom);

        // Camera dei segreti
        rSecrets.setName("Camera dei segreti");
        rSecrets.setDescription("Entrato nella camera dei segrti senti l'aria e l'atmosfera del castello cambiare. Ormai sei andato troppo oltre, devi portare a termine la tua sfida contro gli enigmi che troverai davanti.");
        rSecrets.setNorth(armourRoom);

        // Sala delle armature
        armourRoom.setName("Sala delle armature");
        armourRoom.setDescription("La Sala delle Armature è una stanza maestosa con armature e armi antiche esposte su supporti di legno. Sembrano essere fissati magicamente ad essi, diventando irremovibili.");
        armourRoom.setNorth(treasureRoom);
        armourRoom.setEast(illusionRoom);
        armourRoom.setSouth(rSecrets);
        armourRoom.setWest(oblivionRoom);

        // Stanza del tesoro
        treasureRoom.setName("Stanza del tesoro");
        treasureRoom.setDescription("Nei racconti del regno si narrava di un tempo lontano, nel quale la stanza del tesoro del castello brillava per le mille ricchezze. \nSembra che ora poco rimanga al suo interno. ");
        treasureRoom.setNextNorth(prophecyChamber);
        treasureRoom.setSouth(armourRoom);

        // Camera delle profezie
        prophecyChamber.setName("Camera delle profezie");
        prophecyChamber.setDescription("La camera delle profezie è riempita da macchinari ed aggeggi che sembrano essere usati per lo studio dello spazio e delle magie.\nI motivi a tema astronomico e cosmologico fanno si che il tuo sguardo si perda nel vuoto.");
        prophecyChamber.setNorth(timeRoom);
        prophecyChamber.setSouth(treasureRoom);

        // Sala del tempo
        timeRoom.setName("Sala del tempo");
        timeRoom.setDescription("Sei ora nella sala del tempo del castello. Si dice che qui il tempo scorra più lentamente.\nIl passato, il presente ed il futuro sembrano mescolarsi.");
        timeRoom.setWest(darkCrypt);
        timeRoom.setNextEast(memoryRoom);
        timeRoom.setSouth(prophecyChamber);

        // Cripta Oscura
        darkCrypt.setName("Cripta Oscura");
        darkCrypt.setDescription("Un grezzo buco nel muro ti ha portato in una camera buia e dall'atmosfera inquietante.\nL'aria è capace di far salire brividi gelidi lungo la tua spina dorsale.\nIl tetto basso della stanza rende il tutto opprimente.\nSenti dozzine di presenze toccare le tue spalle.");
        darkCrypt.setEast(timeRoom);

        // Sala dei ricordi
        memoryRoom.setName("Sala dei ricordi");
        memoryRoom.setDescription("Una sala di colore bianco puro dove risaltano solo gli oggetti al suo interno e le porte nero opaco.\nSolo essere in piedi qui dentro ti fa girare la testa.");
        Stobj chiave = new Stobj();
        chiave.setName("Chiave");
        chiave.setDescription("Guarda c'è una chiave , potrebbe servirti a qualcosa potrei consigliarti di prenderla poi fai come vuoi ! non dire che non ti avevo avvisato!!");
        chiave.setPickupable(true);
        chiave.setUsable(true);
        memoryRoom.addObject(chiave);
        door = new Door();
        door.setName("Porta");
        door.setDescription("Guardare una porta non serve a nulla.");
        door.setOpen(false);
        door.setDirection("n");
        memoryRoom.addObject(door);
        door = new Door();
        door.setName("Porta");
        door.setDescription("Guardare una porta non serve a nulla.");
        door.setOpen(true);
        door.setDirection("w");
        memoryRoom.addObject(door);
        memoryRoom.setNextNorth(eternityRoom);
        memoryRoom.setWest(timeRoom);
        memoryRoom.setNextWest(timeRoom);

        // Sala delle illusioni
        illusionRoom.setName("Sala delle illusioni");
        illusionRoom.setDescription("Ti trovi nella Sala delle I?lu?io?i, per la prima volta, vedi una camera che pullula di vita.");
        Stobj illusionOne = new Stobj();
        illusionOne.setName("Guardia");
        illusionOne.setDescription("La figura della guardia imponente è immobile al centro della stanza.\nI suoi occhi sono privi di vita, non vuole parlare.");
        illusionRoom.addObject(illusionOne);
        Stobj illusionTwo = new Stobj();
        illusionTwo.setName("Signora");
        illusionTwo.setDescription("Un anziana signora vestita con un uniforme da alchemista.\nIl copricapo non mi permette di guardarla negli occhi.\nSi rifiuta di rispondermi");
        illusionRoom.addObject(illusionTwo);
        Stobj illusionThree = new Stobj();
        illusionThree.setName("Armaiolo");
        illusionThree.setDescription("Un grosso uomo muscoloso con vestiti da fabbro. Nonostante la stazza e la sua presenza torreggiante, non sembra dare segni di vita.");
        illusionRoom.addObject(illusionThree);
        illusionRoom.setWest(armourRoom);

        // Camera dell'oblio
        oblivionRoom.setName("Camera dell'oblio ");
        oblivionRoom.setDescription("Sai che proseguendo in questa direzione raggiungerai la sala dei misteri! Chissà quale evento le ha fatto meritare un tale nome.");
        oblivionRoom.setEast(armourRoom);
        oblivionRoom.setWest(misteryRoom);

        // Sala dei misteri
        misteryRoom.setName("Sala dei misteri");
        misteryRoom.setDescription("Qualcosa non sembra essere normale qui...");
        misteryRoom.setEast(oblivionRoom);

        this.setCurrentRoom(castle);

        // stanza dell' Eternità
        eternityRoom.setName("Stanza dell'eternità");
        eternityRoom.setDescription("Sei nella Stanza dell'Eternità. Guardando in avanti non riesci a notare una fine alle pareti che sembrano estendersi all'infinito. In mezzo al nulla noti una serratura che sembra fluttuare.");
        eternityRoom.setNextNorth(end);
        eternityRoom.setSouth(memoryRoom);
        eternityRoom.setNextSouth(memoryRoom);

        // sala del destino
        end.setName("Sala del destino");
        end.setDescription("Sei nella Stanza del Destino.\nLe pareti sono rivestite di antichi arazzi che raffigurano il ciclo della vita.\nAl centro della stanza, un altare d'oro brillante risplende sotto una luce misteriosa.\n" +
        "I tuoi occhi cadono su un antica pergamena posata sull'altare.\nComplimenti, nessuno era mai arrivato fino a questo punto.\nRiuscirai a risolvere l'enigma finale che ti porterà alla vita eterna, oppure un giorno anche il tuo nome verrà dimenticato?");
        end.setSouth(eternityRoom);
        end.setNextNorth(castle);
        this.setCurrentRoom(castle);
    }

    public void setCurrentRoom(Room r) {
        this.currentRoom = r;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setPreviousRoom(Room r) {
        this.previousRoom = r;
    }

    public Room getPreviousRoom() {
        return this.previousRoom;
    }

    /**
     * Riempimento della mappa a partire da una mappa esistente
     * 
     * @param m
     */
    public void setMap(Map m) {
        this.setCastleEntrance(m.getCastleEntrance());
        this.setMoonRoom(m.getMoonRoom());
        this.setLaboratory(m.getLaboratory());
        this.setArmour(m.getArmour());
        this.setTreasureRoom(m.getTreasureRoom());
        this.setProphecyChamber(m.getProphecyChamber());
        this.setTimeRoom(m.getTimeRoom());
        this.setDarkCrypt(m.getDarkCrypt());
        this.setRsecrets(m.getRsecrets());
        this.setArmourRoom(m.getArmourRoom());
        this.setIllusionRoom(m.getIllusionRoom());
        this.setOblivionRoom(m.getOblivionRoom());
        this.setMisteryR(m.getMisteryR());
        this.setWlair(m.getWlair());
        this.setStorage(m.getStorage());
        this.setEternityroom(m.getEternityroom());
        this.setCurrentRoom(m.getCurrentRoom());
        this.setPreviousRoom(m.getPreviousRoom());
    }

    public Room getCastleEntrance() {
        return castle;
    }

    public void setCastleEntrance(Room castle) {
        this.castle = castle;
    }

    public Room getMoonRoom() {
        return moonRoom;
    }

    public void setMoonRoom(Room MoonRoom) {
        this.moonRoom = MoonRoom;
    }

    public Room getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Room laboratory) {
        this.laboratory = laboratory;
    }

    public Room getArmour() {
        return armour;
    }

    public void setArmour(Room Armour) {
        this.armour = Armour;
    }

    public Room getTreasureRoom() {
        return treasureRoom;
    }

    public void setTreasureRoom(Room treasureRoom) {
        this.treasureRoom = treasureRoom;
    }

    public Room getProphecyChamber() {
        return prophecyChamber;
    }

    public void setProphecyChamber(Room prophecyChamber) {
        this.prophecyChamber = prophecyChamber;
    }

    public Room getTimeRoom() {
        return timeRoom;
    }

    public void setTimeRoom(Room timeRoom) {
        this.timeRoom = timeRoom;
    }

    public Room getDarkCrypt() {
        return darkCrypt;
    }

    public void setDarkCrypt(Room darkCrypt) {
        this.darkCrypt = darkCrypt;
    }

    public Room getRsecrets() {
        return rSecrets;
    }

    public void setRsecrets(Room rsecrets) {
        this.rSecrets = rsecrets;
    }

    public Room getArmourRoom() {
        return armourRoom;
    }

    public void setArmourRoom(Room armourRoom) {
        this.armourRoom = armourRoom;
    }

    public Room getIllusionRoom() {
        return illusionRoom;
    }

    public void setIllusionRoom(Room illusionroom) {
        this.illusionRoom = illusionroom;
    }

    public Room getOblivionRoom() {
        return oblivionRoom;
    }

    public void setOblivionRoom(Room oblivionroom) {
        this.oblivionRoom = oblivionroom;
    }

    public Room getMisteryR() {
        return misteryRoom;
    }

    public void setMisteryR(Room misteryr) {
        this.misteryRoom = misteryr;
    }

    public Room getMemoryRoom() {
        return memoryRoom;
    }

    public void setMemoryRoom(Room memoryRoom) {
        this.memoryRoom = memoryRoom;
    }

    public Room getEternityroom() {
        return eternityRoom;
    }

    public void setEternityroom(Room eternityroom) {
        this.eternityRoom = eternityroom;
    }

    public Room getDestinyroom() {
        return end;
    }

    public void setDestinyroom(Room end) {
        this.end = end;
    }

    public Room getWlair() {
        return wyvernLair;
    }

    public void setWlair(Room wlair) {
        this.wyvernLair = wlair;
    }

    public Room getStorage() {
        return storage;
    }

    public void setStorage(Room storage) {
        this.storage = storage;
    }

    /**
     * Spostamento alla stanza precedente
     */
    public void back() {
        Room tmp = this.getCurrentRoom();
        this.setCurrentRoom(this.getPreviousRoom());
        this.setPreviousRoom(tmp);
    }

}
