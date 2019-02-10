package domini;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * Algoritme basat en:
 * http://www.cs.uni.edu/~wallingf/teaching/cs3530/resources/knuth-mastermind.pdf
 */
public class IA5Guess extends IA implements Serializable{


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
    public IA5Guess(int amplada, ArrayList<String> colors, boolean hiHaBuits, boolean hiHaRepetits){
        super(amplada, colors, hiHaBuits, hiHaRepetits);
    }

    public Codi firstGuess(){
        return generateRandomCode();
    }

    public Codi nextGuess(Valoracio val){
        return generateRandomCode();
    }

}
