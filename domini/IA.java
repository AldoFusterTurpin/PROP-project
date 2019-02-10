package domini;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public abstract class IA implements Serializable{

    /**
     * Int indicant l'amplada de cada codi de resposta que s'ha de generar
     */
    protected int amplada;

    /**
     * Int indicant quants guesses ha fet la IA
    */
    protected int ronda;

    /**
     * ArrayList indicant quins colors es poden fer servir
     */
    protected ArrayList<String> colors;

    /**
     * ArrayList que desa tots els intents fets per la IA
     */
    protected ArrayList<Codi> intents;

    /**
     * ArrayList que desa totes les valoracions dels intents realitzats
     */
    protected ArrayList<Valoracio> valoracions;

    /**
     * Boolea indicant si hi poden haver repetits
     */
    protected boolean hiHaRepetits;

    /**
     * ArrayList que indica el últim codi enviat per la IA
     */
    protected Codi lastIntent;

    //Constructora
    /**
     * Constructora de la classe, inicialitza la IA a partir de l'amplada
     * proporcionada.
     * @param amplada Amplada de cada codi de la resposta que s'ha de generar.
     * @param colors ArrayString contenint tots els possibles colors, incloient
     * valor buit si cal.
     * @param hiHaBuits boolean indicant si hi pot haver caselles buides en la solució "*"
     * @param hiHaRepetits boolean indicant si hi pot haver colors repetits en
     * una resposta donada.
     */
    public IA(int amplada, ArrayList<String> colors, boolean hiHaBuits, boolean hiHaRepetits){
        this.amplada = amplada;
        this.ronda = 0;
        this.colors = new ArrayList<>(colors);
        this.hiHaRepetits = hiHaRepetits;

        this.intents = new ArrayList();
        this.valoracions = new ArrayList();
    }

    /**
     * Genera un codi aleatori segons la configuració de la partida
     * @return Codi amb colors aleatòris que segueix les normes
     * configurades actuals
     */
    protected Codi generateRandomCode() {
        ArrayList<String> possiblesColors = new ArrayList(this.colors);

        Codi codiGenerat = new Codi(this.amplada);
        Random random = new Random();
        for(int i = 0; i < this.amplada; i++){
            int j = random.nextInt(possiblesColors.size());

            try {
                codiGenerat.afegirColor(possiblesColors.get(j));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            if(!this.hiHaRepetits) possiblesColors.remove(j);
        }

        return codiGenerat;
    }

    abstract public Codi firstGuess();

    abstract public Codi nextGuess(Valoracio val);
}
