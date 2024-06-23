/**
 * Classe utile alla definizione della mappa di gioco
 */
package gameCore;

import java.io.Serializable;

import base.Room;
import base.Stobj;
import objectSet.Door;
import objectSet.Key;
import objectSet.Potion;
import roomSet.AlchemicalLaboratory;
import roomSet.ArmourRoom;
import roomSet.CastleEntrance;
import roomSet.DarkCrypt;
import roomSet.DestinyRoom;
import roomSet.EternityRoom;
import roomSet.MisteryRoom;
import roomSet.MountFork;
import roomSet.MountStart;
import roomSet.ProphecyChamber;
import roomSet.RoomSecrets;
import roomSet.RoomWDoor;
import roomSet.ShamSecretRoom;
import roomSet.ShamanRoom;
import roomSet.TimeRoom;
import roomSet.TreasureRoom;
import roomSet.WeaponRoom;


public class Map implements Serializable{
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
    private Room illusionRoom= new Room();
    private Room oblivionRoom = new Room();
    private Room misteryRoom = new MisteryRoom();
    private Room mountBase = new Room();
    private Room mountStart = new MountStart();
    private Room fork = new MountFork();
    private Room wyvernLair=new Room();
    private Room storage=new RoomWDoor();
    private Room shamanRoom=new ShamanRoom();
    private Room closet=new ShamSecretRoom();
    private Room memoryRoom=new RoomWDoor();
    private Room eternityRoom=new EternityRoom();
    private Room end=new DestinyRoom();

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
        castle.setDescription("Ti trovi nell'entrata del Castello Enigmatico.\nLe pareti di pietra sono adornate da antichi stemmi e affreschi sbiaditi,\ntestimoni del passato glorioso del castello.");
        castle.setNextNorth(moonRoom);
      
        //Sala della Luna
        moonRoom.setName("Sala della luna");
        moonRoom.setDescription("Ti trovi nella stanza della luna, una stanza rotonda\ncon un soffitto a cupola che mostra le fasi lunari.\nUn lucernario al centro lascia entrare una luce argentata.\nAl centro della sala c'è un altare di pietra bianca con rune brillanti.\nLe pareti sono decorate con arazzi lunari e lampade\ndi cristallo emettono una luce soffusa.");
        door.setName("Porta");
        door.setDescription("E' la porta che conduce all' entrata  del castello. E' aperta");
        door.setOpen(true);
        door.setDirection("s");
        moonRoom.addObject(door);
        moonRoom.setNorth(rSecrets);
        moonRoom.setEast(armour);
        moonRoom.setSouth(castle);
        moonRoom.setNextSouth(castle);
        moonRoom.setWest(laboratory);
        
        //Laboratorio Alchemico
        laboratory.setName("Alchimista");
        laboratory.setDescription("Ti trovi nel laboratorio alchemico,\n accolto da un anziana alchimista con un sorriso gentile.\nTi offre un caloroso benvenuto e ti invita a esplorare le meraviglie del suo laboratorio.\nCon entusiasmo ti parla dei suoi prodotti alchemici unici,\noffrendoti la possibilità di acquistare pozioni curative,\nelisir di guarigione e altri straordinari oggetti magici.\nTi assicura che i suoi prodotti sono di altissima qualità e potrebbero essere esattamente ciò di cui hai bisogno per le tue avventure.");
        laboratory.setEast(moonRoom);
        
        //Armeria
        armour.setName("Armiere");
        armour.setDescription("Sei nella piccola Armeria. Ti accoglie  un uomo abbastanza muscolosovche ti chiede come può esserti utile.");
        armour.setWest(moonRoom);
        
        //Camera dei segreti
        rSecrets.setName("Camera dei segreti");
        rSecrets.setDescription("Ti avvicini alla camera dei segreti che si trova esettamente a nord dalla Sala della luna.\nSai che oltre questa stanza si troverà la cosidetta sala delle armature.\nLa porta è chiusa per aprirla dovrai parlare con il custode");
        rSecrets.setNextNorth(armourRoom);
        rSecrets.setSouth(moonRoom);

        //Sala delle armature
        armourRoom.setName("Sala delle armature");
        armourRoom.setDescription("La Sala delle Armature è una stanza maestosa con armature e armi antiche esposte su supporti di legno. ");
        armourRoom.setNorth(treasureRoom);
        armourRoom.setEast(illusionRoom);
        armourRoom.setSouth(rSecrets);
        armourRoom.setWest(oblivionRoom);

        //Stanza del tesoro
        treasureRoom.setName("Stanza del tesoro");
        treasureRoom.setDescription("Nei racconti del regno si narrava di un tempo lontano, nel quale la stanza del tesoro del castello brillava per le mille ricchezze. \nSembra che ora poco rimanga al suo interno. ");
        treasureRoom.setNextNorth(prophecyChamber);
        treasureRoom.setSouth(armourRoom);

        //Camera delle profezie
        prophecyChamber.setName("Camera delle profezie");
        prophecyChamber.setDescription("La camera delle profezie è riempita da macchinari ed aggeggi che sembrano essere usati per lo studio dello spazio e delle magie.\nI motivi a tema astronomico e cosmologico fanno si che il tuo sguardo si perda nel vuoto.");
        prophecyChamber.setNorth(timeRoom);
        prophecyChamber.setSouth(treasureRoom);

        //Sala del tempo
        timeRoom.setName("Sala del tempo");
        timeRoom.setDescription("Sei ora nella sala del tempo del castello. Si dice che qui il tempo scorra più lentamente.\nIl passato, il presente ed il futuro sembrano mescolarsi.");
        timeRoom.setWest(darkCrypt);
        timeRoom.setNextEast(memoryRoom);
        timeRoom.setSouth(prophecyChamber);

        //Cripta Oscura
        darkCrypt.setName("Cripta Oscura");
        darkCrypt.setDescription("Un grezzo buco nel muro ti ha portato in una camera buia e dall'atmosfera inquietante.\nL'aria è capace di far salire brividi gelidi lungo la tua spina dorsale.\nIl tetto basso della stanza rende il tutto opprimente.\nSenti dozzine di presenze toccare le tue spalle.");
        darkCrypt.setEast(timeRoom);    

        // sala dei ricordi
        memoryRoom.setName("Sala dei ricordi");
        memoryRoom.setDescription("Sei in una stanza che sembra essere dove lo sciamano conserva le sue cianfrusaglie.");
        Stobj chiave = new Key();
        chiave.setName("Chiave");
        chiave.setDescription("Guarda c'è una chiave , potrebbe servirti a qualcosa potrei consigliarti di prenderla poi fai come vuoi ! non dire che non ti avevo avvisato!!");
        chiave.setPickupable(true);
        chiave.setUsable(true);
        memoryRoom.addObject(chiave);
        door = new Door();
        door.setName("Porta");
        door.setDescription("Guarda una porta non serve a nulla . E' chiusa");
        door.setOpen(false);
        door.setDirection("n");
        memoryRoom.addObject(door);
        door = new Door();
        door.setName("Porta");
        door.setDescription("Guarda una porta non serve a nulla . E' chiusa");
        door.setOpen(true);
        door.setDirection("w");
        memoryRoom.addObject(door);
        memoryRoom.setNextNorth(eternityRoom);
        memoryRoom.setWest(timeRoom);
        memoryRoom.setNextWest(timeRoom);

        //Sala delle illusioni 
        obj.setName("bacca");
        obj.setDescription("Una bacca! E' piccola ma sembra molto nutriente.\nPuò essere mangiata. Mangiare qualcosa trovata in una stanza non è proprio il massimo, non credi ?");
        obj.setPickupable(true);
        obj.setUsable(true);
        illusionRoom.setName("Sala delle illusioni");
        illusionRoom.setDescription("Ti trovi nella Sala delle Illusioni, ma presto scopri che non puoi interagire con nessuno. È un caso, o c'è qualcosa di più misterioso dietro questa apparente casualità?");
        illusionRoom.setMoney(1);
        illusionRoom.addObject(obj);
        illusionRoom.setWest(armourRoom);
        
        //Camera dell'oblio
        oblivionRoom.setName("Camera dell'oblio ");
        oblivionRoom.setDescription("Sai che proseguendo in questa direzione raggiungerai la sala dei misteri! \nVoci dicono che sia una stanza ababstanza misteriosa .Forse  potrebbe aiutarti...?");
        oblivionRoom.setEast(armourRoom);
        oblivionRoom.setWest(misteryRoom);
        
        //Sala dei misteri
        misteryRoom.setName("Sala dei misteri");
        misteryRoom.setDescription("Sei nella sala dei misteri la prima cosa che vedi è un vigilante, che cerca in tutti i modi di parlare con te!!! Forse sarebbe il caso di rivolgergli la parola…");
        misteryRoom.setEast(oblivionRoom);
        
        //Piedi della montagna (fine foresta)
        mountBase.setName("Base della montagna");
        mountBase.setDescription("Vedi la foresta diradarsi per lasciare spazio ad un enorme montagna.");
        mountBase.setNorth(mountStart);
        mountBase.setSouth(armourRoom);
        
        //Davanti alla porta segreta sulla montagna
        mountStart.setName("Sentiero sul fianco della montagna");
        mountStart.setDescription("Proseguendo, ti ritrovi su un'area rocciosa poco più grande di un pianerottolo di una casa."
                                   + "\nIl sentiero è bloccato da una frana e davanti a te vedi due strane leve sospette.");
        mountStart.setNextNorth(fork);
        mountStart.setSouth(mountBase);
        
        //Bivio all'interno della montagna
        fork.setName("Entrata nella montagna");
        fork.setDescription("Sei all'entrata di una strana caverna nella montagna.\nCapisci che è stata abitata ma adesso sembra abbandonata. La strada prosegue verso est.\nAd ovest c'è solo un muro...");
        fork.setNextWest(wyvernLair);
        fork.setNextEast(storage);
        fork.setSouth(mountStart);
        
        //Magazzino est dopo il bivio
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
        storage.setNextNorth(shamanRoom);
        storage.setWest(fork);
        storage.setNextWest(fork);
        
        //Stanza sciamano
        shamanRoom.setName("Stanza dello Sciamano");
        shamanRoom.setDescription("Sei nella stanza in cui viveva lo sciamano in completo isolamento.\nSulla  parete sinistra, c'è un'immensa libreria, piena di libri e di appunti.\n" +
                                    "La parete davanti a te è sgombra, ma noti un piccolo incavo al centro di essa.\n" +
                                    "La parete destra invece è occupata da un'enorme scrivania alla quale è sedutavuna figura incappucciata che ti porge le spalle.");
        shamanRoom.setNextNorth(closet);
        shamanRoom.setSouth(storage);
        shamanRoom.setNextSouth(storage);
        
        //Stanzino nascosto dello sciamano
        closet.setName("Stanzino nascosto");
        closet.setDescription("Sei nello stanzino segreto dello sciamano.\nChissà quali cose losche avvenivano qui dentro...\n" +
                                "All'interno ci sono almeno una quindicina di mensole che partono dal basso, con ogni tipo di pozione meticolosamente conservate.\n"
                                + "Leggi etichette che vanno da \"amore\" a \"veleno\", ma il tuo interesse cade su una in particolare:\n" +
                                "\"Pozione millecure\", è proprio quel che cercavi!");
        closet.setSouth(shamanRoom);
        closet.setNextNorth(castle);

        this.setCurrentRoom(castle);



               //stanza dell' Eternità
               eternityRoom.setName("Stanza dell'eternità");
               eternityRoom.setDescription("Sei nella Stanza dell'Eternità Lungo la parete sinistra si estende un'enorme muro. Al centro della parete di fronte a te, noti un piccolo incavo, forse una serratura. La parete opposta è dominata da una massiccia scrivania, dove una figura incappucciata è seduta, dando le spalle al centro della stanza. Ricordi di aver trovato una chiave nella stanza precedente. Potrebbe essere cruciale qui.");
               eternityRoom.setNextNorth(end);
               eternityRoom.setSouth(memoryRoom);
               eternityRoom.setNextSouth(memoryRoom);
               
               //sala del destino 
               end.setName("Sala del destino");
               end.setDescription("Sei nella Stanza del Destino.\nLe pareti sono rivestite di antichi arazzi che raffigurano il ciclo della vita.\n" +
               "Chissà quali segreti celano queste mura...\n" +
               "Al centro della stanza, un altare d'oro brillante risplende sotto una luce misteriosa.\n" +
               "I tuoi occhi cadono su un antica pergamena posata sull'altare, apparentemente l'oggetto del tuo viaggio.\n" +
               "Complimenti, nessuno era mai arrivato fino a questo punto.\nRiuscirai a risolvere l'enigma finale che ti porterà alla vita eterna, oppure sarai dimenticato nel passato?");
               end.setSouth(eternityRoom);
               end.setNextNorth(castle);
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
       this.setTreasureRoom(m.getTreasureRoom());
       this.setProphecyChamber(m.getProphecyChamber());
       this.setTimeRoom(m.getTimeRoom());
       this.setDarkCrypt(m.getDarkCrypt());
       this.setRsecrets(m.getRsecrets());
       this.setArmourRoom(m.getArmourRoom());
       this.setIllusionRoom(m.getIllusionRoom());
       this.setOblivionRoom(m.getOblivionRoom());
       this.setMisteryR(m.getMisteryR());
       this.setFork(m.getFork());
       this.setWlair(m.getWlair());
       this.setStorage(m.getStorage());
       this.setShamanroom(m.getShamanroom());
       this.setCloset(m.getCloset());
       this.setEternityroom(m.getEternityroom());
       this.setDestinyroom(m.getCloset());
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

    public void setLaboratory(Room laboratory ) {
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

    public Room getFork() {
        return fork;
    }

    public void setFork(Room fork) {
        this.fork = fork;
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

    public Room getShamanroom() {
        return shamanRoom;
    }

    public void setShamanroom(Room shamanroom) {
        this.shamanRoom = shamanroom;
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
