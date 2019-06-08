/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatki;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Tomek
 */
public class RezerwacjeSzukaj extends javax.swing.JFrame {
     private EntityManagerFactory emf;
     DefaultListModel<kino.Rezerwacja> seansListModel;
     DefaultListModel<kino.Rezerwacja> rezerwacjaListModel;
      
     
      
    /**
     * Creates new form Rezerwacje
     */
    public RezerwacjeSzukaj() {
        initComponents();
        rezerwacjaListModel = new DefaultListModel<>();
        rezerwacjeList.setModel(rezerwacjaListModel);
        
    }
     private EntityManager getEntityManager() {
            if (emf==null) emf=Persistence.createEntityManagerFactory("kinoPU2");
            return emf.createEntityManager();
     }
      private void getRezerwacje()
    {
        String Imie= ImiejTextField.getText();
        String Nazwisko= NazwiskojTextField.getText();
        String Tytul= FilmjTextField.getText();
        
        EntityManager em=getEntityManager();
        TypedQuery<kino.Rezerwacja> q;
        rezerwacjaListModel.clear();
        try {
            q = em.createNamedQuery("Rezerwacja.findByKlientFilm", kino.Rezerwacja.class);
            
            q.setParameter("tytul","%"+Tytul+"%");
            q.setParameter("imie",Imie+"%");
            q.setParameter("nazwisko",Nazwisko+"%");
            
            for(kino.Rezerwacja rezerwacja : q.getResultList())
            {
                rezerwacjaListModel.addElement(rezerwacja);
               
            }
        }
        catch(PersistenceException ex)
        {
            Logger.getLogger(RezerwacjeSzukaj.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(null,"Błąd połączenia z bazą danych");
        }
        finally {
            em.close();
        }
    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImiejLabel = new javax.swing.JLabel();
        NazwiskojLabel = new javax.swing.JLabel();
        FilmjLabel = new javax.swing.JLabel();
        ImiejTextField = new javax.swing.JTextField();
        NazwiskojTextField = new javax.swing.JTextField();
        WyszukajjButton = new javax.swing.JButton();
        PrzejdzjButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        rezerwacjeList = new javax.swing.JList<>();
        FilmjTextField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        zamknijMItem = new javax.swing.JMenuItem();
        zamknijOknoMItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ImiejLabel.setText("Imię");

        NazwiskojLabel.setText("Nazwisko");

        FilmjLabel.setText("Film");

        WyszukajjButton.setText("Wyszukaj");
        WyszukajjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WyszukajjButtonActionPerformed(evt);
            }
        });

        PrzejdzjButton.setText("Przejdz do seansu");
        PrzejdzjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrzejdzjButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(rezerwacjeList);

        FilmjTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilmjTextFieldActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        zamknijMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        zamknijMItem.setText("Zamknij Program");
        zamknijMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zamknijMItemActionPerformed(evt);
            }
        });
        fileMenu.add(zamknijMItem);

        zamknijOknoMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        zamknijOknoMItem.setText("Zamknij Okno");
        zamknijOknoMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zamknijOknoMItemActionPerformed(evt);
            }
        });
        fileMenu.add(zamknijOknoMItem);

        jMenuBar1.add(fileMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(WyszukajjButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addComponent(PrzejdzjButton)
                .addGap(43, 43, 43))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ImiejLabel)
                        .addGap(34, 34, 34)
                        .addComponent(ImiejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(FilmjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NazwiskojLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NazwiskojTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(FilmjTextField))))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ImiejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ImiejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NazwiskojTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NazwiskojLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FilmjLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FilmjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(WyszukajjButton)
                    .addComponent(PrzejdzjButton))
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void WyszukajjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WyszukajjButtonActionPerformed
        // TODO add your handling code here:
        getRezerwacje();
    }//GEN-LAST:event_WyszukajjButtonActionPerformed

    private void PrzejdzjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrzejdzjButtonActionPerformed
        // TODO add your handling code here:
         EntityManager em = getEntityManager();
        kino.Rezerwacja rezer = rezerwacjeList.getSelectedValue();
        
        new Rezerwacje(rezer).setVisible(true);
        
    }//GEN-LAST:event_PrzejdzjButtonActionPerformed

    private void FilmjTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilmjTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FilmjTextFieldActionPerformed

    private void zamknijMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zamknijMItemActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_zamknijMItemActionPerformed

    private void zamknijOknoMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zamknijOknoMItemActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_zamknijOknoMItemActionPerformed

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
            java.util.logging.Logger.getLogger(RezerwacjeSzukaj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RezerwacjeSzukaj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RezerwacjeSzukaj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RezerwacjeSzukaj.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RezerwacjeSzukaj().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FilmjLabel;
    private javax.swing.JTextField FilmjTextField;
    private javax.swing.JLabel ImiejLabel;
    private javax.swing.JTextField ImiejTextField;
    private javax.swing.JLabel NazwiskojLabel;
    private javax.swing.JTextField NazwiskojTextField;
    private javax.swing.JButton PrzejdzjButton;
    private javax.swing.JButton WyszukajjButton;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<kino.Rezerwacja> rezerwacjeList;
    private javax.swing.JMenuItem zamknijMItem;
    private javax.swing.JMenuItem zamknijOknoMItem;
    // End of variables declaration//GEN-END:variables
}
