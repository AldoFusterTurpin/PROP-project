package domini;

import java.util.ArrayList;
import java.io.Serializable;

/**
 *Aquesta classe representa un taulell en una partida, i per tant es va
 * actualitzant quan aquesta progressa.
 */
public class EstatTaulell implements Serializable {
//Atributs
	/**
	 * Model de taulell del qual es mostra l'estat
	 */
	private ModelTaulell taulell;

    private Codi codiSecret;
    /**
     * El nombre d'intents realitzats pel CodeMaker intentant esbrinar el codi
     * secret
     */
    private int numIntents;
    /**
     * Llista que conte els diversos intents del CodeBreaker ordenats del primer
     * al mes recent
     */
    private ArrayList<Codi> intents;
    /**
     * Llista amb les valoracions del CodeMaker sobre els intents del
     * CodeBreaker, la valoracio a la posicio i puntua el codi a la posicio i
     * d'intents
     */
    private ArrayList<Valoracio> valoracions;
    /**
     * Indica si s'ha assignat codi secret
     */
    private boolean secretIsSet;
//Creadora

    /**
     * Creadora de la classe, inicialitza el valors del taulell segons el model
     * i prepara per iniciar una partida
     * @param mt ModelTaulell del qual es prenen els atributs per la
     * inicialitzacio
     */
    public EstatTaulell(ModelTaulell mt){
	taulell=mt;
        numIntents=0;
        intents=new ArrayList(0);
        valoracions=new ArrayList(0);
        codiSecret=new Codi(taulell.getAmplada());
        secretIsSet=false;
    }

//Consultores de la classe
    /**
     * Obte el codi secret creat pel CodeMaker
     * @return el Codi secret
     */
    public Codi getCodiSecret(){
        Codi c=new Codi(codiSecret);
        return c;
    }
    /**
     * Obte l'amplada del taulell
     * @return l'atribut amplada del model de taulell
     */
    public int getCodeSize(){
        return taulell.getAmplada();
    }
    /**
     * Obte l'alçada del taulell
     * @return l'atribut alcada del model de taulell
     */
    public int getBoardSize(){
        return taulell.getAlcada();
    }
    /**
     * Obte una suposicio de codi realitzada pel CodeBreaker
     * @param i el torn en el que s'ha realitzat l'intent que es vol obtenir
     * (a partir de zero)
     * @return l'intent en la posicio i
	 * @throws java.lang.Exception
     */
    public Codi getIntent(int i)throws Exception{
        if(i<numIntents && i>=0) {
            Codi c=new Codi(intents.get(i));
            return c;
        }
		throw new Exception("Error al obtenir l'intent numero " + i + ", de moment nomes s'han realitzat "+numIntents+" intents");
    }
    /**
     * Obte el llistat de colors admesos al taulell
     * @return la llista amb els colors usables en aquest taulell
     */
    public ArrayList<String> getColors(){
        return taulell.getColors();
    }
    /**
     * Obte una valoracio realitzada pel CodeMaker
     * @param v el torn en el que s'ha realitzat la valoracio que es vol obtenir
     * (a partir de zero)
     * @return la valoracio en la posicio v
	 * @throws java.lang.Exception
     */
    public Valoracio getValoracio(int v)throws Exception{
        if(v<valoracions.size() && v>=0) {
            Valoracio val=new Valoracio(valoracions.get(v));
            return val;
        }
        throw new Exception("Error al obtenir la valoracio " + v + ", de moment nomes s'han realitzat "+valoracions.size()+" valoracions");
    }
    /**
     * Consulta si ja s'han realitzat el maxim d'intents possibles
     * @return true si el CodeBreaker ha gastat tots els seus intents i per tant
     * el CodeMaker guanya, false si el CodeBreaker encara te intents disponibles
     */
    public boolean isOver(){
        return taulell.getAlcada()<=numIntents;
    }
    /**
     * Indica si el codi secret compleix les normes de la partida
     * @param permetBuit true si el codi secret pot tenir espai en blanc, false
     * si no
     * @param permetRepetits true si el codi secret pot tenir colors repetits,
     * false si no
     * @return 0 si el codi secret compleix les normes de la partida, 1 si conte
     * el buit i no hauria, 2 si conte repetits i no estan permesos i 3 si te
	 * colors no permesos
     */
    public int codiCompleixNormes(boolean permetBuit, boolean permetRepetits){
        Codi c= new Codi(codiSecret);
        if(!codiTeColorsCorrectes(c)) return 3;
        if(!permetBuit && conteBuit(c)){
            return 1;
        }
        if(!permetRepetits && teRepetits(c)){
            return 2;
        }
        return 0;
    }

//Modificadores de la classe
    /**
     * Afegeix el codi secret realitzat pel CodeMaker si respecta els colors
     * @param secret el Codi que es vol usar com a codi secret
	 * @throws java.lang.Exception
     */
    public void setCodiSecret(Codi secret) throws Exception{
        if(codiTeColorsCorrectes(secret)){
            codiSecret=secret;
            secretIsSet=true;
        }
	else throw new Exception("El codi conte colors invalids");
    }
    /**
     Afegeix una suposicio de codi del CodeBreaker si respecta els colors
     * @param intent el Codi que el CodeBreaker vol comparar amb el secret
     * @return false si conte colors no admesos al taulell, true si es valid
     * i s'ha afegit. NO comprova les altres opcions de la partida
     * @throws java.lang.Exception
     */
    public boolean afegirIntent(Codi intent) throws Exception{
        if(secretIsSet){
            if(codiTeColorsCorrectes(intent)){
                intents.add(intent);
                valoracions.add(new Valoracio(taulell.getAmplada()));
                ++numIntents;
                return true;
            }
            return false;
        } throw new Exception("No es pot realitzar un intent si encara no hi ha codi secret");
    }
    /**
     * Realitza una valoracio automatica de l'ultim intent del CodeBreaker
     * @throws java.lang.Exception
     */
    public void maquinaFaVal() throws Exception{
        if(numIntents>0){
            Valoracio val;
            Codi c=new Codi(intents.get(numIntents-1));
            //si maquina es codemaker
            val=codiSecret.generarValoracio(c);
            valoracions.set(numIntents-1,val);
        } else throw new Exception("No es pot realitzar una valoracio si no hi ha cap intent");
    }
    /**
     * Realitza una valoracio manual de l'ultim intent del CodeBreaker
     * @param ar Llista de la valoracio feta pel jugador
	 * @return true si la valoracio es correcte i s'ha afegit, false altrament
     * @throws java.lang.Exception
     */
    public boolean jugadorFaVal(ArrayList<Integer> ar) throws Exception{
        if(numIntents>0){
            Valoracio val = new Valoracio(taulell.getAmplada());
            val.setVal(ar);
            val.sort();
            Codi c= new Codi( intents.get(numIntents-1));
            if(compararValoracio(c, val)){
                valoracions.set(numIntents-1,val);
		return true;
            }
            return false;
        } else throw new Exception("No es pot realitzar una valoracio si no hi ha cap intent");
    }
//Representacio
    /**
     * Dibuixa una representacio grafica de l'estat del taulell on es veu els
     * colors permesos, els intents realitzats, els intents que encara no s'han
     * fet i opcionalment el codi secret
     * @param showAll boolea que indica si es mostra o no el codi secret; si es
     * true es mostra, si es false no
     * @return Codificacio del taulell com una llista d'Strings
     */
    public ArrayList<ArrayList<String>> showTaulell(){
		ArrayList<ArrayList<String>> ar= new ArrayList<ArrayList<String>> (0);
        int i;
        for(i=taulell.getAlcada(); i>numIntents; --i){
			ArrayList<String> s= new ArrayList<String> (0);
            for(int j=0; j<taulell.getAmplada(); ++j) s.add("*");
            ar.add(s);
        }
        for(i=numIntents-1; i>=0; --i) {
            ArrayList<String> s=intents.get(i).printCodi();
            ar.add(s);
        }
		return ar;
    }
//Privades

    /**
     * Indica si els colors del codi estan permesos en el taulell
     * @param c el codi que volem consultar
     * @return true si el codi conte nomes colors permesos pel taulell
     * (o el buit "*"), altrament false
	 * @throws java.lang.Exception
     */
    private boolean codiTeColorsCorrectes(Codi c){
        ArrayList<String> aux=c.getCombinacio();
        ArrayList<String> col=taulell.getColors();
        for(int i=0;i<taulell.getAmplada();++i){
            if(!col.contains(aux.get(i)) && !aux.get(i).equals("*")) return false;
        }
        return true;
    }
    /**
     * Indica si la valoracio respecte un codi es correcte
     * @param c el codi sobre el que s'ha fet la valoracio
     * @param val la valoracio que es vol evaluar
     * @return true si val es una valoracio correcte pel codi c, false altrament
     */
    private boolean compararValoracio(Codi c, Valoracio val) throws Exception{
        Valoracio v=codiSecret.generarValoracio(c);
        return val.isEqual(v);
    }

    /**
     * Indica si un codi conte el buit ("*")
     * @param codi que es vol validar
     * @return true si el codi conte almenys una aparicio de "*", false si no
     */

    private boolean conteBuit(Codi c){
        return c.getCombinacio().contains("*");
    }
    /**
     * Indica si un codi te colors repetits
     * @param c el codi que es vol validar
     * @return true si algun color apareix mes de un cop en el codi, false si no
     */
    private boolean teRepetits(Codi c){
        ArrayList<String> al = c.getCombinacio();
        String s;
        while(al.size()>1){
            s=al.remove(0);
            if(al.contains(s)) return true;
        }
        return false;
    }
}
