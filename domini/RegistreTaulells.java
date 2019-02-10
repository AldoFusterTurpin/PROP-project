package domini;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Collection;

/**
 * Aquesta classe representa conjunt de models de taulell de MasterMind
 *
 */
public class RegistreTaulells implements Serializable {

//Atributs
    /**
     * Indica el nombre de taulells presents en el registre
     */
    private int nTaulells;
    /**
     * Indica l'id que s'assignara al proxim taulell que s'afageixi
     */
    private int id_next;

    /**
     * Un Map que conte els taulells amb el seu id com a clau
     */
    private HashMap<Integer,ModelTaulell> taulells;
    /**
     * Llista de noms dels taulells en el registre
     */
    private HashMap<String,Integer> noms;

//Creadora
    /**
     * Creadora de la classe que inicialitza els atributs a 0
     */
    public RegistreTaulells() throws Exception{

        ModelTaulell mt=new ModelTaulell("Default");
        mt.setAlcada(5);
        mt.setAmplada(4);
        mt.setNColors(4);
        ArrayList<String> newList=new ArrayList<>(0);
        newList.add("#ff0000");
        newList.add("#00ff00");
        newList.add("#0000ff");
        newList.add("#ffff00");
        mt.setColors(newList);
        ModelTaulell newBoard=new ModelTaulell(mt);
        taulells=new HashMap();
        taulells.put(0, newBoard);
        noms=new HashMap();
        noms.put("Default",0);
        nTaulells=id_next=1;
    }
//Consultores de la classe
    /**
     * Obte el nombre de taulells en el registre
     * @return el nombre de taulells del registre
     */
    public int getNTaulells(){
        return nTaulells;
    }
    /**
     * Obte el ID del proxim taulell que s'afageixi
     * @return el ID del proxim taulell que s'afageixi
     */
    public int getNextId(){
        return id_next;
    }
    /**
     * Obte els taulells del registre
     * @return el Map taulells que conte els taulells del registre
     */
    public HashMap getTaulells(){
        return (HashMap) taulells.clone();
    }
    /**
     * Obte el ModelTaulell del registre a traves del seu identificador
     * @param id l'identificador del taulell que es vol consultar
     * @return el ModelTaulell que te per identificador id.
	 * @throws java.lang.Exception
     */
    public ModelTaulell getTaulell(int id) throws Exception{
        if(taulells.containsKey(id)) return new ModelTaulell(taulells.get(id));
        else throw new Exception("No s'ha trobat el taulell amb id = "+id);
    }

//Modificadores de la classe
    /**
     * Permet substituir un taulell del registre per un altre o afegir-lo amb
     * una clau determinada
     * @param id l'identificador del taulell que es vol substituir, si no n'hi
     * havia cap l'afegeix com a un nou taulell amb aquest identificador
     * @param board el taulell pel qual es substitueix
	 * @throws java.lang.Exception
     */
    public void substituirTaulell( int id, ModelTaulell board) throws Exception {
        if(taulells.containsKey(id)) taulells.put(id, new ModelTaulell(board));
        else throw new Exception("No es pot realitzar la substitucio, no s'ha trobat el taulell amb id = "+id);
    }
    /**
     * Afageix un nou taulell al registre i li assigna un identificador
     * @param board el taulell que es vol afegir
     * @return l'identificador corresponent al taulell afegit
     * @throws java.lang.Exception
     */
    public int afegirTaulell(ModelTaulell board) throws Exception{
        ModelTaulell newBoard=new ModelTaulell(board);
        taulells.put(id_next, newBoard);
        ++id_next;
        ++nTaulells;
        if(noms.containsKey(newBoard.getNom())) throw new Exception("Ja existeix el taulell amb nom "+newBoard.getNom());
        else noms.put(newBoard.getNom(),id_next-1);
        return id_next-1;
    }
    public boolean freeName(String n){
        return !noms.containsKey(n);
    }
    public int getIDFromName(String n){
        return noms.get(n);
    }
    /**
     * Elimina un taulell del registre pel seu identificador si el nombre de
     * taulells presents en el registre passa a ser 0 es reseteja l'id del proxim
     * a zero
     * @param id identificador del taulell que es vol eliminar
	 * @throws java.lang.Exception
     */
    public void eliminarTaulell(int id) throws Exception{
        if(id!=0){
            String name= taulells.get(id).getNom();
            if(taulells.remove(id)!=null){
                noms.remove(name);
                --nTaulells;
                if(nTaulells==1) id_next=1;

            }
            else throw new Exception("No s'ha trobat el taulell amb id = "+id);

        }
        else throw new Exception("No es pot eliminar el model Default");
    }

    //Representacio
    public ArrayList<String> getIDNoms(){
        ArrayList<String> a = new ArrayList<String> (0);
        for(int i=0; i<id_next; ++i ){
            if(taulells.containsKey(i)){
                String s="";
                s+= taulells.get(i).getNom();
                a.add(s);
            }
        }
        return a;
    }
}
