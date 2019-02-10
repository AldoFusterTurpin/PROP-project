package domini;

import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

/**
 * Aquesta classe representa la valoracio d'una suposicio de codi respecte el
 * codi secret, per simplificar els punts es codifiquen com a enters on:
 * 2 indica que algun color de la suposicio esta en el lloc correcte,
 * 1 indica que algun color de la suposicio esta en el codi secret pero no al
 * lloc correcte,
 * 0 indica que algun color no esta en el codi secret.
 * Ha d'haver una puntuacio per cada color del codi sempre prioritzant el valor
 * mes alt
 *
 */
public class Valoracio implements Serializable {
    /**
     * Indica el nombre de colors del codi puntuats
     */
    private int nPunts;
    /**
     * Llista que indica la valoracio total del codi
     */
    private ArrayList<Integer> val;

//Creadores
    /**
     * Creadora de la classe, inicialitza el nombre de punts a zero i la
     * valoracio com a tot zeros
     * @param startValue tamany del codi i per tant nombre de punts de la
     * valoracio
     */
    public Valoracio(int startValue){
        nPunts=0;
        val= new ArrayList(0);
        for(int i=0; i<startValue; ++i) val.add(0);
    }
    /**
     *
     Creadora de la classe que permet copiar una valoracio
     * @param v Valoracio que volem copiar
     */
    public Valoracio(Valoracio v){
        nPunts=v.getNPunts();
        val= v.getVal();
    }
//Consultores de la classe
    /**
     * Obte el nombre d'elements del codi valorats fins al moment
     * @return el numero de punts afegits a la valoracio
     */
    public int getNPunts(){
        return nPunts;
    }
    /**
     * Obte la valoracio sencera
     * @return la llista de punts donats al codi
     */
    public ArrayList<Integer> getVal(){
        return (ArrayList<Integer>) val.clone();
    }
     /**
     * Indica la igualtat entre dos valoracions
     * @param v la valoracio amb la qual es vol fer la comparacio
     * @return true si aquest objecte is igual a l'argument v; false altrament
     */
    public boolean isEqual(Valoracio v){
       return v.getVal().equals(val);
    }

//Modificadores de la llista
    /**
     * Afegeix una valoracio completa
     * @param newVal el valor que prendra la valoracio
	 * @throws java.lang.Exception
     */
    public void setVal(ArrayList<Integer> newVal)throws Exception{
	if (newVal.size()!=val.size()) throw new Exception("La longitud de la valoracio no es correspon amb longitud del codi");
	for(int k: newVal){
            if(k!=0 && k!=1 && k!=2) throw new Exception("Puntuacio no valida, la puntuacio dels elements ha de ser 2, 1 o 0");
	}
        val=newVal;
        nPunts=newVal.size();
    }
    /**
     * Afegeix un unic punt a la valoracio
     * @param punt la puntacio donada a un element del codi
	 * @throws java.lang.Exception
     */
    public void afegirPunt(int punt)throws Exception{
        if(nPunts<val.size()){
            if(punt>=0 && punt<=2){
                val.set(nPunts, punt);
                ++nPunts;
            }
            else throw new Exception("Puntuacio no valida, la puntuacio d'un element ha de ser 2, 1 o 0");
        }
        else throw new Exception("No es pot afegir el punt, la valoracio ja esta completa");
    }

    /**
     * Ordena en ordre decreixent els punts de la valoracio
     */
    public void sort(){
        Collections.sort(val);
        Collections.reverse(val);
    }
}
