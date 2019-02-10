package domini;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.Serializable;


//import domini.Jugador

public class RegistreJugadors implements Serializable {
    //PRIVAT
    //key->id del jugador(el nom public); value->el jugador
    private Map<String, Jugador> jugadors;

    //key->username; value-> id del jugador(el nom public)
    //pels jugadors registrats volem saber quin id(nompublic) té cada
    //jugador registrat(username)
    private Map<String, String> usernames;


    //PUBLIC

    /**
     * Constructor sense paràmetres
     */
    public RegistreJugadors() {
        jugadors = new HashMap<>();
        usernames = new HashMap<>();
    }

    /**
     * Obte el nombre de jugadors que conté Registre de Jugadors
     * @return El nombre de jugadors que conté Registre de Jugadors
     */
    public int obtenirNombreJugadors(){
        return jugadors.size();
    }

    /**
     * donat un username retorna un Optional amb l'id del jugador corresponent si existeix.
     * @param username del jugador
     * @return UN optional amb el corresponent id o un Optional.Empty si no existeix
     */
    public Optional<String> obtenirIdDeUsername(String username) {
        if (username == null) return Optional.empty();

        //else...
        Optional<String> idJugador = Optional.empty();
        if (usernames.containsKey(username)) idJugador = Optional.of(usernames.get(username));
        return idJugador;
    }

    /**
     * Retorna si el jugador amb id 'idJugador' es troba al registre de jugadors
     * @param idJugador L'id del Jugador
     * @return Certi si el registre de jugadors conté el jugador amb id 'idJugador'. Altrament fals
     */
    public boolean registreConteJugador(String idJugador) {
        if (idJugador == null) return false;
        //else..
        return jugadors.containsKey(idJugador) ;
    }

    //http://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html
    public boolean existeixJugadorRegistrat(String username) {
        if (username == null) return false;
        //else...
        Optional<String> idJugador = obtenirIdDeUsername(username);
        if(! idJugador.isPresent()) return false;
        else return registreConteJugador(idJugador.get());
    }

    public void actualitzarJugador(Jugador j){
        String nom=j.getNomPublic();
        jugadors.put(nom,j);
    }

    public boolean afegirJugador(String nomPublic, boolean esHuma) throws Exception {
        if (nomPublic != null) {
            if (nomPublic.equals("")) return false;
            else if (registreConteJugador(nomPublic)) {
                //throw new Exception("error intern al afegirJugador, ja existeix jugador amb aquest id(nomPublic)");
                return false;
            }
            else {
                //capçalera del constructor de Jugador:
                //public Jugador(String nomPublic, boolean esHuma);
                jugadors.put(nomPublic, new Jugador(nomPublic, esHuma));
                return true;
            }
        }
        else return false;
    }


    //per afegir jugadors registrats
    public boolean afegirJugadorRegistrat(String nomPublic, String username, String passwordUsuari) throws Exception {
        if (nomPublic != null && username!= null && passwordUsuari != null) {
            if (nomPublic.equals("") || username.equals("")) return false;
            else if (registreConteJugador(nomPublic)) {
                return false;
                //throw new Exception("error intern al afegirJugadorRegistrat, ja existeix jugador amb aquest id(nomPublic)");
            }
            else if (usernames.containsKey(username)) return false;
            else {
                //capçalera del constructor de JugadorRegistrat:
                //public JugadorRegistrat(String nomPublic, String username, String passwordUsuari);
                jugadors.put(nomPublic, new JugadorRegistrat(nomPublic, username, passwordUsuari));
                usernames.put(username, nomPublic);
                return true;
            }
        }
        else return false;
    }



    /**
     * Elimina el jugador amb id(nomPublic) 'idJugador' del conjunt de Jugadors
     * @param idJugador l'id del jugador que es vol eliminar
     * @return Si s'ha pogut eliminar el jugador
     * @throws Exception Si no s'ha pogut eliminar el jugador perquè no
     * existeix un jugador amb id 'idJugador'
     */
    public boolean eliminarJugador(String idJugador) throws Exception {
        if (idJugador != null) {
            if (registreConteJugador(idJugador)) {
                String username = obtenirJugador(idJugador).getNomUsuari();
                usernames.remove(username);
                jugadors.remove(idJugador);
                return true;
            }
            else {
                return false;
                //throw new Exception("Error al eliminar jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
            }
        }
        else return false;
    }

    /**
     * Obté el Jugador amb id 'idJugador'
     * @param idJugador l'id del Jugador que es vol obtenir
     * @return Retorna el Jugador que te id 'idJugador' si existeix
     * @throws Exception Si no s'ha pogut obtenir el jugador perquè no
     * existeix un jugador amb id 'idJugador'
     *
     */
    //privada pk en principi els demés no la necessiten, pero jo la uso
    public Jugador obtenirJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) return jugadors.get(idJugador);
        else throw new Exception("Error al obtenir el Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    //GETTERS(abans de demanar info cal comprovar que el jugador existeix! )
    //sinó es produirà excepció!
    //
    /**
     * Retorna la puntuació del jugador amb id idJugador
     * @param idJugador l'identificador del Jugador
     * @return la puntuació del Jugador
     * @throws java.lang.Exception
     */
    public int getPuntuacioJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getPuntuacio();
        }
        else throw new Exception("Error al obtenir la puntuacio de Jugador amb id: "+ idJugador + ". Jugador no trobat " + "\n");
    }

    public int getNumeroPartidesJugadesJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getNumeroPartidesJugades();
        }
        else throw new Exception("Error al obtenir el numero de partides jugades de Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public int getNumeroPartidesGuanyadesJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getNumeroPartidesGuanyades();
        }
        else throw new Exception("Error al obtenir el numero de partides guanyades de Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public int getNumeroPartidesPerdudesJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getNumeroPartidesPerdudes();
        }
        else throw new Exception("Error al obtenir el numero de partides perdudes de Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public double getPercentatgePartidesGuanyadesJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getPercentatgePartidesGuanyades();
        }
        else throw new Exception("Error al obtenir percentatge de partidesGuanyades de Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public double getPercentatgePartidesPerdudesJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getPercentatgePartidesPerdudes();
        }
        else throw new Exception("Error al obtenir percentatge de partidesPerdudes de Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public double getPuntuacioMitjana(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getPuntuacioMitjana();
        }
        else throw new Exception("Error al obtenir RelacioPuntuacio_PartidesJugades de Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public String getInfoJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getInfoJugador();
        }
        else throw new Exception("Error al obtenir info del Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public String getInfoTotsJugadors() {
        //aprofitant poder de Java8
        return jugadors.entrySet()
                        .stream()//passem a un stream per poder iterar amb filtres de Java8
                        .map(e -> e.getValue().getInfoJugador()) //mapegem a obtenir la info
                        .collect(Collectors.joining("\n")); //ho juntem tot en un unic string
    }

    // ***************************************************************************************
    // ***********************************************************************************    *
    //realment aquesta funcionalitat es per provar la classe, al joc no hi serà en principi   *
    public void eliminarTotsElsJugadors() {                                                 //*
        jugadors.clear();                                                                   //*
    }                                                                                       //*
    // *********************************************************************************      *
    // *************************************************************************************  *


    public String getUsername(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getNomUsuari();
        }
        else throw new Exception("Error al obtenir nom usuari del Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public String getPasswordUsuari(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            return obtenirJugador(idJugador).getPasswordUsuari();
        }
        else throw new Exception("Error al obtenir password usuari del Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public void setPasswordUsuari(String idJugador, String passwordUsuari) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (passwordUsuari == null ) throw new Exception("Error a setPasswordUsuari: passwordUsuari es null");
        if (registreConteJugador(idJugador)) {
            obtenirJugador(idJugador).setPasswordUsuari(passwordUsuari);
        }
        else throw new Exception("Error al posar password usuari al Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }


    public void guanyaPartidaJugador(String idJugador, int puntuacio) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (puntuacio < 0) throw new Exception("Error al guanyarPartidaJugador: puntuació no pot ser negativa");
        if (registreConteJugador(idJugador)) {
            obtenirJugador(idJugador).guanyaPartida(puntuacio);
        }
        else throw new Exception("Error al guanyar partida el Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }

    public void perdPartidaJugador(String idJugador) throws Exception {
        if (idJugador == null ) throw new Exception("Error: idJugador es null");
        if (registreConteJugador(idJugador)) {
            obtenirJugador(idJugador).perdPartida();
        }
        else throw new Exception("Error al perdre partida del Jugador amb id: " + idJugador + ". Jugador no trobat" + "\n");
    }


    /**************************************************************************************************/
    /***************                                                ***********************************/
    /************** LES SEGUENTS FUNCIONS SÓN USADES NOMÉS ALS TEST ***********************************/
    /***************             -START-                                   *****************************/
    //això és degut a que necessito un format concret per a fer servir la vista del ranking (Object[][]) per tant prefereixo
    //tenir 2 versions de cada funció, la que uso al test i la que uso a l'aplicació definitiva GUI que no pas
    //tenir la ineficiència de fer la conversió posteriorment quan hagi de fer el rànking
    /*************************************************************************************************/


    //retorna una LLista d'Strings on cada string es la info d'un jugador(aplica a tots els ordena)
    //l'arrayList està ordenat pel criteri que s'apliqui en cada cas
    public ArrayList<String> ordenarPerNomPublic() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)//equivalente a .map((j) -> j.getValue()); coge el valor V del map<K,V>;
                                     //java.util.Map.Entry se refiere a una entrada del stream(un elemento)
                .sorted(Comparator.comparing(Jugador::getNomPublic))
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    /************************ RANKING *****************************************/
    public ArrayList<String> ordenarPerPuntuacio() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPuntuacio)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerPartidesJugades() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesJugades) //condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerPartidesGuanyades() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesGuanyades)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerPartidesPerdudes() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesPerdudes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerPercentatgePartidesGuanyades() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPercentatgePartidesGuanyades)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerPercentatgePartidesPerdudes() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPercentatgePartidesPerdudes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerPuntuacioMitjana() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPuntuacioMitjana)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerMaximaRatxaVictories() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getMaximaRatxaVictories)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> ordenarPerMaximaRatxaDerrotes() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getMaximaRatxaDerrotes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugador)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**************************************************************************************************/
    /***************                                                ***********************************/
    /************** LES ANTERIORS FUNCIONS SÓN USADES NOMÉS ALS TEST **********************************/
    /***************               -END-                                 ******************************/

/*------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------------------------------------*/


    //permet convertir ArrayList<String[]> a String[][].
    //És necessari ja que la JTable necessita les files en format String[][]
    //pero jo les tinc en ArrayList<String[]> ja que he usat streams
    private String[][] convertir(ArrayList<String[]> llista) {
        int n = llista.size();
        String[][] ret = new String[n][];
        int i = 0;
        for (String[] array : llista){
            ret[i] = array;
            i++;
        }
        return ret;
    }

    /**************************************************************************************************/
    /***************                                                ***********************************/
    /************** LES SEGUENTS FUNCIONS SÓN USADES NOMÉS A L'APLICACIÓ GUI **************************/
    /***************               -START-                                *****************************/
    /*************************************************************************************************/
    public String[][] ordenarPerNomPublicGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)//equivalente a .map((j) -> j.getValue()); coge el valor V del map<K,V>;
                                     //java.util.Map.Entry se refiere a una entrada del stream(un elemento)
                .sorted(Comparator.comparing(Jugador::getNomPublic))
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));

        return convertir(llista);

    }
    /************************ RANKING *****************************************/
    public String[][] ordenarPerPuntuacioGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPuntuacio)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerPartidesJugadesGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesJugades) //condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerPartidesGuanyadesGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesGuanyades)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerPartidesPerdudesGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesPerdudes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerPercentatgePartidesGuanyadesGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPercentatgePartidesGuanyades)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerPercentatgePartidesPerdudesGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPercentatgePartidesPerdudes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerPuntuacioMitjanaGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPuntuacioMitjana)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerMaximaRatxaVictoriesGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getMaximaRatxaVictories)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }

    public String[][] ordenarPerMaximaRatxaDerrotesGUI() {
        ArrayList<String[]> llista = jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getMaximaRatxaDerrotes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .collect(Collectors.toCollection(ArrayList::new));
        return convertir(llista);
    }
    /**************************************************************************************************/
    /***************                                                              *********************/
    /************** LES ANTERIORS FUNCIONS SÓN USADES NOMÉS A L'ALPICACIÓ GUI    **********************/
    /***************               -END-                                          **********************/

 /*------------------------------------------------------------------------------------------------------------------------------------*/

    /************************ RECORDS *****************************************/
    //(només aquell jugador amb el valor màxim d'un atribut concret)

    public String[] recordPuntuacio() {
         return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPuntuacio)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordPartidesJugades() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesJugades) //condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordPartidesGuanyades() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesGuanyades)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordPartidesPerdudes() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getNumeroPartidesPerdudes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordPercentatgePartidesGuanyades() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPercentatgePartidesGuanyades)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordPercentatgePartidesPerdudes() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPercentatgePartidesPerdudes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordPuntuacioMitjana() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getPuntuacioMitjana)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordMaximaRatxaVictories() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getMaximaRatxaVictories)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public String[] recordMaximaRatxaDerrotes() {
        return jugadors.entrySet()
                .stream()
                .map(Entry::getValue)
                .sorted(Comparator.comparing(Jugador::getMaximaRatxaDerrotes)//condicio comparacio
                                  .reversed() //reversed pk descendent(major puntuacio a dalt)
                                  .thenComparing(Jugador::getNomPublic))//després per nom
                .map(Jugador::getInfoJugadorTaula)
                .findFirst()
                .get();
    }

    public boolean iniciarSessio(String username, String password) throws Exception {
        if (username == null || password == null) return false;
        if (! existeixJugadorRegistrat(username)) return false;
        else {
            Optional<String> idJugador = obtenirIdDeUsername(username);
            if (! registreConteJugador(idJugador.get())) return false;
            //else..
            String password_real = obtenirJugador(idJugador.get()).getPasswordUsuari();
            return password.equals(password_real);
        }
    }

}
