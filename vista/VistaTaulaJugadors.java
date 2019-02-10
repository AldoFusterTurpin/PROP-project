package vista;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


//aquesta classe és cridada desde VistaEscollirRanking i VistaEscollirRecord ja que
//s'utilitza tant per representar tant el Ranking com els Records ja que els records són la
//1a entrada del que seria el jugador en el Ranking(visualment parlant), això NO vol dir
//que la lógica es calculi de la mateixa manera a la capa de domini i s'ensenyi la 1a fila de la taula, NO és així.
//A la capa de domini es realitza una lògica diferenciada en el cas de Ranking o de Record.
//En el Record és retorna només el jugador que té el record de l'atribut que s'estigui demanant
//mentre que en el Ranking s'ordena tots els jugadors pel criteri seleccionat.
//Però he fet aquesta classe prou flexible perquè serveixi tant pel Ranking com el Record
public class VistaTaulaJugadors {
    private JFrame frame;
    private MyTableModel modelTaula;
    private JTable taula;
    private JScrollPane sp;

    /******************************************************************/
        class MyTableModel extends AbstractTableModel {
            private Object[][] rows;
            private String[] columns;


            public MyTableModel(String rows[][], String columns[]) {
                this.rows = rows;
                this.columns = columns;
            }

            @Override
            public int getColumnCount() {
                return columns.length;
            }

            @Override
            public int getRowCount() {
                return rows.length;
            }

            @Override
            public String getColumnName(int col) {
                return columns[col];
            }

            @Override
            public Object getValueAt(int row, int col) {
                return rows[row][col];
            }

            @Override
            public Class getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }


            @Override
            public boolean isCellEditable(int row, int col) {
                //no volem que les celdes es puguin editar
                return false;
            }
        }
        /******************************************************************/

    //com aquesta classe representa el ranking, el parámetre que té son les files
    //que s'han de mostrar en el ranking, això vindrà determinat per quin ordre
    //del ranking hagi escollit l'usuari(ordenar per puntuació, per nom...)
    //Desde VistaEscollirRanking, es crearà VistaTaulaJugadors depenent de l'opció que hagu
    //escollit l'usuari per ordenar el ranking
    public VistaTaulaJugadors(String rows[][], String columns[], String title) {
        frame = new JFrame();
        modelTaula = new MyTableModel(rows, columns);
        taula = new JTable(modelTaula);
        //taula.setEnabled(true);
        taula.setFillsViewportHeight(true);//perquè taula ocupi tot el viewport

        sp = new JScrollPane(taula);

        frame.getContentPane().add(sp);

        /*************   Per ajustar mida de les columnes, no funciona...*****/
        /*
        TableColumn columna = null;
        for (int j = 0; j < taula.getColumnCount(); j++) {
            columna = taula.getColumnModel().getColumn(j);
            columna.setPreferredWidth(200);
        }
        /*
        /*********************************************************************/
        frame.setTitle(title);
        frame.getContentPane().setSize(new java.awt.Dimension(1500, 500));
        frame.getContentPane().setPreferredSize(new java.awt.Dimension(1500, 500));

        frame.setResizable(true);

        //molt important! S'ha de cirdar pack() abans de 'frame.setLocationRelativeTo(null);'
        //ja que sinó no es centrarà a la pantalla correctament el frame perquè el pack() es el que
        //calcula les dimensions d'on s'està executant la app
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String rows[][], String columns[], String title) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaTaulaJugadors(rows, columns, title);
            }
        });
    }

}
