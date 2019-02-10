
package vista;

import domini.Valoracio;
import javax.swing.JOptionPane;

public class VistaValoracio extends javax.swing.JDialog {

    /**
     * VistaPartida que ha creat aquesta vista
     */
    private static VistaPartida pare;


    public static void setParent(VistaPartida v) {
        pare = v;
    }


    private void returnResults() {
        boolean correctInput = true;
        int blackDots, whiteDots;
        blackDots = whiteDots = 0;
        try {
            blackDots = Integer.parseInt(blackDotField.getText());
            whiteDots = Integer.parseInt(whiteDotField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "There aren't only numerical digits on the input fields", "Alert", JOptionPane.ERROR_MESSAGE);
            correctInput = false;
        }
        int total = blackDots + whiteDots;
        int amplada = pare.getAmplada();
        if (amplada < total) {
            JOptionPane.showMessageDialog(null, "This avaluation is too big for this board", "Alert", JOptionPane.ERROR_MESSAGE);
            correctInput = false;
        }
        if (correctInput){
            Valoracio val = new Valoracio(amplada);
            try {
                for (int i = 0; i < blackDots; i++) val.afegirPunt(2);
                for (int i = 0; i < whiteDots; i++) val.afegirPunt(1);
                for (int i = total; i < amplada; i++) val.afegirPunt(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            }
            pare.setValoracioActual(val);
        }
    }


    /**
     * Creates new form VistaValoracio
     */
    public VistaValoracio(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();

    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blackDotField = new javax.swing.JTextField();
        whiteDotField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        buttonConfirm = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        buttonExit = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(335, 300));
        setMinimumSize(new java.awt.Dimension(335, 300));
        setPreferredSize(new java.awt.Dimension(335, 300));
        getContentPane().setLayout(null);

        blackDotField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        blackDotField.setText("0");
        blackDotField.setMaximumSize(new java.awt.Dimension(50, 30));
        blackDotField.setMinimumSize(new java.awt.Dimension(50, 30));
        blackDotField.setPreferredSize(new java.awt.Dimension(50, 30));
        getContentPane().add(blackDotField);
        blackDotField.setBounds(200, 90, 50, 30);

        whiteDotField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        whiteDotField.setText("0");
        whiteDotField.setMaximumSize(new java.awt.Dimension(50, 30));
        whiteDotField.setMinimumSize(new java.awt.Dimension(50, 30));
        whiteDotField.setPreferredSize(new java.awt.Dimension(50, 30));
        getContentPane().add(whiteDotField);
        whiteDotField.setBounds(200, 140, 50, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/whiteDot.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(270, 130, 30, 50);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Encerts Totals:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 80, 180, 40);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Encerts Parcials:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 130, 190, 50);

        buttonConfirm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonConfirm.setText("Confirmar");
        buttonConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConfirmActionPerformed(evt);
            }
        });
        getContentPane().add(buttonConfirm);
        buttonConfirm.setBounds(180, 190, 120, 40);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Valoració");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(70, 10, 180, 40);

        buttonExit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonExit.setText("Sortir");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });
        getContentPane().add(buttonExit);
        buttonExit.setBounds(40, 190, 120, 40);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/blackDot.png"))); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(270, 80, 30, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConfirmActionPerformed
        returnResults();
        dispose();
    }//GEN-LAST:event_buttonConfirmActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        dispose();
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
            java.util.logging.Logger.getLogger(VistaValoracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaValoracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaValoracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaValoracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VistaValoracio dialog = new VistaValoracio(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField blackDotField;
    private javax.swing.JButton buttonConfirm;
    private javax.swing.JButton buttonExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField whiteDotField;
    // End of variables declaration//GEN-END:variables
}
