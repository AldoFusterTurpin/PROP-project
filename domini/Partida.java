package domini;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Partida implements Serializable {
//Atributs
    /**
     * Estat d'un taulell en un moment donat, incloient la seva configuració
     * a partir de ModelTaulell, els codis introduits i les comprovacions.
     */
    private EstatTaulell estat;

    /**
     * Codemaker d'aquesta partida, o sigui, qui construeix el codi secret i avalua les solucions
     */
    private Jugador codemaker;

    /**
     * Codebreaker d'aquesta partida, o sigui,
     * qui introdueix els codis per intentar trobar el codi secret
     */
    private Jugador codebreaker;

    /**
     * Indica si el jugador assignat com a codemaker és una IA o no.
     */
    private boolean codemakerIsIA;


    /**
     * Indica si el jugador assignat com a codebreaker és una IA o no.
     */
    private boolean codebreakerIsIA;

    /**
     * Booleà que determina si es permeten valors de casella buida en els còdis
     * d'aquesta partida, representat com a *
     */
    private boolean permetBuit;

    /**
     * Booleà que determina si es permeten codis amb colors repetits en aquesta partida.
     */
    private boolean permetDuplicats;

    /**
     * Booleà que determina si es donen pistes en aquesta partida.
     */
    private boolean donarPistes;

    /**
     * Booleà que indica si la partida s'ha acabat, ja sigui perquè s'han jugat
     * totes les rondes possibles o perquè s'ha acabat el codi secret.
     */
    private boolean partidaIsOver;

    /**
     * Booleà que indica si ha guanyat el codemaker o codebreaker.
     * false -> ha guanyat el codemaker
     * true -> ha guanyat el codebreaker
     */
    private boolean guanyador;

    /**
     * Intent d'un codebreaker en un torn determinat
     */
    private Codi intentCodebreaker;

    /**
     * Boolea que indica si s'ha proporcionat un intent valid aquest torn
     */
    private boolean hiHaIntent;

    /**
     * Valoracio feta pel codemaker
     */
    private Valoracio valoracioCodemaker;

    /**
     * Boolea que indica si s'ha proporcionat una valoracio valida aquest torn
     */
    private boolean hiHaVal;

    /**
     * Indica si es el torn del codebreaker o codemaker
     * true => torn del codebreaker
     * false => torn del codemaker
     */
    private boolean tornCodebreaker;

    /**
     * Codi secret per a inicialitzar la partida
     */
    private Codi codiSecret;

    /**
     * Int que conta en quina ronda de la partida estem, per determinar quan
     * ha d'acabar.
     */
    private int ronda;


    /**
     * IA encarregada de calcular cada resposta d'un jugador IA codebreaker
     * seguint un algoritme genètic.
     */
    private IA IA;

    /**
     * Llista de colors legals, amb * si hi ha colors buits
     */
    private ArrayList<String> colors;

    //
    //Determinar si cal ficar temps per ronda de moment.
    //

    //Creadora

    /**
     * funció creadora d'una partida, inicialitza el EstatTaulell estat segons els arguments
     * donats. Assumeix que la IA serà de tipus Genètic
     * @param model ModelTaulell Necessari per a inicialitzar l'estat
     * @param config Opcions Inicialitza les variables de la partida que determinen si
     * es permeten colors duplicats, colors buits i pistes.
     * @param codemaker Jugador Determina qui és el codemaker d'aquesta partida, o sigui, qui
     * construeix el codi secret i avalua les solucions
     * @param codebreaker Jugador Determina qui és el codebreaker d'aquesta partida, o sigui,
     * qui introdueix els codis per intentar trobar el codi secret
     */
    public Partida(ModelTaulell model, Opcions config, Jugador codemaker, Jugador codebreaker) {
        this.estat = new EstatTaulell(model);
        this.permetBuit = config.getPermetBuit();
        this.permetDuplicats = config.getPermetDuplicats();
        this.ronda = 0;
        this.partidaIsOver = false;

        this.codemaker = codemaker;
        this.codebreaker = codebreaker;


        this.codemakerIsIA = !this.codemaker.getEsHuma();
        this.codebreakerIsIA = !this.codebreaker.getEsHuma();

        this.colors = estat.getColors();
        if (this.permetBuit) colors.add("*");

        if(codebreakerIsIA || codemakerIsIA)
            this.IA = new IAGenetic(estat.getCodeSize(), this.colors,this.permetBuit, this.permetDuplicats);

        this.tornCodebreaker = true;
        this.hiHaIntent = false;
        this.hiHaVal = false;
    }

    /**
     * Funció creadora d'una partida, inicialitza el EstatTaulell estat segons els arguments
     * donats. Conté paràmetre per a inicialitzar el tipus de IA.
     * @param model ModelTaulell Necessari per a inicialitzar l'estat
     * @param config Opcions Inicialitza les variables de la partida que determinen si
     * es permeten colors duplicats, colors buits i pistes.
     * @param codemaker Jugador Determina qui és el codemaker d'aquesta partida, o sigui, qui
     * construeix el codi secret i avalua les solucions
     * @param codebreaker Jugador Determina qui és el codebreaker d'aquesta partida, o sigui,
     * qui introdueix els codis per intentar trobar el codi secret
     * @param IAIsGenetic Indica si la IA del codebreaker (en el cas que sigui IA)
     * ha de ser de tipus genetic o 5Guess. Si es true llavors sera de tipus Genetic,
     * i si no de tipus 5Guess.
     */
    public Partida(ModelTaulell model, Opcions config, Jugador codemaker, Jugador codebreaker, boolean IAIsGenetic) {
        this.estat = new EstatTaulell(model);
        this.permetBuit = config.getPermetBuit();
        this.permetDuplicats = config.getPermetDuplicats();
        this.ronda = 0;
        this.partidaIsOver = false;

        this.codemaker = codemaker;
        this.codebreaker = codebreaker;


        this.codemakerIsIA = !this.codemaker.getEsHuma();
        this.codebreakerIsIA = !this.codebreaker.getEsHuma();

        this.colors = estat.getColors();
        if (this.permetBuit) colors.add("*");

        if(codebreakerIsIA || codemakerIsIA) {
            if (IAIsGenetic) this.IA = new IAGenetic(estat.getCodeSize(), this.colors,
                                                     this.permetBuit, this.permetDuplicats);
            else this.IA = new IA5Guess(estat.getCodeSize(), this.colors,
                                        this.permetBuit, this.permetDuplicats);
        }

        this.tornCodebreaker = true;
        this.hiHaIntent = false;
        this.hiHaVal = false;

    }

    //CONSULTORES
    /**
     * Consultora de l'estat de la partida
     * @return estat EstatTaulell, classe contenent tota l'informacio d'una
     * partida sense acabar.
     */
    public EstatTaulell getEstat() {
        return estat;
    }

    /**
     * Consultora de si una partida s'ha finalitzat o no
     * @return boolean que indica si la partida s'ha acabat o no.
     * false -> La partida encara s'està jugant.
     * true -> La partida ja s'ha acabat
     */
    public boolean isPartidaOver() {
        return partidaIsOver;
    }

    /**
     * Consultora del guanyador d'una partida
     * @return booleà que indica el guanyador d'una partida.
     * false -> guanya el codemaker
     * true -> guanya el codebreaker
     * @throws Exception No es pot cridar la funció sense que hagi acabat la partida
     */
    public boolean isGuanyador() throws Exception {
        if (this.partidaIsOver) return guanyador;
        else throw new Exception ("No hi pot haver guanyador si encara no s'ha acabat la partida");
    }

    public Jugador getCodemaker() {
        return codemaker;
    }

    public Jugador getCodebreaker() {
        return codebreaker;
    }

    public Jugador getHumanPlayer() {
        if (codemakerIsIA) return codebreaker;
        else return codemaker;
    }

    public boolean getCodemakerIsIA() {
        return codemakerIsIA;
    }

    public boolean getCodebreakerIsIA() {
        return codebreakerIsIA;
    }

    public ArrayList<String> getAllowedColors() {
        return colors;
    }

    public boolean getTornCodebreaker() {
        return tornCodebreaker;
    }

    public int getRonda() {
        return ronda;
    }

    public Codi getResposta(int i) {
        try {
            return estat.getIntent(i);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Valoracio getValoracio(int i) {
        try {
            return estat.getValoracio(i);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public int getAmplada() {
        return estat.getCodeSize();
    }

    public int getAlcada() {
        return estat.getBoardSize();
    }

    /**
     * Consultora de la puntuació del codebreaker, que és la suma dels punts de
     * totes les seves valoracions
     * @return int que conté la suma de tots els punts de les valoracions de
     * cada resposta.
     */
    public int getPuntuacio() throws Exception{
        if (!this.partidaIsOver) throw new Exception ("No hi ha puntuacio fins que la partida no acabi");
        else return (this.estat.getBoardSize() - this.ronda);
    }

    public Codi getCodiSecret() {
        return estat.getCodiSecret();
    }


      /**
       * Retorna el codi secret
       * @param ensenyarTot printejar tota la informació de la partida, inclos
       * codi secret
       * @return Codi secret de la partida
       */
    public void ensenyarInfoPartida(boolean ensenyarTot) {
        ArrayList<ArrayList<String> > info = estat.showTaulell();
        for (int i = 0; i < info.size(); i++) System.out.println(info.get(i));
    }

//Privades

    private void printLlista(ArrayList<String> llista) {
        for(int i=0; i<llista.size(); ++i) {
            System.out.print("|["+llista.get(i)+"]");
	}
        System.out.println("|");
    }



    /**
     * Funcio que printeja tots els colors valids, per a facilitar a l'usuari
     * escriure codis
     */
    public void printColorsLegals() {
        System.out.println("Colors valids: ");

        printLlista(this.colors);

        if (this.permetBuit) System.out.println("Caselles buides permeses: [*]");
        if (this.permetDuplicats) System.out.println("Colors duplicats permesos");
        else System.out.println("Colors duplicats NO permesos");
        System.out.println("Separar colors per comes, incloient al final");
        //Fer que també detecti EOL com a separador
    }





//Públiques

    public void initCodiSecret(Codi codiSecret) {
        this.codiSecret = codiSecret;
    }

    /**
     * Inicialitza el codi secret. Si el codemaker és una IA, es genera un codi
     * aleatori, si és un jugador es fa server codiSecret inicialitzat per initCodiSecret
     * @return Boolea indicant si s'ha inicialitzat correctament.
     */
    public boolean initPartida(){
        if (this.codemakerIsIA) {
            codiSecret = IA.generateRandomCode();
            try {
                estat.setCodiSecret(codiSecret);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return true;
        } else {
            try {
                 System.out.println("introduint codi secret");
                estat.setCodiSecret(codiSecret);
                 System.out.println("comprovant codi secret");
                if(estat.codiCompleixNormes(permetBuit, permetDuplicats) == 0) return true;
                else return false;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }
    }



    /**
     * Assigna un valor al codi que el codebreaker enviara com a resposta aquest torn
     * @param intent Codi que el codebreaker huma enviara com a resposta aquest torn
     */
    public void setCodiIntent(Codi intent) {
        this.intentCodebreaker = intent;
        this.hiHaIntent = true;
    }

    /**
     * Funcio que juga el torn d'un codebreaker
     * @return false si es pot jugar al menys un torn més, true si la partida s'ha acabat
     * @throws Exception
     */
    public boolean tornCodebreaker() throws Exception {
        if (this.partidaIsOver) throw new Exception("No es pot jugar un torn ja que la partida ja s'ha acabat.");
        else if (!this.codebreakerIsIA && !this.hiHaIntent) throw new Exception("No s'ha introduit cap intent");
        else if (!this.tornCodebreaker) throw new Exception("No es el torn del codebreaker");
        else {
            //IA
            if (this.codebreakerIsIA) {
                System.out.println("Intent " + this.ronda + " de la IA.");
                if(this.ronda == 0) intentCodebreaker = IA.firstGuess();

                else {
                    Valoracio v = this.estat.getValoracio(this.ronda-1);
                    intentCodebreaker = IA.nextGuess(v);
                }

                System.out.println("La maquina respon:" + intentCodebreaker.printCodi());
            }
            //afegir intent
            boolean codiValid = true;
            try {
                if (!estat.afegirIntent(intentCodebreaker)) codiValid = false;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new Exception("Inicialitza la partida abans de continuar.");
            }
            if (!codiValid)
                    throw new Exception ("Codi no valid degut a colors incorrectes");
            //comprovar si guess es el codi secret
            if (intentCodebreaker.isEqual(this.estat.getCodiSecret())) {
                System.out.println("Codi secret encertat! Felicitats, Codebreaker!");
                this.partidaIsOver = true;
                this.guanyador = true;
                return true;
            } else if (this.estat.isOver()) { //comprovar si s'han acabat els torns
                System.out.println("Totes les files exhaurides. Felicitats, Codemaker!");
                this.partidaIsOver = true;
                this.guanyador = false;
                return true;
            }
            this.tornCodebreaker = false;
            this.hiHaIntent = false;
            return false;
        }
    }

    /**
     * Assigna un valor a la valoracio que el codebreaker enviara com a resposta aquest torn
     * @param val Valoracio que el codemaker huma enviara com a resposta aquest torn
     */
    public void setValoracio(Valoracio val) {
        this.valoracioCodemaker = val;
        this.hiHaVal = true;
    }

    /**
     * Part d'un torn del codemaker. Si el codebreaker es huma s'ha d'haver passat
     * una valoracio correcta anteriorment amb setValoracio()
     * @return true si s'ha fet la valoracio correctament, false si no
     * @throws Exception
     */
    public boolean tornCodemaker() throws Exception {

        if (this.codemakerIsIA) {
            try {
                this.estat.maquinaFaVal();
                this.estat.getValoracio(ronda).toString();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            if (!this.hiHaVal) throw new Exception ("No hi ha cap valoracio");
            try {
                System.out.println();
                if(!this.estat.jugadorFaVal(valoracioCodemaker.getVal())) return false;

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        this.ronda++;
        this.hiHaVal = false;
        this.tornCodebreaker = true;
        estat.showTaulell();
        return true;
    }

    /**
     * Acaba la partida prematurament, tria com a guanyador al jugador no humà
     * o al codemaker en cas de dos jugadors IA.
     */
    public void abortarPartida() {
        this.partidaIsOver = true;
        if (!this.codemakerIsIA)
            this.guanyador = true;
        else //if (!this.codebreakerIsIA)
            this.guanyador = false;
    }

}
