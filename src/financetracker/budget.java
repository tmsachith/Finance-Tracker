/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financetracker;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;


/**
 *
 * @author DELL
 */
public class budget extends javax.swing.JFrame {

    /**
     * Creates new form open
     */
    String acc_no = "";
    boolean have = false;
    dashnew1 dashboard; // Add a field to hold the reference to dashnew1

    public budget(String accno, dashnew1 d) { // Update the constructor
        acc_no = accno;
        dashboard = d; // Store the reference
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        y = new javax.swing.JComboBox<>();
        m = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        am = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(null);
        jPanel1.setPreferredSize(new java.awt.Dimension(487, 694));
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Finance_Tracker/open2.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(50, 20, 390, 380);

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jButton1.setText("Set");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(80, 590, 290, 34);

        y.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2024", "2025", "2026" }));
        jPanel1.add(y);
        y.setBounds(80, 500, 90, 24);

        m.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" }));
        jPanel1.add(m);
        m.setBounds(180, 500, 100, 24);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel6.setText("Budget Amount :");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(80, 546, 120, 18);
        jPanel1.add(am);
        am.setBounds(210, 540, 160, 30);

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(300, 500, 69, 25);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 25)); // NOI18N
        jLabel2.setText("SET BUDGET");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(150, 440, 170, 33);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String year = y.getSelectedItem().toString();
        String month = m.getSelectedItem().toString();
        double amount = Double.parseDouble(am.getText());
        String id = year+month+acc_no;
        if(have)
        {
            databaseconnection.updateBudget(id, amount);
        }
        else
        {
            databaseconnection.setBudget(id, acc_no, amount, year, month, 0, 0);
        }
        
        
        if (dashboard != null) {
            dashboard.budgett();
        }
        
        this.dispose();
            
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        String yr = y.getSelectedItem().toString();
        String mn = m.getSelectedItem().toString();
        
        if(databaseconnection.budgetcheck(yr+mn+acc_no))
        {
            have = true;
            am.setText(Double.toString(databaseconnection.getBudget(yr+mn+acc_no)));
            jButton1.setText("Update");
        }
        else
        {
            have = false;
            am.setText("");
            jButton1.setText("Set");
            JOptionPane.showMessageDialog(this, "Please set a budget.", "Alert", JOptionPane.DEFAULT_OPTION);

        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(budget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(budget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(budget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(budget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
        String ac="";
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new budget(ac, null).setVisible(true); // Pass null if no dashnew1 instance
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField am;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> m;
    private javax.swing.JComboBox<String> y;
    // End of variables declaration//GEN-END:variables
}