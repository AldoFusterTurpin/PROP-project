package persistencia;

import domini.Partida;
import domini.RegistreJugadors;
import domini.RegistreTaulells;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.Desktop;

public class CtrlPersistencia {
   /**
    * Creadora de la calsse
    */
   public CtrlPersistencia() {
   }
   /**
    * Indica si existeix un registre de taulells guardat;
    * @return true si existeix false altrament
    */
   public boolean existsSavedRegistreTaulells(){
       String temp = "saved/RegistreTaulells.sav";
       return new File(temp).exists();
   }
   /**
    * Indica si existeix un registre de jugadors guardat;
    * @return true si existeix false altrament
    */
   public boolean existsSavedRegistreJugadors(){
       String temp = "saved/RegistreJugadors.sav";
       return new File(temp).exists();
   }
   /**
    * Guardar el registre de taulells al disc dur
    * @param rt el registre a guardar
    */
   public void guardarRegistreTaulells(RegistreTaulells rt){
       try {
           crearDirectori("saved/");
           FileOutputStream f = new FileOutputStream(new File("saved/RegistreTaulells.sav"));
           ObjectOutputStream o = new ObjectOutputStream(f);
           // Write objects to file
           o.writeObject(rt);
           o.close();
       }
       catch(IOException e){
            System.err.println("Error al generar l'arxiu de guardat");
       }
   }

   /**
    * Carrega un registre de taulells des del disc dur
    * @return el registre carregat
    */
   public RegistreTaulells carregarRegistreTaulells() {
       try{
            RegistreTaulells  rt=new RegistreTaulells();
            FileInputStream fi = new FileInputStream(new File("saved/RegistreTaulells.sav"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            // Read objects
            rt = (RegistreTaulells) oi.readObject();
            oi.close();
            fi.close();
            return rt;
       }
       catch(FileNotFoundException fn){
           System.err.println("No s'ha trobat l'arxiu guardat");
       }
       catch(IOException ie){
           System.err.println("Error en la lectura del fitxer");
       }
       catch(ClassNotFoundException ie){
           System.err.println("No es troba la classe, l'arxiu de joc pot estar danyat.");
       } catch (Exception ex) {
           System.err.println(ex.getMessage());
       }
       return null;
   }

   /**
    * Guardar el registre de jugadors al disc dur
    * @param rj el registre a guardar
    */
   public void guardarRegistreJugadors(RegistreJugadors rj){
       try {
           crearDirectori("saved/");
           FileOutputStream f = new FileOutputStream(new File("saved/RegistreJugadors.sav"));
           ObjectOutputStream o = new ObjectOutputStream(f);
           // Write objects to file
           o.writeObject(rj);
           o.close();
       }
       catch(IOException e){
            System.err.println("Error al generar l'arxiu de guardat");
       }
   }

   /**
    * Carrega un registre de jugadors des del disc dur
    * @return el registre carregat
    */
   public RegistreJugadors carregarRegistreJugadors(){
       RegistreJugadors rj=new RegistreJugadors();
       try{
            FileInputStream fi = new FileInputStream(new File("saved/RegistreJugadors.sav"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            // Read objects
            rj = (RegistreJugadors) oi.readObject();
            oi.close();
            fi.close();
            return rj;
       }
       catch(FileNotFoundException fn){
           System.err.println("No s'ha trobat l'arxiu guardat");
       }
       catch(IOException ie){
           System.err.println("Error en la lectura del fitxer");
       }
       catch(ClassNotFoundException ie){
           System.err.println("No es troba la classe, l'arxiu de joc pot estar danyat.");
       }
       return rj;
   }

   /**
    * Guardar una partida al disc dur
    * @param p la partida a guardar
    * @param nomPlayer nom del propietari de la partida
    * @param slot num de partida guardada
    */
   public void guardarPartida(Partida p, String nomPlayer, int slot){
       try {
           crearDirectori("saved/"+nomPlayer+"/");
           FileOutputStream f = new FileOutputStream(new File("saved/"+nomPlayer+"/"+slot+".sav"));
           ObjectOutputStream o = new ObjectOutputStream(f);
           // Write objects to file
           o.writeObject(p);
           o.close();
       }
       catch(IOException e){
            System.err.println("Error al generar l'arxiu de guardat");
       }
   }

   /**
    * Carrega un registre de jugadors des del disc dur
    * @param nomPlayer  nom del propietari de la partida
    * @param slot num de partida guardada
    * @return el registre carregat
    */
   public Partida carregarPartida(String nomPlayer, int slot){
       try{
            FileInputStream fi = new FileInputStream(new File("saved/"+nomPlayer+"/"+slot+".sav"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            // Read objects
            Partida p = (Partida) oi.readObject();
            oi.close();
            fi.close();
            return p;
       }
       catch(FileNotFoundException fn){
           System.err.println("No s'ha trobat l'arxiu guardat");
       }
       catch(IOException ie){
           System.err.println("Error en la lectura del fitxer");
       }
       catch(ClassNotFoundException ie){
           System.err.println("No es troba la classe, l'arxiu de joc pot estar danyat.");
       }
       return null;
   }

   /**
    * Eliminar una partida d'un jugador
    * @param nomPlayer nom del propietari de la partida
    * @param slot numero de partida guardada
    */
   public void eliminarPartida(String nomPlayer, int slot){
       try {
           String file = "saved/"+nomPlayer+"/"+slot+".sav";
           Path p= Paths.get(file);
           Files.delete(p);
       } catch (IOException ex) {
           System.err.println("Error al esborrar el fitxer. "+ex.getMessage());
       }
    }
   /**
    * Eliminar un jugador
    * @param nomPlayer nom del jugador a eliminar
    */
   public void eliminarJugador(String nomPlayer){
       try {
		   ArrayList<String> l= llistaSavedGames(nomPlayer);
		   for(String s : l) eliminarPartida("test",Integer.parseInt(s));
           String file = "saved/"+nomPlayer;
           Path p= Paths.get(file);
           Files.delete(p);
       } catch (IOException ex) {
           System.err.println("Error al esborrar el fitxer. "+ex.getMessage());
       }
    }
   /**
    * Obte la llista de partides guardades del jugador
    * @param nomPlayer nom del jugador del que es volen obtenir les partides guardades
    * @return llista de les partides guardades
    */
   public ArrayList<String> llistaSavedGames(String nomPlayer){
       File dir = new File("saved",nomPlayer);
       File[] list = dir.listFiles();
       ArrayList<String> ar=new ArrayList<>(0);
       for(File f : list){
           String s= f.getName();
           if(s.endsWith(".sav")) ar.add(s.replace(".sav", ""));
       }
       return ar;
   }
  public void mostrarManual(){
  	if (Desktop.isDesktopSupported()) {
  	   try {
  	      File myFile = new File("manual.pdf");
  	         Desktop.getDesktop().open(myFile);
  	     } catch (IOException ex) {
  	      // no application registered for PDFs
			 System.err.println("Error al obrir el manual: "+ex.getMessage());
  	  }
  	}
  }

   /**
    * crear un directori, que pot contenir subdirectoris
    * @param nom path dels directoris a crear
    */
   private void crearDirectori(String nom){
       new File(nom).mkdirs();
   }
}
