package domini;

import java.io.Serializable;

/**
 * Aquesta classe representa un jugador del Mastermind: s'instancia per a crear
 * un Jugador convidat.
 */
public class Jugador implements Serializable {
    //PROTECTED i PRIVAT:
    //Atributs


    /**
     * Nom del jugador que es mostra en el Ranking o les estadístiques(és únic)
     * també serveix de identificador
    */
    protected String nomPublic;

    /**
     * Cert si es huma o fals si es una IA
     */
    protected boolean esHuma;

    /**
     * La puntuació total del jugador
    */
    protected int puntuacio;

    /**
     * El nombre de partides que el jugador ha jugat
    */
    protected int nPartidesJugades;

    /**
     * El nombre de partides que el jugador ha guanyat amb el rol de codebraker, és a dir,
     * si el Jugador ha aconseguit trencar el codi
    */
    protected int nPartidesGuanyades;

    /**
     * El nombre de partides que el jugador ha perdut amb el rol de codebraker
    */
    protected int nPartidesPerdudes;

    /**
     * El percentatge de partides guanyades pel jugador
    */
    protected double percentatgePartidesGuanyades;

    /**
     * El percentatge de partides perdudes pel jugador
    */
    protected double percentatgePartidesPerdudes;

    //realment és millor Jugador aquell que té més puntuació amb menys partides jugades
    /**
     * El nombre de punts que té el jugador dividit entre el nombre de partides jugades
     */
    protected double puntuacioMitjana;

    //fa referència al rol de codebraker, ja que es considera victoria quan un jugador
    //aconsegueix esbrinar(trencar) el codi quan fa de codebraker
    protected boolean anteriorPartidaGuanyada;
    protected int actualRatxaVictories;
    protected int actualRatxaDerrotes;
    protected int maximaRatxaVictories;
    protected int maximaRatxaDerrotes;

    //Métodes

    //Per evitar redundància i facilitar futures expansions
    //Aquest métode NO POT ser protected ni public(NO s'ha de poder sobreescriure)
    //ja que es crida dins d'un constructor i en un constructor NO es pot invocar métodes
    //que es puguin sobreescriure. EL constructor de la superclasse s'executa
    // abans que el de la subclasse, per tant, el métode sobreescrit a la subclasse
    //s'erà invocat abans que el constructor de la subclasse s'hagi executat.
    /**
     * Inicialitza els atributs del Jugador
     */
    private void inicialitzar() {
        puntuacio = 0;
        nPartidesJugades = 0;
        nPartidesGuanyades = 0;
        nPartidesPerdudes = 0;
        percentatgePartidesGuanyades = 0.0;
        percentatgePartidesPerdudes = 0.0;
        puntuacioMitjana = 0.0;
        anteriorPartidaGuanyada = false;
        actualRatxaVictories = 0;
        actualRatxaDerrotes = 0;
        maximaRatxaVictories = 0;
        maximaRatxaDerrotes = 0;
    }

    /**
     * Incrementa la puntuació del jugador en 'increment'
     * @param increment En quant s'incrementa la puntuació del jugador
     */
    private void incrementarPuntuacio(int increment) {
        puntuacio = puntuacio + increment;
    }

    //per evitar redundància i facilitar futures expansions
    /**
     * Actualitza certes estadístiques derivades del Jugador: el percentatge de partides guanyades,
     * el percentatge de partides perdudes i la relacio puntuacio / partides de jugades
     */
    protected void actualitzaInfoInterna() {
        actualitzaPercentatgePartidesGuanyades();
        actualitzaPercentatgePartidesPerdudes();
        actualitzaPuntuacioMitjana();
    }

    /**
     * Actualitza el percentatge de partides guanyades pel jugador
     */
    protected void actualitzaPercentatgePartidesGuanyades() {
        percentatgePartidesGuanyades = ((double)nPartidesGuanyades / (double)nPartidesJugades)*100;
    }

    /**
     * Actualitza el percentatge de partides perdudes pel jugador
     */
    protected void actualitzaPercentatgePartidesPerdudes() {
        percentatgePartidesPerdudes = ((double)nPartidesPerdudes / (double)nPartidesJugades)*100;
    }

    /**
     * Actualitza la relació entre puntuació del jugador i el nombre de partides jugades d'aquest
     */
    protected void actualitzaPuntuacioMitjana() {
        puntuacioMitjana = ((double)puntuacio / (double)nPartidesJugades);
    }

    //constructors
    /**
     * Crea un jugador amb identificador, esHuma i  nomPublic inicialitzats
     * @param nomPublic El nom del jugador que es mostrarà al Ranking i estadístiques
     * @param esHuma Cert si el jugador es Huma, fals si es una IA
     *
     */
    public Jugador(String nomPublic, boolean esHuma) {
        this.nomPublic = nomPublic;
        this.esHuma = esHuma;

        inicialitzar();

    }

    //Métodes
    //Consultors
    /**
     * Retorna el nom públic del jugador
     * @return EL nom públic del jugador
     */
    public String getNomPublic() {
        return nomPublic;
    }

    public boolean getEsHuma() {
        return esHuma;
    }

    /**
     * Obté la puntuació del jugador
     * @return la puntuació del Jugador
     */
    public int getPuntuacio() {
        return puntuacio;
    }

    /**
     * Obté el nombre de partides jugades pel jugador
     * @return el nombre de partides jugades pel jugador
     */
    public int getNumeroPartidesJugades() {
        return nPartidesJugades;
    }

    /**
     * Obté el nombre de partides guanyades pel jugador
     * @return el nombre de partides guanyades pel jugador
     */
    public int getNumeroPartidesGuanyades() {
        return nPartidesGuanyades;
    }

    /**
     * Obté el nombre de partides perdudes pel jugador
     * @return el nombre de partides perdudes pel jugador
     */
    public int getNumeroPartidesPerdudes() {
        return nPartidesPerdudes;
    }

    /**
     * Obté el percentatge de partides guanyades pel jugador
     * @return el percentatge de partides guanyades pel jugador
     */
    public double getPercentatgePartidesGuanyades() {
        return percentatgePartidesGuanyades;
    }

    /**
     * Obté el percentatge de partides perdudes pel jugador
     * @return el percentatge de partides perdudes pel jugador
     */
    public double getPercentatgePartidesPerdudes() {
        return percentatgePartidesPerdudes;
    }

    /**
     * Obté el nombre de punts que té el jugador dividit entre el nombre de partides jugades
     * @return el nombre de punts que té el jugador dividit entre el nombre de partides jugades
     */
    public double getPuntuacioMitjana() {
        return puntuacioMitjana;
    }

    public int getMaximaRatxaVictories() {
        return maximaRatxaVictories;
    }

    public int getMaximaRatxaDerrotes() {
        return maximaRatxaDerrotes;
    }

    //Modificadors
    /**
     * Indica que el jugador guanya una partida(adivina el codi fent de codebraker) i actualitza les seves variables internes
     * @param increment En quant s'ha d'incrementar la puntuació del jugador
     */
    public void guanyaPartida(int increment) {
        //important ordre; sinó al cridar actualitzaInfoInterna()podriem estar
        //dividint per 0
        //Així mai dividirà per 0 ja que sempre nPartidesjugades >= 1
        nPartidesJugades += 1;
        nPartidesGuanyades += 1;
        //important cridar primer a 'incrementarPuntuacio(increment)'
        incrementarPuntuacio(increment);
        actualitzaInfoInterna();

        if (anteriorPartidaGuanyada) {
            ++actualRatxaVictories;
            if (actualRatxaVictories > maximaRatxaVictories)
                maximaRatxaVictories = actualRatxaVictories;

        }
        else {
            anteriorPartidaGuanyada = true;
            actualRatxaVictories = 1;
            if (actualRatxaVictories > maximaRatxaVictories)
                maximaRatxaVictories = actualRatxaVictories;
        }

        actualRatxaDerrotes = 0;
    }

    /**
     * Indica que el jugador perd una partida i actualitza les seves variables internes
     */
    public void perdPartida() {
        //important ordre; sinó al cridar actualitzaInfoInterna()podriem estar
        //dividint per 0
        //Així mai dividirà per 0 ja que sempre nPartidesjugades valdrà com a mínim 1
        nPartidesJugades += 1;
        nPartidesPerdudes +=1;
        actualitzaInfoInterna();

        if (!anteriorPartidaGuanyada) {
            ++actualRatxaDerrotes;
            if (actualRatxaDerrotes > maximaRatxaDerrotes) {
                maximaRatxaDerrotes = actualRatxaDerrotes;
            }
        }
        else {
            anteriorPartidaGuanyada = false;
            actualRatxaDerrotes = 1;
            if (actualRatxaDerrotes > maximaRatxaDerrotes)
                maximaRatxaDerrotes = actualRatxaDerrotes;

        }
        actualRatxaVictories = 0;
    }

    /**
     *
     * @return Tota la informació de l'usuari
     */
    /* s'usa al test i a VistaUsuariLogejat pel format que necessitem(diferent de Ranking i Records)*/
    public String getInfoJugador() {
        String ret = "Public name: " + getNomPublic()
            + "\n" + "Is human?: " + String.valueOf(getEsHuma())
            + "\n" + "Score: " + String.valueOf(getPuntuacio())
            + "\n" + "Games played: " + String.valueOf(getNumeroPartidesJugades())
            + "\n" + "Games won: " + String.valueOf(getNumeroPartidesGuanyades())
            + "\n" + "Games lost: " + String.valueOf(getNumeroPartidesPerdudes())
            + "\n" + "% of games won: " + String.valueOf(getPercentatgePartidesGuanyades())
            + "\n" + "% of games lost: " +  String.valueOf(getPercentatgePartidesPerdudes())
            + "\n" + "Average score: " + String.valueOf(getPuntuacioMitjana())
            + "\n" + "Streak of games won: " + String.valueOf((getMaximaRatxaVictories()))
            + "\n" + "Streak of games lost: " + String.valueOf((getMaximaRatxaDerrotes()))
            + "\n";
        return ret;
    }

    //s'usa a al Ranking i Records perquè necessitem format especial
    public String[] getInfoJugadorTaula() {
        String[] ret = new String[11];

        ret[0] = getNomPublic();
        if (getEsHuma()) ret[1] = "Yes";
        else ret[1] = "No";

        //ret[1] = String.valueOf(getEsHuma()); aquesta linia eliminar

        ret[2] = String.valueOf(getPuntuacio());
        ret[3] =  String.valueOf(getNumeroPartidesJugades());
        ret[4] =  String.valueOf(getNumeroPartidesGuanyades());
        ret[5] =  String.valueOf(getNumeroPartidesPerdudes());
        ret[6] =  String.valueOf(getPercentatgePartidesGuanyades());
        ret[7] =  String.valueOf(getPercentatgePartidesPerdudes());
        ret[8] =  String.valueOf(getPuntuacioMitjana());
        ret[9] =  String.valueOf((getMaximaRatxaVictories()));
        ret[10] = String.valueOf((getMaximaRatxaDerrotes()));

        return ret;
    }



    //llença excepció ja que aquest métode a la pràctica, si es controla bé la lògica del programa,
    //mai s'hauria de cridar per a un Jugador que no sigui JugadorRegistrat
    //IMPORTANT: ha de retornar String tot i que sigui mentira per poder sobreescriure el métode a
    //jugadorRegistrar i així deixar que el polimorfisme actui a registreJugadors ja que allà no es diferencia
    //entre jugadors registrats i no registrats, directament es crida als mètodes i compilador decideix en temps d'execució
    //quin métode invoca depenent del tipus
    public String getNomUsuari() throws Exception {
        throw new UnsupportedOperationException("error: No soc un jugador registrat");
    }

    ///llença excepció ja que aquest métode a la pràctica, si es controla bé la lògica del programa,
    //mai s'hauria de cridar per a un Jugador que no sigui JugadorRegistrat
    //IMPORTANT: ha de retornar String tot i que sigui mentira per poder sobreescriure el métode a
    //jugadorRegistrar i així deixar que el polimorfisme actui a registreJugadors ja que allà no es diferencia
    //entre jugadors registrats i no registrats, directament es crida als mètodes i compilador decideix en temps d'execució
    //quin métode invoca depenent del tipus
    public String getPasswordUsuari() {
        throw new UnsupportedOperationException("error: No soc un jugador registrat");
    }

    //llença excepció ja que aquest métode a la pràctica, si es controla bé la lògica del programa,
    //mai s'hauria de cridar per a un Jugador que no sigui JugadorRegistrat
    //IMPORTANT: ha de retornar String tot i que sigui mentira per poder sobreescriure el métode a
    //jugadorRegistrar i així deixar que el polimorfisme actui a registreJugadors ja que allà no es diferencia
    //entre jugadors registrats i no registrats, directament es crida als mètodes i compilador decideix en temps d'execució
    //quin métode invoca depenent del tipus
    public void setPasswordUsuari(String passwordUsuari) {
        throw new UnsupportedOperationException("error: No soc un jugador registrat");
    }
}
