/**
 * Classe utile alla definizione della mappa di gioco
 */
package gameCore;

import java.io.Serializable;

import base.Room;
import base.Stobj;
import monsterSet.Monster_Gatto;
import monsterSet.Monster_Viverna;
import objectSet.Door;
import objectSet.Potion;
import roomSet.AlchemicalLaboratory;
import roomSet.CastleEntrance;
import roomSet.MisteryRoom;
import roomSet.MountFork;
import roomSet.MountStart;
import roomSet.RoomSecrets;
import roomSet.RoomWDoor;
import roomSet.SalaArmi;
import roomSet.ShamSecretRoom;
import roomSet.ShamanRoom;
import roomSet.WeaponRoom;


public class Map implements Serializable{
    private Room castle = new CastleEntrance();
    private Room MoonRoom = new RoomWDoor();
    private Room laboratory = new AlchemicalLaboratory();
    private Room Armour = new WeaponRoom();
    private Room rsecrets = new RoomSecrets();
    private Room salaarmi = new SalaArmi();
    private Room illusionroom= new Room();
    private Room oblivionroom = new Room();
    private Room misteryr = new MisteryRoom();
    private Room mountbase = new Room();
    private Room mountstart = new MountStart();
    private Room fork = new MountFork();
    private Room wlair=new Room();
    private Room storage=new RoomWDoor();
    private Room shamanroom=new ShamanRoom();
    private Room closet=new ShamSecretRoom();

    private Room currentRoom = new Room();
    private Room previousRoom = null;
     
    /**
     * Costruzione della mappa di gioco
     */
    public Map(){
        Stobj obj = new Stobj();
        Door door = new Door();
        //Creazione delle stanze e assegnazione degli oggetti interagibili
        
        // Entrata del castello 
        castle.setName("Entrata del castello ");
        castle.setDescription("Ti trovi nell'entrata del Castello Enigmatico. Le pareti di pietra sono adornate da antichi \nstemmi e affreschi sbiaditi, testimoni del passato glorioso del castello.");
        castle.setNextNorth(MoonRoom);
      
        //Sala della Luna
        MoonRoom.setName("Sala della luna");
        MoonRoom.setDescription("Ti trovi nella stanza della luna, una stanza rotonda con un soffitto a cupola che mostra le \n fasi lunari. Un lucernario al centro lascia entrare una luce argentata. Al centro della sala \n c'è un altare di pietra bianca con rune brillanti. Le pareti sono decorate con arazzi lunari e lampade di cristallo emettono una luce soffusa.\n");
        door.setName("Porta");
        door.setDescription("E' la porta che conduce all' entrata  del castello . E' aperta");
        door.setOpen(true);
        door.setDirection("s");
        MoonRoom.addObject(door);
        MoonRoom.setNorth(rsecrets);
        MoonRoom.setEast(Armour);
        MoonRoom.setSouth(castle);
        MoonRoom.setNextSouth(castle);
        MoonRoom.setWest(laboratory);
        
        //Laboratorio Alchemico
        laboratory.setName("Alchimista");
        laboratory.setDescription("Ti trovi nel laboratorio alchemico, accolto da un anziana alchimista con un sorriso gentile. Ti offre un caloroso benvenuto e ti invita a esplorare le meraviglie del suo laboratorio. Con entusiasmo ti parla dei suoi prodotti alchemici unici, offrendoti la possibilità di acquistare pozioni curative, elisir di guarigione e altri straordinari oggetti magici. Ti assicura che i suoi prodotti sono di altissima qualità e potrebbero essere esattamente ciò di cui hai bisogno per le tue avventure.");
        laboratory.setEast(MoonRoom);
        
        //Armeria
        Armour.setName("Armiere");
        Armour.setDescription("Sei nella piccola Armeria. Ti accoglie  un uomo abbastanza muscoloso\nche ti chiede come può esserti utile.");

        Armour.setWest(MoonRoom);
        
        //Camera dei segreti
        rsecrets.setName("Camera dei segreti");
        rsecrets.setDescription("Ti avvicini alla camera dei segreti che si trova esettamente a nord dalla Sala della luna .\nSai che oltre questa stanza si troverà la cosidetta sala delle armature.\nLa porta è chiusa per aprirla dovrai parlare con il custode");

        rsecrets.setNextNorth(salaarmi);
        rsecrets.setSouth(MoonRoom);

        //Sala delle armature
        salaarmi.setName("Sala delle armature ");
        salaarmi.setDescription("La Sala delle Armature è una stanza maestosa con armature e armi antiche esposte su supporti di legno. ");
        salaarmi.setNorth(mountbase);
        salaarmi.setEast(illusionroom);
        salaarmi.setSouth(rsecrets);
        salaarmi.setWest(oblivionroom);

        //sala delle illusioni 
        obj.setName("bacca");
        obj.setDescription("Una bacca! E' piccola ma sembra molto nutriente.\nPuò essere mangiata. Mangiare qualcosa trovata in una stanza non è proprio il massimo, non credi ?");
        obj.setPickupable(true);
        obj.setUsable(true);
        illusionroom.setName("Sala delle illusioni");
        illusionroom.setDescription("Ti trovi nella Sala delle Illusioni, ma presto scopri che non puoi interagire con nessuno. È un caso, o c'è qualcosa di più misterioso dietro questa apparente casualità?");
        illusionroom.setMoney(1);
        illusionroom.addObject(obj);
        illusionroom.setWest(salaarmi);
        
        //camera dell'oblio
        oblivionroom.setName("Camera dell'oblio ");
        oblivionroom.setDescription("Sai che proseguendo in questa direzione raggiungerai la sala dei misteri! \nVoci dicono che sia una stanza ababstanza misteriosa .Forse  potrebbe aiutarti...?");
        oblivionroom.setMonster(new Monster_Gatto());
        oblivionroom.setEast(salaarmi);
        oblivionroom.setWest(misteryr);
        
        //sala dei misteri
        misteryr.setName("Sala dei misteri");
        misteryr.setDescription("Sei nella sala dei misteri la prima cosa che vedi è un vigilante, che cerca in tutti i modi di parlare con te!!! Forse sarebbe il caso di rivolgergli la parola…");
        misteryr.setEast(oblivionroom);
        
        //Piedi della montagna (fine foresta)
        mountbase.setName("Base della montagna");
        mountbase.setDescription("Vedi la foresta diradarsi per lasciare spazio ad un enorme montagna.");
        mountbase.setNorth(mountstart);
        mountbase.setSouth(salaarmi);
        
        //davanti alla porta segreta sulla montagna
        mountstart.setName("Sentiero sul fianco della montagna");
        mountstart.setDescription("Proseguendo, ti ritrovi su un'area rocciosa poco più grande di un pianerottolo di una casa."
                                   + "\nIl sentiero è bloccato da una frana e davanti a te vedi due strane leve sospette.");
        mountstart.setNextNorth(fork);
        mountstart.setSouth(mountbase);
        
        //bivio all'interno della montagna
        fork.setName("Entrata nella montagna");
        fork.setDescription("Sei all'entrata di una strana caverna nella montagna.\nCapisci che è stata abitata ma adesso sembra abbandonata. La strada prosegue verso est.\nAd ovest c'è solo un muro...");
        fork.setNextWest(wlair);
        fork.setNextEast(storage);
        fork.setSouth(mountstart);
        
        //tana della viverna
        wlair.setName("Tana della viverna");
        wlair.setDescription("Sei nella tana dove la viverna era in letargo. Una caverna spaziosa e ben riscaldata!");
        wlair.setMonster(new Monster_Viverna());
        wlair.setEast(fork);
        
        //magazzino est dopo il bivio
        storage.setName("Magazzino");
        storage.setDescription("Sei in una stanza che sembra essere dove lo sciamano conserva le sue cianfrusaglie.");
        Stobj potion = new Potion();
        potion.setName("Pozione");
        potion.setAlias(new String[]{"cura"});
        potion.setDescription("Pozione curativa. Apparteneva allo sciamano. Sei un ladro...");
        potion.setPickupable(true);
        potion.setUsable(true);
        storage.addObject(potion);
        storage.addObject(potion);
        storage.setMoney(21);
        door = new Door();
        door.setName("Porta");
        door.setDescription("Una semplice porta di legno. E' chiusa");
        door.setOpen(false);
        door.setDirection("n");
        storage.addObject(door);
        door = new Door();
        door.setName("Porta");
        door.setDescription("Una semplice porta di legno. E' aperta");
        door.setOpen(true);
        door.setDirection("w");
        storage.addObject(door);
        storage.setNextNorth(shamanroom);
        storage.setWest(fork);
        storage.setNextWest(fork);
        
        //stanza sciamano
        shamanroom.setName("Stanza dello Sciamano");
        shamanroom.setDescription("Sei nella stanza in cui viveva lo sciamano in completo isolamento.\nSulla  parete sinistra, c'è un'immensa libreria, piena di libri e di appunti.\n" +
                                    "La parete davanti a te è sgombra, ma noti un piccolo incavo al centro di essa.\n" +
                                    "La parete destra invece è occupata da un'enorme scrivania alla quale è seduta\nuna figura incappucciata che ti porge le spalle.");
        shamanroom.setNextNorth(closet);
        shamanroom.setSouth(storage);
        shamanroom.setNextSouth(storage);
        
        //stanzino nascosto dello sciamano
        closet.setName("Stanzino nascosto");
        closet.setDescription("Sei nello stanzino segreto dello sciamano.\nChissà quali cose losche avvenivano qui dentro...\n" +
                                "All'interno ci sono almeno una quindicina di mensole che partono dal basso,\ncon ogni tipo di pozione meticolosamente conservate.\n"
                                + "Leggi etichette che vanno da \"amore\" a \"veleno\",\nma il tuo interesse cade su una in particolare:\n" +
                                "\"Pozione millecure\", è proprio quel che cercavi!");
        closet.setSouth(shamanroom);
        closet.setNextNorth(castle);

        this.setCurrentRoom(castle);
    }

    public void setCurrentRoom(Room r){
        this.currentRoom = r;
    }

    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    public void setPreviousRoom(Room r){
        this.previousRoom = r;
    }

    public Room getPreviousRoom(){
        return this.previousRoom;
    }
    
    /**
     * Riempimento della mappa a partire da una mappa esistente
     * @param m 
     */
    public void setMap(Map m){
       this.setCastleEntrance(m.getCastleEntrance());
       this.setMoonRoom(m.getMoonRoom());
       this.setLaboratory(m.getLaboratory());
       this.setArmour(m.getArmour());
       this.setRsecrets(m.getRsecrets());
       this.setSalaarmi(m.getSalaarmi());
       this.setIllusionRoom(m.getIllusionRoom());
       this.setOblivionRoom(m.getOblivionRoom());
       this.setMisteryR(m.getMisteryR());
       this.setMountbase(m.getMountbase());
       this.setMountstart(m.getMountstart());
       this.setFork(m.getFork());
       this.setWlair(m.getWlair());
       this.setStorage(m.getStorage());
       this.setShamanroom(m.getShamanroom());
       this.setCloset(m.getCloset());
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
        return MoonRoom;
    }

    public void setMoonRoom(Room MoonRoom) {
        this.MoonRoom = MoonRoom;
    }

    public Room getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Room laboratory ) {
        this.laboratory = laboratory;
    }

    public Room getArmour() {
        return Armour;
    }

    public void setArmour(Room Armour) {
        this.Armour = Armour;
    }

    public Room getRsecrets() {
        return rsecrets;
    }

    public void setRsecrets(Room rsecrets) {
        this.rsecrets = rsecrets;
    }

    public Room getSalaarmi() {
        return salaarmi;
    }

    public void setSalaarmi(Room salaarmi) {
        this.salaarmi = salaarmi;
    }

    public Room getIllusionRoom() {
        return illusionroom;
    }

    public void setIllusionRoom(Room illusionroom) {
        this.illusionroom = illusionroom;
    }

    public Room getOblivionRoom() {
        return oblivionroom;
    }

    public void setOblivionRoom(Room oblivionroom) {
        this.oblivionroom = oblivionroom;
    }

    public Room getMisteryR() {
        return misteryr;
    }

    public void setMisteryR(Room misteryr) {
        this.misteryr = misteryr;
    }

    public Room getMountbase() {
        return mountbase;
    }

    public void setMountbase(Room forestend) {
        this.mountbase = forestend;
    }

    public Room getMountstart() {
        return mountstart;
    }

    public void setMountstart(Room mountstart) {
        this.mountstart = mountstart;
    }

    public Room getFork() {
        return fork;
    }

    public void setFork(Room fork) {
        this.fork = fork;
    }

    public Room getWlair() {
        return wlair;
    }

    public void setWlair(Room wlair) {
        this.wlair = wlair;
    }

    public Room getStorage() {
        return storage;
    }

    public void setStorage(Room storage) {
        this.storage = storage;
    }

    public Room getShamanroom() {
        return shamanroom;
    }

    public void setShamanroom(Room shamanroom) {
        this.shamanroom = shamanroom;
    }

    public Room getCloset() {
        return closet;
    }

    public void setCloset(Room closet) {
        this.closet = closet;
    }

    /**
     * Spostamento alla stanza precedente
     */
    public void back(){
        Room tmp = this.getCurrentRoom();
        this.setCurrentRoom(this.getPreviousRoom());
        this.setPreviousRoom(tmp);
    }
    
}
