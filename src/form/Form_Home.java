/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package form;

import javax.swing.ImageIcon;
import model.Model_Card;

/**
 *
 * @author Ernesto
 */
public class Form_Home extends javax.swing.JPanel {

    /**
     * Creates new form Form_Home
     */
    public Form_Home() {
        initComponents();
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/icons/icons8-array-30.png")), "Lab 1°", "Arreglos", "Dificultad: ★☆☆☆☆☆"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/icons/icons8-sort-30.png")), "Lab 2°", "Ordenamiento y Búsqueda", "Dificultad: ★★☆☆☆☆"));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/icons/icons8-arrows-30.png")), "Lab 3°", "Recursión", "Dificultad: ★★★☆☆☆"));
        card4.setData(new Model_Card(new ImageIcon(getClass().getResource("/icons/icons8-queue-30.png")), "Lab 4°", "Pilas y Colas", "Dificultad: ★★★★☆☆"));
        card5.setData(new Model_Card(new ImageIcon(getClass().getResource("/icons/icons8-list-30.png")), "Lab 5°", "ArrayLists", "Dificultad: ★★★☆☆☆"));
        card6.setData(new Model_Card(new ImageIcon(getClass().getResource("/icons/icons8-tree-32.png")), "Lab 6°", "Árboles", "Dificultad: ★★★★★☆"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        card1 = new componentes.Card();
        card2 = new componentes.Card();
        card3 = new componentes.Card();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        card4 = new componentes.Card();
        card5 = new componentes.Card();
        card6 = new componentes.Card();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 242, 242));
        setForeground(new java.awt.Color(242, 242, 242));
        setOpaque(false);

        jLayeredPane1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        card1.setColor1(new java.awt.Color(142, 142, 250));
        card1.setColor2(new java.awt.Color(123, 123, 245));
        card1.setMinimumSize(new java.awt.Dimension(379, 191));
        card1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                card1MouseClicked(evt);
            }
        });
        jLayeredPane1.add(card1);

        card2.setColor1(new java.awt.Color(241, 208, 62));
        card2.setColor2(new java.awt.Color(241, 208, 62));
        card2.setMinimumSize(new java.awt.Dimension(379, 191));
        card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                card2MouseClicked(evt);
            }
        });
        jLayeredPane1.add(card2);

        card3.setColor1(new java.awt.Color(186, 123, 247));
        card3.setColor2(new java.awt.Color(167, 94, 236));
        card3.setMinimumSize(new java.awt.Dimension(379, 191));
        card3.setPreferredSize(new java.awt.Dimension(112, 172));
        card3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                card3MouseClicked(evt);
            }
        });
        jLayeredPane1.add(card3);

        jLayeredPane2.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        card4.setColor1(new java.awt.Color(142, 142, 250));
        card4.setColor2(new java.awt.Color(123, 123, 245));
        card4.setMinimumSize(new java.awt.Dimension(379, 191));
        card4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                card4MouseClicked(evt);
            }
        });
        jLayeredPane2.add(card4);

        card5.setColor1(new java.awt.Color(241, 208, 62));
        card5.setColor2(new java.awt.Color(241, 208, 62));
        card5.setMinimumSize(new java.awt.Dimension(379, 191));
        card5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                card5MouseClicked(evt);
            }
        });
        jLayeredPane2.add(card5);

        card6.setColor1(new java.awt.Color(186, 123, 247));
        card6.setColor2(new java.awt.Color(167, 94, 236));
        card6.setMinimumSize(new java.awt.Dimension(379, 191));
        card6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                card6MouseClicked(evt);
            }
        });
        jLayeredPane2.add(card6);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-user-35 (1).png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ERNESTO VARGAS | JAVA DEV");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-user-35 (1).png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("ELÍAS CASTILLO | JAVA DEV");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-code-35.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("HYPERION © 2023");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(83, 83, 83)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(76, 76, 76)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addGap(11, 11, 11)))
                        .addGap(60, 60, 60))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void card1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_card1MouseClicked

    private void card2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_card2MouseClicked

    private void card3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_card3MouseClicked

    private void card4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_card4MouseClicked

    private void card5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_card5MouseClicked

    private void card6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_card6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_card6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private componentes.Card card1;
    private componentes.Card card2;
    private componentes.Card card3;
    private componentes.Card card4;
    private componentes.Card card5;
    private componentes.Card card6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    // End of variables declaration//GEN-END:variables
}
