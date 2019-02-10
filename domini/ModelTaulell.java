package domini;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Aquesta classe representa el taulell de MasterMind, permetent configurar els
 * parametres segons la partida.
 *
 */
public class ModelTaulell implements Serializable{
//Atributs
    /**
     * Maxima alçada que pot tenir un taulell
     */
    private static final int MAX_ALCADA=100;
    /**
     * Maxima amplada que pot tenir un taulell
     */
    private static final int MAX_AMPLADA=20;
    /**
     * Maxim nombre de colors que pot tenir un taulell
     */
    private static final int MAX_NUMCOLORS=30;


    /**
     * Nom del taulell
     */
    private String nom;
    /**
     * Alçada del taulell, es a dir el nombre d'intents maxims que es poden fer
     */
    private int alcada;
    /**
     * Amplada del taulell, es a dir el nombre de colors que configuren el codi
     */
    private int amplada;
    /**
     * El nombre de colors diferents que es poden usar en el codi
     */
    private int nColors;
    /**
     * La llista de colors que es poden usar en el codi
     */
    private ArrayList<String> colors;

//Creadores

    /**
     * Creadora de la classe amb els atributs inicialitzats a zero excepte la id
     * @param default_name Nom del taulell
     */
    public ModelTaulell(String default_name){
        nom=default_name;
        amplada=0;
        alcada=0;
        nColors=0;
        colors=new ArrayList<>(0);

    }
    /**
     * Creadora de la classe que permet copiar un ModelTaulell
     * @param antic ModelTaulell que es vol copiar
     */
    public ModelTaulell(ModelTaulell antic){
        nom=antic.getNom();
        alcada=antic.getAlcada();
        amplada=antic.getAmplada();
        nColors=antic.getNColors();
        colors = antic.getColors();
    }

//modificadores de la classe
    /**
     * Configura el nom del teulell
     * @param newValue valor que prendra el parametre nom
     */
    public void setNom(String newValue){
        nom=newValue;
    }
    /**
     * Configura l'alçada del taulell
     * @param newValue valor que prendra el parametre alcada
	 * @throws java.lang.Exception
     */
    public void setAlcada(int newValue) throws Exception{
        if(newValue<=MAX_ALCADA) alcada=newValue;
		else throw new Exception("Alçada maxima permesa es "+MAX_ALCADA);
    }
     /**
     * Configura l'amplada del taulell
     * @param newValue valor que prendra el parametre amplada
	 * @throws java.lang.Exception
     */
    public void setAmplada(int newValue)throws Exception{
        if(newValue<=MAX_AMPLADA) amplada=newValue;
		else throw new Exception("Amplada maxima permesa es "+MAX_AMPLADA);
    }
     /**
     * Configura el numero de colors del taulell
     * @param newValue valor que prendra el parametre nColors
	 * @throws java.lang.Exception
     */
    public void setNColors(int newValue) throws Exception{
        if(newValue<=MAX_NUMCOLORS && newValue>=amplada) {
            ArrayList<String> newList =new ArrayList<> (0);
            for(int i=0;i<newValue;++i){
                if(i<nColors) newList.add(colors.get(i));
                else newList.add("*");
            }
            nColors=newValue;
            colors=newList;
        }
        else if(newValue>=MAX_NUMCOLORS) throw new Exception("Nombre de colors maxims permesos es "+MAX_NUMCOLORS);
	else throw new Exception("El nombre de colors ha de ser com a minim igual que l'amplada");
    }
     /**
     * Configura els colors disponibles en el taulell
     * @param newList valor que prendra la llista colors si no hi ha colors
     * repetits
	 * @throws java.lang.Exception
     */
    public void setColors(ArrayList<String> newList) throws Exception{
        if(!buscarRepetits(newList)) {
            if(newList.contains("*")) throw new Exception ("Color list can't contain empty colors");
            colors=newList;
        }
	else throw new Exception("La llista de colors NO pot contenir colors repetits");
    }

//consultores de la classe

    /**
     * Obte l'identificador del taulell
     * @return l'atribut id
     */
    public String getNom(){
	return nom;
    }
    /**
     * Obte l'alçada del taulell
     * @return l'atribut alcada
     */
    public int getAlcada(){
	return alcada;
    }
    /**
     * Obte l'amplada del taulell
     * @return l'atribut amplada
     */
    public int getAmplada(){
	return amplada;
    }
    /**
     * Obte el numero de colors del taulell
     * @return l'atribut nColors
     */
    public int getNColors(){
	return nColors;
    }
    /**
     * Obte el llistat de colors disponibles en el taulell
     * @return l'atribut colors
     */
    public ArrayList<String> getColors(){
	return (ArrayList<String>) colors.clone();
    }

//Representacio
    /**
     * Dibuixa una representacio grafica del taulell mostrant els colors i els
     * espais disponibles per codi
     * @return Representacio grafica del taulell
     */
    public ArrayList<String> showTaulell(){
	ArrayList<String> ret= new ArrayList<String>(0);
    	ret.add(printColors());
	ret.add("");
        int i;
	for(i=0; i<alcada; ++i) ret.add(printFila());
	return ret;
    }
//Privades
    /**
     * Mostra la llista de colors
     */
    private String printColors(){
        int i;
	String ret="Colors:";
	for(i=0; i<nColors; ++i) {
        	ret+=" "+colors.get(i);
	}
        return ret;
    }

    /**
     * Dibuixa una fila amb [ ] per cada possible element possible del codi
     */
    private String printFila(){
        int i;
        String ret= new String("");
	for(i=0; i<amplada; ++i){
	    ret+="[ ]";
	}
	return ret;
    }

    private boolean buscarRepetits(ArrayList<String> newList) {
        ArrayList<String> al = new ArrayList(newList);
        String s;
        while(al.size()>1){
            s=al.remove(0);
            if(al.contains(s)) return true;
        }
        return false;
    }
}
