package domini;

import persistencia.CtrlPersistencia;
import vista.CtrlVista;

public class CtrlDomini {

    private RegistreJugadors registreJugadors;

    private String idJugadorConvidat;
    private String idIAgenetic;

    private Jugador codebreaker;
    private Jugador codemaker;

    private Opcions opcions_actuals;

    private CtrlVista ctrlVista;
    private CtrlPersistencia ctrlPersistencia;

    private RegistreTaulells registreTaulells;

    public CtrlDomini() throws Exception {
        ctrlVista = new CtrlVista();
        ctrlPersistencia = new CtrlPersistencia();
        if(ctrlPersistencia.existsSavedRegistreTaulells()){
            registreTaulells = ctrlPersistencia.carregarRegistreTaulells();
        }
        else registreTaulells = new RegistreTaulells();

        if(ctrlPersistencia.existsSavedRegistreJugadors()){
            registreJugadors = ctrlPersistencia.carregarRegistreJugadors();
        }
        else registreJugadors = new RegistreJugadors();

        idIAgenetic = "IAGenetic";
        registreJugadors.afegirJugador(idIAgenetic, false);

        idJugadorConvidat =  "GuestPlayer";
        registreJugadors.afegirJugador(idJugadorConvidat, true);
    }

    void run() {
        ctrlVista.assignarCtrlDomini(this);
        ctrlVista.mostrarVistaPantallaBenvinguda();
    }

    public void guardarTaulells(){
        ctrlPersistencia.guardarRegistreTaulells(registreTaulells);
    }

    public void carregarTaulells(){
        if(ctrlPersistencia.existsSavedRegistreTaulells()) registreTaulells=ctrlPersistencia.carregarRegistreTaulells();
    }
    public RegistreTaulells getTaulells(){
        return registreTaulells;
    }
    public void guardarJugadors(){
        ctrlPersistencia.guardarRegistreJugadors(registreJugadors);
    }

    public void carregarJugadors(){
        if(ctrlPersistencia.existsSavedRegistreJugadors())  registreJugadors=ctrlPersistencia.carregarRegistreJugadors();
    }

    public void afegirJugador(String nomPublic, boolean esHuma) throws Exception{
        registreJugadors.afegirJugador(nomPublic,  esHuma);
    }


    /**
     * Donat l'username d'un jugador retorna el seu id
     * @param username Nom d'usuari deñ jugador
     * @return El id del jugador
     */
    public String obtenirIdDeUsername(String username) {
        return registreJugadors.obtenirIdDeUsername(username).get();
    }

    public Jugador obtenirJugador(String idJugador) throws Exception {
        return registreJugadors.obtenirJugador(idJugador);
    }

    public String getInfoJugador(String idJugador) throws Exception {
        return registreJugadors.getInfoJugador(idJugador);
    }

    public void eliminarJugadorRegistrat(String id) throws Exception {
        registreJugadors.eliminarJugador(id);
    }

    public boolean afegirJugadorRegistrat(String nomPublic, String nomUsuari, String passwordUsuari) throws Exception {
        return registreJugadors.afegirJugadorRegistrat( nomPublic, nomUsuari, passwordUsuari);
    }

    //retorna cert si existeix un jugador registrat amb tal username i password
    public boolean iniciarSessio(String username, String password) throws Exception {
        return registreJugadors.iniciarSessio(username, password);
    }

    //ranking-start
    public String[][] ordenarPerNomPublicGUI() { return registreJugadors.ordenarPerNomPublicGUI(); }
    public String[][] ordenarPerPuntuacioGUI() { return registreJugadors.ordenarPerPuntuacioGUI(); }
    public String[][] ordenarPerPartidesJugadesGUI() { return registreJugadors.ordenarPerPartidesJugadesGUI(); }
    public String[][] ordenarPerPartidesGuanyadesGUI() { return registreJugadors.ordenarPerPartidesGuanyadesGUI(); }
    public String[][] ordenarPerPartidesPerdudesGUI() { return registreJugadors.ordenarPerPartidesPerdudesGUI(); }
    public String[][] ordenarPerPercentatgePartidesGuanyadesGUI() { return registreJugadors.ordenarPerPercentatgePartidesGuanyadesGUI(); }
    public String[][] ordenarPerPercentatgePartidesPerdudesGUI() { return registreJugadors.ordenarPerPercentatgePartidesPerdudesGUI(); }
    public String[][] ordenarPerPuntuacioMitjanaGUI() { return registreJugadors.ordenarPerPuntuacioMitjanaGUI(); }
    public String[][] ordenarPerMaximaRatxaVictoriesGUI() { return registreJugadors.ordenarPerMaximaRatxaVictoriesGUI(); }
    public String[][] ordenarPerMaximaRatxaDerrotesGUI() { return registreJugadors.ordenarPerMaximaRatxaDerrotesGUI(); }
    //ranking-end

    //records-start
    public String[] recordPuntuacio() { return registreJugadors.recordPuntuacio(); }
    public String[] recordPartidesJugades() { return registreJugadors.recordPartidesJugades(); }
    public String[] recordPartidesGuanyades() { return registreJugadors.recordPartidesGuanyades(); }
    public String[] recordPartidesPerdudes() { return registreJugadors.recordPartidesPerdudes(); }
    public String[] recordPercentatgePartidesGuanyades() { return registreJugadors.recordPercentatgePartidesGuanyades(); }
    public String[] recordPercentatgePartidesPerdudes() { return registreJugadors.recordPercentatgePartidesPerdudes(); }
    public String[] recordPuntuacioMitjana() { return registreJugadors.recordPuntuacioMitjana(); }
    public String[] recordMaximaRatxaVictories() { return registreJugadors.recordMaximaRatxaVictories(); }
    public String[] recordMaximaRatxaDerrotes() { return registreJugadors.recordMaximaRatxaDerrotes(); }
    //record-end

    public void setOpcions(Opcions o) {
        opcions_actuals = o;
    }

    public void setCodebreakerIA() throws Exception {
        codebreaker = registreJugadors.obtenirJugador(idIAgenetic);
    }

    public void setCodemakerIA() throws Exception {
        codemaker = registreJugadors.obtenirJugador(idIAgenetic);
    }

    public void setCodemakerConvidat() throws Exception {
        codemaker = registreJugadors.obtenirJugador(idJugadorConvidat);
    }

    public void setCodebreakerConvidat() throws Exception {
        codebreaker = registreJugadors.obtenirJugador(idJugadorConvidat);
    }

    public void setCodebreaker(Jugador j) { codebreaker = j; }

    public void setCodemaker(Jugador j) { codemaker = j; }

    public void jugarPartida(ModelTaulell model) {
        // public Partida(ModelTaulell model, Opcions config, Jugador codemaker, Jugador codebreaker);
        ctrlVista.jugarPartida(new Partida( model,  this.opcions_actuals,  this.codemaker, this.codebreaker));
    }
    //records-end


    public void guanyaPartidaJugador(String idJugador, int puntuacio) throws Exception {
        registreJugadors.guanyaPartidaJugador(idJugador, puntuacio);
    }

    public void acabarPartida() {
        registreJugadors.actualitzarJugador(codemaker);
        registreJugadors.actualitzarJugador(codebreaker);
        guardarJugadors();
    }

    //part comuna
    public static void main(String[] args) throws Exception {
        CtrlDomini ctrlDomini = new CtrlDomini();
        ctrlDomini.run();
    }

    public RegistreTaulells getRegistreTaulells() {
        return registreTaulells;
    }
}
