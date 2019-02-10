package vista;

import domini.Codi;
import domini.CtrlDomini;
import domini.Jugador;
import domini.ModelTaulell;
import domini.Opcions;
import domini.Partida;
import domini.Valoracio;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import persistencia.CtrlPersistencia;

//classes auxiliars





/**
 *

 */
public class VistaPartida extends javax.swing.JFrame {

    /**
     *Model de taulell seleccionat que es representa
     */
    private Partida pt;

    /**
     *Jpanel que representa l'espai ocupat per la representacio del taulell
     */
    private JPanel board;

    /**
     * Amplada del taulell de la partida actual
     */
    private int amplada;

    /**
     * Alcada del taulell de la partida actual
     */
    private int alcada;

    /**
     * ronda actual
     */
    private int ronda;

    /**
     * Valoracio a avaluar que sigui correcta
     */
    private Valoracio valActual;

    /**
     * Indica si hi ha una valoracio a processar
     */
    private boolean hiHaVal = false;

    /**
     * Resposta envidada en el torn actual
     */
    private Codi respostaActual;

    /**
     * Codi secret en cas de que el jugador sigui codemaker
     */
    private Codi codiSecret;

    /**
     * Comprova si s'ha inputejat un codi secret
     */
    private boolean hiHaCodiSecret;

    /**
     * Comprova si s'ha enviat alguna resposta valida en aquest torn
     */
    private boolean hiHaResposta = false;

    /**
     * Llista de tots els panells del taulell, per poder dibuixar les peces
     */
    private HashMap<String,ImagePanel> forats;

    private CtrlDomini ctrldomini;

    //FUNCIONS AUXILIARS
    //TODO TODO TODO FER QUE LA CLASSE NO DEPENGUI EN VISTACREARTAULELL

    /**
    * Funcio auxiliar per crear un CirclePanel a partir de uns valors
    * @param size tamany tant amplada com alçada que prendra el resultat
    * @param color el color que tindra el cercle en el panell
    * @param posX la posicio en X on es dibuixara
    * @param posY la posicio en Y on es dibuixara
    * @param nam nom del panell
    * @return El CirclePanel configurat
    */
    private CirclePanel drawCircle(int size, String color, int posX, int posY, String name){
        CirclePanel aux = new CirclePanel();
        if (color.equals("*")) {
            aux.setForeground(Color.decode("#000000"));
        } else {
            aux.setForeground(Color.decode(color));
        }
        aux.setBackground(aux.getForeground());
        aux.setFocusable(false);
        aux.setName(name);
        aux.setOpaque(true);
        aux.setSize(size,size);
        aux.setLocation(posX,posY);
        return aux;
    }

    /**
     * Funcio auxiliar per crear un ImagePanel a partir de uns valors
     * @param w amplada del panell a crear
     * @param h alçada del panell a crear
     * @param path string que indica la ubicacio de la imatge
     * @param posX la posicio en X on es dibuixara
     * @param posY la posicio en Y on es dibuixara
     * @param nam nom del panell
     * @return El ImagePanel configurat
     */
    private ImagePanel drawImage(int w, int h, String path, int posX, int posY, String name){
        ImagePanel aux = new ImagePanel();
        aux.setImg(path);
        aux.setFocusable(false);
        aux.setName(name);
        aux.setOpaque(true);
        aux.setSize(w,h);
        aux.setLocation(posX,posY);
        return aux;
    }

    /**
     * Actualitza la representacio grafica del taulell
     */
    private void updateBoard(){
        representacioTaulell.removeAll();
        representacioTaulell.repaint();
        representacioTaulell.setLayout(null);

        int numEsp=amplada;
        int panelH= representacioTaulell.getHeight()-12;
        int panelW= representacioTaulell.getWidth()-18;
        int sizeX =Math.min((int) panelW/numEsp,panelW/7);
        int sizeY= (int) panelH/alcada;

        int size=Math.min(sizeX, sizeY);

        board=new JPanel();
        board.setLocation(9+(panelW-numEsp*size)/2,6);
        board.setSize(numEsp*size,panelH);
        board.setOpaque(false);
        board.setLayout(null);
        representacioTaulell.add(board);
        //Dibuixar peces
        ArrayList<ArrayList<String>> peces;
        peces = pt.getEstat().showTaulell();
        for (int i = 0; i < peces.size(); i++) {
            for (int j = 0; j < peces.get(i).size(); j++) {
                String color = peces.get(i).get(j);
                if (!color.equals("*")) {
                    String name = "pos"+String.valueOf(peces.size()-i-1)+String.valueOf(j);
                    ImagePanel hole = forats.get(name);

                    //JOptionPane.showMessageDialog(null, color, "Alert", JOptionPane.ERROR_MESSAGE);
                    CirclePanel circle = drawCircle(32,color,hole.getX(),
                            hole.getY(),"circle"+String.valueOf(i)+String.valueOf(j));
                    //CirclePanel circle = drawCircle(32,color,32*j,32*i,"circle"+String.valueOf(i)+String.valueOf(j));

                    board.add(circle);
                }
            }
        }
        representacioTaulell.repaint();


        //Dibuixar Holes
        String path="/vista/hole2.png";
         for(int i=0; i<alcada; ++i){
            for(int j=0; j<amplada; ++j){
                String name = "pos"+ String.valueOf(i) + String.valueOf(j);


                ImagePanel aux = drawImage(size,size,path,j*size,panelH-(i+1)*size,name); //he canviat i i j, comprovar que no passa res
                board.add(aux);
                if (aux == null) System.err.println("aux null");
                if (name == null) System.err.println("name null");
                forats.put(name, aux);
            }
        }

    }

        private void iniBoard(){
        representacioTaulell.removeAll();
        representacioTaulell.repaint();
        representacioTaulell.setLayout(null);

        int numEsp=amplada;
        int panelH= representacioTaulell.getHeight()-12;
        int panelW= representacioTaulell.getWidth()-18;
        int sizeX =Math.min((int) panelW/numEsp,panelW/7);
        int sizeY= (int) panelH/alcada;

        int size=Math.min(sizeX, sizeY);

        board=new JPanel();
        board.setLocation(9+(panelW-numEsp*size)/2,6);
        board.setSize(numEsp*size,panelH);
        board.setOpaque(false);
        board.setLayout(null);
        representacioTaulell.add(board);

        //Dibuixar Holes
        String path="/vista/hole2.png";
         for(int i=0; i<alcada; ++i){
            for(int j=0; j<amplada; ++j){
                String name = "pos"+ String.valueOf(i) + String.valueOf(j);


                ImagePanel aux = drawImage(size,size,path,j*size,panelH-(i+1)*size,name); //he canviat i i j, comprovar que no passa res
                board.add(aux);
                if (aux == null) System.err.println("aux null");
                if (name == null) System.err.println("name null");
                forats.put(name, aux);
            }
        }
    }


    //FI FUNCIONS AUXILIARS
 	public void slotPartida(int slot){
            CtrlPersistencia persist=new CtrlPersistencia();
            try {
                persist.guardarPartida(pt,pt.getHumanPlayer().getNomUsuari(),slot);
				dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            }
	}


    private void dibuixarAvaluacio (Valoracio val, int fila) {
        JLabel aux = new JLabel();
        JLabel aux2 = new JLabel();
        int blackDots = 0;
        int whiteDots = 0;
        for (Integer i : val.getVal()) {
            if (i == 2) blackDots++;
            else if (i == 1) whiteDots++;
        }
        ImagePanel hole = forats.get("pos"+String.valueOf(fila)+String.valueOf(0));

        aux.setText("Totals: " + String.valueOf(blackDots));
        aux2.setText("Parcials: " + String.valueOf(whiteDots));
        int alcadaVal = valoracionsPanell.getHeight() - (fila+1)*hole.getHeight();
        aux.setBounds(0, alcadaVal, 60, 15);
        aux2.setBounds(0, alcadaVal+15, 60, 15);

        aux.setFont(new java.awt.Font("Tahoma",0,9));
        aux2.setFont(new java.awt.Font("Tahoma",0,9));
        valoracionsPanell.add(aux);
        valoracionsPanell.add(aux2);
        valoracionsPanell.repaint();
    }

    public void startGame() {
        if (pt.getCodemakerIsIA()) {
            pt.initPartida();
            codiSecretLabel.setVisible(false);
        } else {
            VistaResposta.main(null);
            VistaResposta.setParent(this);
            VistaResposta.setSecretCode();

            //dibuixar codi secret per algun lloc TODO
        }
    }

    public void startGame2() {
        pt.initCodiSecret(codiSecret);
        if (!pt.initPartida()) {
            JOptionPane.showMessageDialog(null, "Secret code not accepted, try again", "Alert", JOptionPane.ERROR_MESSAGE);
            VistaResposta.main(null);
            VistaResposta.setParent(this);
            VistaResposta.setSecretCode();
        } else {
            dibuixarCodiSecret();
            updateGame();
        }
    }

    private void dibuixarCodiSecret() {
        ArrayList<String> colors;
        if (codiSecret != null) {
            colors = codiSecret.getCombinacio();
        }
        else colors = pt.getCodiSecret().getCombinacio();
        int offset = 0;
        for (String color : colors) {
            JPanel aux = new JPanel();
            if (color.equals("*")) {
                aux.setForeground(Color.decode("#000000"));
            } else {
                aux.setForeground(Color.decode(color));
            }
            aux.setBackground(aux.getForeground());
            aux.setFocusable(false);
            aux.setName(color);
            aux.setOpaque(true);
            aux.setSize(32,32);
            aux.setLocation(offset,0);
            aux.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            offset += aux.getWidth();
            panelCodiSecret.add(aux);
        }
        panelCodiSecret.repaint();
    }

    /**
     * Actualitza el estat del joc
     */

    public void updateGame() {
        //torn codebreaker
        if (pt.getTornCodebreaker()) {
            boolean endGame = false; //Check if game has ended
            boolean playTurn = true; //Know if there's an answer to submit
            if (!pt.getCodebreakerIsIA()) {
                if (hiHaResposta) {
                    pt.setCodiIntent(respostaActual);
                    hiHaResposta = false;
                } else playTurn = false;
            }
            if (playTurn) {
                try {
                    endGame = pt.tornCodebreaker();
                    updateBoard();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
                }
                if (endGame) {
                    ensenyarCodiSecret();
                    try {
                        if(pt.isGuanyador()) {
                            JOptionPane.showMessageDialog(null,
                                "Codi secret encertat! Felicitats, Codebreaker!", "Felicitats!", JOptionPane.INFORMATION_MESSAGE);
                            //pt.getCodebreaker().guanyaPartida(pt.getPuntuacio()); ANTERIOR NO ANAVA
                            ctrldomini.guanyaPartidaJugador(pt.getCodebreaker().getNomPublic() ,pt.getPuntuacio());
                            pt.getCodemaker().perdPartida();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                "Totes les files exhaurides. Felicitats, Codemaker!", "Felicitats!", JOptionPane.INFORMATION_MESSAGE);
                            pt.getCodemaker().guanyaPartida(pt.getPuntuacio());
                            pt.getCodebreaker().perdPartida();
                        }
                        ctrldomini.acabarPartida();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        //torn codemaker
        else if (!pt.getTornCodebreaker()) {
            boolean playTurn = true;
            if (!pt.getCodemakerIsIA()) {
                if (hiHaVal) {
                    pt.setValoracio(valActual);
                    hiHaVal = false;
                } else playTurn = false;
            }
            if (playTurn) {
                boolean valAccepted = false;
                try {
                    valAccepted = pt.tornCodemaker();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
                }
                if (valAccepted) {
                    dibuixarAvaluacio(pt.getValoracio(pt.getRonda()-1),pt.getRonda()-1);
                }
            }
        }

        rondaCount.setText("Ronda: " + String.valueOf(pt.getRonda() + 1));
        enableButtons();
    }

    /**
     * Activa i desactiva el boto correcte per la fase del torn
     */
    private void enableButtons() {
        if (pt.isPartidaOver()) {
            respostaButton.setEnabled(false);
            avaluarButton.setEnabled(false);
        }
        else if(pt.getTornCodebreaker()) {
            respostaButton.setEnabled(true);
            avaluarButton.setEnabled(false);
        } else {
            respostaButton.setEnabled(false);
            avaluarButton.setEnabled(true);
        }
    }

    private void ensenyarCodiSecret() {
        if (!pt.getCodemakerIsIA()) {
            codiSecretLabel.setEnabled(true);
            dibuixarCodiSecret();
        }
    }

    /**
     * Creates new form VistaPartida
     */
    public VistaPartida(Partida p, CtrlDomini ctrldomini) {
        initComponents();
        forats = new HashMap<String,ImagePanel>();
        pt = p;
        amplada = pt.getAmplada();
        alcada = pt.getAlcada();
        ronda = pt.getRonda();

        this.ctrldomini = ctrldomini;


        if (ronda == 0) startGame();
        iniBoard();
        updateBoard();
        enableButtons();
        if(pt.getTornCodebreaker() && !pt.getCodebreakerIsIA() && ronda == 0) updateGame();
        if(!pt.getCodemakerIsIA() && ronda != 0) dibuixarCodiSecret();
    }

    public int getAmplada() {
        //JOptionPane.showMessageDialog(null, "amplada es "+String.valueOf(amplada), "Alert", JOptionPane.ERROR_MESSAGE);
        return amplada;
    }

    public ArrayList<String> getColors() {
        return pt.getAllowedColors();
    }

    public void setValoracioActual(Valoracio val) {
        this.valActual = val;
        this.hiHaVal = true;
        updateGame();
    }

    public void setRespostaActual (Codi resposta) {
        this.respostaActual = resposta;
        this.hiHaResposta = true;
    }

    public void setCodiSecret (Codi input) {
        this.codiSecret = input;
        this.hiHaCodiSecret = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rondaCount = new javax.swing.JLabel();
        representacioTaulell = new javax.swing.JPanel();
        respostaButton = new javax.swing.JButton();
        avaluarButton = new javax.swing.JButton();
        guardarButton = new javax.swing.JButton();
        codiSecretLabel = new javax.swing.JLabel();
        panelCodiSecret = new javax.swing.JPanel();
        valoracionsPanell = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 700));
        setMinimumSize(new java.awt.Dimension(800, 700));
        getContentPane().setLayout(null);

        rondaCount.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        rondaCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rondaCount.setText("Ronda: 1");
        getContentPane().add(rondaCount);
        rondaCount.setBounds(630, 100, 170, 70);

        representacioTaulell.setBackground(new java.awt.Color(99, 97, 100));
        representacioTaulell.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 47, 60), 5));
        representacioTaulell.setMaximumSize(new java.awt.Dimension(390, 450));
        representacioTaulell.setPreferredSize(new java.awt.Dimension(388, 451));

        javax.swing.GroupLayout representacioTaulellLayout = new javax.swing.GroupLayout(representacioTaulell);
        representacioTaulell.setLayout(representacioTaulellLayout);
        representacioTaulellLayout.setHorizontalGroup(
            representacioTaulellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        representacioTaulellLayout.setVerticalGroup(
            representacioTaulellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        getContentPane().add(representacioTaulell);
        representacioTaulell.setBounds(220, 70, 388, 530);

        respostaButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        respostaButton.setText("Triar Resposta");
        respostaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                respostaButtonActionPerformed(evt);
            }
        });
        getContentPane().add(respostaButton);
        respostaButton.setBounds(640, 610, 140, 50);

        avaluarButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        avaluarButton.setText("Avaluar Resposta");
        avaluarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avaluarButtonActionPerformed(evt);
            }
        });
        getContentPane().add(avaluarButton);
        avaluarButton.setBounds(640, 540, 140, 50);

        guardarButton.setText("Guardar Partida");
        guardarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarButtonActionPerformed(evt);
            }
        });
        getContentPane().add(guardarButton);
        guardarButton.setBounds(650, 20, 120, 40);

        codiSecretLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        codiSecretLabel.setText("Codi Secret:");
        getContentPane().add(codiSecretLabel);
        codiSecretLabel.setBounds(100, 610, 160, 50);

        panelCodiSecret.setMaximumSize(new java.awt.Dimension(320, 32));
        panelCodiSecret.setMinimumSize(new java.awt.Dimension(320, 32));
        panelCodiSecret.setPreferredSize(new java.awt.Dimension(320, 32));

        javax.swing.GroupLayout panelCodiSecretLayout = new javax.swing.GroupLayout(panelCodiSecret);
        panelCodiSecret.setLayout(panelCodiSecretLayout);
        panelCodiSecretLayout.setHorizontalGroup(
            panelCodiSecretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        panelCodiSecretLayout.setVerticalGroup(
            panelCodiSecretLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        getContentPane().add(panelCodiSecret);
        panelCodiSecret.setBounds(270, 620, 320, 32);

        javax.swing.GroupLayout valoracionsPanellLayout = new javax.swing.GroupLayout(valoracionsPanell);
        valoracionsPanell.setLayout(valoracionsPanellLayout);
        valoracionsPanellLayout.setHorizontalGroup(
            valoracionsPanellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        valoracionsPanellLayout.setVerticalGroup(
            valoracionsPanellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        getContentPane().add(valoracionsPanell);
        valoracionsPanell.setBounds(90, 70, 130, 530);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void respostaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respostaButtonActionPerformed
        if (!pt.getCodebreakerIsIA()) {
            VistaResposta.main(null);
            VistaResposta.setParent(this);
        } else {
            updateGame();
        }
    }//GEN-LAST:event_respostaButtonActionPerformed

    private void avaluarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avaluarButtonActionPerformed
        if (!pt.getCodemakerIsIA()) {
            VistaValoracio.main(null);
            VistaValoracio.setParent(this);
        } else {
            updateGame();
        }
    }//GEN-LAST:event_avaluarButtonActionPerformed

    private void guardarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarButtonActionPerformed
        VistaGuardarPartida.main(null);
        VistaGuardarPartida.setParent(this);
		VistaGuardarPartida.setEstaGuardant(true);
    }//GEN-LAST:event_guardarButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(Partida p, CtrlDomini ctrldomini) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPartida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaPartida(p, ctrldomini).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton avaluarButton;
    private javax.swing.JLabel codiSecretLabel;
    private javax.swing.JButton guardarButton;
    private javax.swing.JPanel panelCodiSecret;
    private javax.swing.JPanel representacioTaulell;
    private javax.swing.JButton respostaButton;
    private javax.swing.JLabel rondaCount;
    private javax.swing.JPanel valoracionsPanell;
    // End of variables declaration//GEN-END:variables
}
