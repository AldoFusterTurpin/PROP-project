package vista;

import domini.Codi;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class VistaResposta extends javax.swing.JDialog {

    /**
     * VistaPartida que ha creat aquesta vista
     */
    private static VistaPartida pare;

    /**
     * ArrayList de strings emmagatzemant la resposta
     */
    private ArrayList<String> resposta;


    /**
     * Tamany botons en pixels
     */
    private final int tamanyBotons = 16;

    private static boolean settingSecretCode = false;




    private JButton drawColorButton(int size, int x, int y, String color) {
        JButton aux = new JButton();
        //tenir en compte colors transparetns TODO
        if (color.equals("*")) {
            aux.setForeground(Color.decode("#000000"));
        } else {
            aux.setForeground(Color.decode(color));
        }
        aux.setName(color);
        aux.setOpaque(true);
        aux.setSize(size,size);
        aux.setLocation(x,y);
        return aux;
    }

    /**
     * VistaPartida que ha creat aquesta vista
     * @param v VistaPartida pare
     */
    public static void setParent(VistaPartida v) {
        pare = v;
    }

    public static void setSecretCode() {
        settingSecretCode = true;

    }



    private void actualitzarResposta() {
        respostaPanel.removeAll();
        respostaPanel.repaint();

        int offset = 0;
        for (String color : resposta) {
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
            respostaPanel.add(aux);
        }
        respostaPanel.repaint();
    }

    private void drawColorButtons() {
        int offset = 0;

        ActionListener listener = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorPaletteActionPerformed(evt);
            }
        };

        for (String color : pare.getColors()) {
            if (!color.equals("*")){
                JButton aux = drawColorButton(tamanyBotons, offset, 0,color);
                aux.setBackground(Color.decode(color));
                aux.setSize(32, 32);
                botonsPanel.add(aux);
                offset += aux.getWidth();
                aux.addActionListener(listener);
            } else {
                JButton aux = drawColorButton(tamanyBotons, offset, 0,"*");
                aux.setBackground(Color.decode("#000000"));
                aux.setSize(32, 32);
                botonsPanel.add(aux);
                offset += aux.getWidth();
                aux.addActionListener(listener);
            }

        }
    }

    /**
     * Creates new form VistaResposta
     */
    public VistaResposta(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.resposta = new ArrayList<String>();
        //for (int i = 0; i < 4; i++) resposta.add("#%02x%02x%02x");

        drawColorButtons();
    }

    //Afegeix a la resposta actual el color del boto presionat
    private void colorPaletteActionPerformed(java.awt.event.ActionEvent evt) {
        if (resposta.size() < pare.getAmplada()) {
            resposta.add(((JComponent) evt.getSource()).getName());
            actualitzarResposta();
        }  else JOptionPane.showMessageDialog(null, "The answer is too long", "Alert", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titolLabel = new javax.swing.JLabel();
        buttonExit = new javax.swing.JButton();
        buttonClear = new javax.swing.JButton();
        botonsPanel = new javax.swing.JPanel();
        respostaPanel = new javax.swing.JPanel();
        buttonSubmit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(500, 364));
        setMinimumSize(new java.awt.Dimension(500, 364));
        setPreferredSize(new java.awt.Dimension(500, 364));
        setResizable(false);
        getContentPane().setLayout(null);

        titolLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        titolLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titolLabel.setText("Seleccionar Codi");
        getContentPane().add(titolLabel);
        titolLabel.setBounds(130, 20, 253, 60);

        buttonExit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonExit.setText("Sortir");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });
        getContentPane().add(buttonExit);
        buttonExit.setBounds(40, 270, 110, 50);

        buttonClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonClear.setText("Esborrar");
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearActionPerformed(evt);
            }
        });
        getContentPane().add(buttonClear);
        buttonClear.setBounds(200, 270, 110, 50);

        botonsPanel.setLayout(null);
        getContentPane().add(botonsPanel);
        botonsPanel.setBounds(40, 90, 380, 60);

        respostaPanel.setLayout(null);
        getContentPane().add(respostaPanel);
        respostaPanel.setBounds(40, 180, 380, 60);

        buttonSubmit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonSubmit.setText("Confirmar");
        buttonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(buttonSubmit);
        buttonSubmit.setBounds(350, 270, 110, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        respostaPanel.removeAll();
        resposta.clear();
        respostaPanel.repaint();
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSubmitActionPerformed
        if (resposta.size() == pare.getAmplada()) {
            Codi aux = new Codi(pare.getAmplada());
            aux.setCombinacio(resposta);
            if (!settingSecretCode) {
                pare.setRespostaActual(aux);
                pare.updateGame();
                dispose();
            } else {
                pare.setCodiSecret(aux);
                pare.startGame2();
                dispose();
            }
        } else JOptionPane.showMessageDialog(null, "The answer is too short", "Alert", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_buttonSubmitActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        if (settingSecretCode) {
            JOptionPane.showMessageDialog(null, "S'ha d'introduir un codi secret", "Alert", JOptionPane.ERROR_MESSAGE);
        } else {
            dispose();
        }
    }//GEN-LAST:event_buttonExitActionPerformed




    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(VistaResposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaResposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaResposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaResposta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VistaResposta dialog = new VistaResposta(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel botonsPanel;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSubmit;
    private javax.swing.JPanel respostaPanel;
    private javax.swing.JLabel titolLabel;
    // End of variables declaration//GEN-END:variables
}