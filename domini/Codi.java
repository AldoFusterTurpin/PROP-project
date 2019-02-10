package domini;

import java.util.ArrayList;
import java.io.Serializable;


/**
 * Aquesta classe representa un codi del MasterMind aplicable tant com a codi
 * secret com a suposicio, els colors del codi estan representats com a strings
 * pel que poden ser lletres, numeros o paraules; el blanc es representa per *.
 *
 */
public class Codi implements Serializable {
//Atributs
    /**
     * Indica l'amplada del codi, es a dir el nombre de colors que el configuren
     */
    private int size;
    /**
     * Indica la combinacio de colors que configura el codi
     */
    private ArrayList<String> combinacio;

//Creadores
    /**
     * Creadora de la classe
     * @param startValue Indica el valor que prendra size
     */
    public Codi(int startValue){
        size=startValue;
        combinacio= new ArrayList(0);
    }
    /**
     * Creadora de la classe que permet copiar un codi
     * @param c Codi que volem copiar
     */
    public Codi(Codi c){
        size=c.getSize();
        combinacio= c.getCombinacio();
    }

//consultores de la classe
    /**
     * Obte l'amplada del codi
     * @return l'atribut size
     */
    public int getSize(){
        return size;
    }
    /**
     * Obte un element del del codi
     * @param i la posicio de l'element buscat (iniciant a 0)
     * @return L'element a la posicio i-essima de la combinacio
	 * @throws java.lang.Exception
     */
    public String getElement(int i) throws Exception{
       if(i>=size || i<0) throw new Exception("No existeix la posicio "+i);
       else return combinacio.get(i);
    }
    /**
     * Obte la combinacio de colors del codi
     * @return la combinacio del codi
     */
    public ArrayList<String> getCombinacio(){
        return (ArrayList<String>) combinacio.clone();
    }
    /**
     * Indica la igualtat entre dos codis
     * @param c el codi amb el que es vol fer la comparacio
     * @return true si aquest objecte is igual a l'argument c; false altrament
     */
    public boolean isEqual(Codi c){
       return c.getCombinacio().equals(combinacio);
    }

    /**
     * Genera una valoracio per un codi donat
     * @param objectiu el Codi amb el que es vol comparar
     * @return la valoracio del codi propi respecte al codi objectiu
	 * @throws java.lang.Exception
     */
    public Valoracio generarValoracio(Codi objectiu)throws Exception{
        Valoracio val=new Valoracio(size);
            if(size!=objectiu.getSize()) throw new Exception("L'amplada dels dos codis es diferent, impossible comparar-los");
            else {
                ArrayList<String> auxI=new ArrayList<> (combinacio);
		ArrayList<String> auxS=objectiu.getCombinacio();
		for(int i=0;i<size;++i){
                    if(auxS.get(i).equals(auxI.get(i))){
                    val.afegirPunt(2);
                    auxS.set(i,"");
                    auxI.set(i,"");
		}
            }
            for(int j=0;j<size;++j){
                if(!auxS.get(j).equals("")){
                for(int k=0; k<size;++k){
    	            if(auxS.get(j).equals(auxI.get(k))){
	                val.afegirPunt(1);
	                auxS.set(j,"");
	                auxI.set(k,"");
	                break;
	            }
	        }
                if(!auxS.get(j).equals("")) val.afegirPunt(0);
                }
            }
            val.sort();
            return val;
        }
    }

//modificadores de la classe
    /**
     * Defineix una nova combinacio pel codi
     * @param newComb es el nou valor de la combinacio
     */
    public void setCombinacio(ArrayList<String> newComb){
        combinacio=newComb;
    }
    /**
     * Afageix un nou color a la combinacio
     * @param color el color que s'afegeix al final de la combinacio
	 * @throws java.lang.Exception
     */
    public void afegirColor(String color) throws Exception{
        if(combinacio.size()<size) combinacio.add(color);
        else throw new Exception("No es pot afegir el color "+color+", el codi ja esta complet");
    }

//Representacio
    /**
     * Dibuixa una representacio grafica del codi
     * @return¡Representacio grafica del codi com a string
     */
    public ArrayList<String> printCodi(){
	int i;
	ArrayList<String> ret = new ArrayList<String> (0);
	for(i=0; i<combinacio.size(); ++i) {
            ret.add(combinacio.get(i));
	}
	return ret;
    }
}
