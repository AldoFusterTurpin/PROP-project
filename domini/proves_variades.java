package domini;

import java.util.Arrays;

public class proves_variades {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        RegistreJugadors r = new RegistreJugadors();
        r.afegirJugadorRegistrat("aldo1", "el_pepe", "123");
        r.afegirJugadorRegistrat("aldo2", "programmer", "345");
        r.afegirJugadorRegistrat("aldo_test", "The:best", "");



//      System.out.print("->");
//      System.out.print(r.getInfoTotsJugadors());
//      System.out.print("<-");

//      System.out.print("->");
//      r.ordenarPerNomPublic().forEach(System.out::println);
//      System.out.print("<-");

//      System.out.print("->");
//      r.ordenarPerPuntuacio().forEach(System.out::println);
//      System.out.print("<-");

//      System.out.print("->");
//      r.ordenarPerPartidesJugades().forEach(System.out::println);
//      System.out.print("<-");

        int i = 0;
        String[][] s = r.ordenarPerNomPublicGUI();
        for (String[] a : s) {
            System.out.print("Element" + i+ ":\n");
            for (int k = 0; k < a.length; ++k) {
                System.out.println(a[k]);
            }
            System.out.print("----------------------------------\n");
            ++i;
        }

        vista.VistaOpcions.main(null, null, null);

    }
}
