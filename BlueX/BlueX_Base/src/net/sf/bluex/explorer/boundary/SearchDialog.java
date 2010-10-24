/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchDialog.java
 *
 * Created on Oct 2, 2009, 5:01:29 PM
 */

package net.sf.bluex.explorer.boundary;

import java.io.File;
import java.util.Vector;
import net.sf.bluex.components.HelpMapper;
import net.sf.bluex.controller.FileModule;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.explorer.components.SearchPanel;

/**
 *
 * @author Blue
 */
public class SearchDialog extends javax.swing.JDialog {
    Vector<SearchPanel> vectSP=new Vector<SearchPanel>();

    private static SearchDialog sd;

    /** Creates new form SearchDialog */
    private SearchDialog(java.awt.Frame parent) {
        super(parent, false);
        initComponents();

        this.setIconImage(UsefulMethods.getImage(FileModule.APP_ICON));
        this.setLocationRelativeTo(parent);
    }

    public static void openSearchBoxFor(File currentFile){
        if(sd==null)
            sd=new SearchDialog(null);
        if(!sd.isVisible())
            sd.setVisible(true);
        else
            sd.requestFocus();
        
        sd.addSearchPanel(currentFile);
    }

    private void addSearchPanel(File currentFile){
        SearchPanel sp=new SearchPanel(currentFile);
        vectSP.add(sp);
        jTabbedPane1.addTab("Search "+sp.getSearchNumber(), sp);
        jTabbedPane1.setSelectedComponent(sp);
        sp.setFocus();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search");

        jButton3.setText("Help");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        HelpMapper.showHelpFor("Search");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        for(SearchPanel sp : vectSP)
            sp.doStopSearch();//stop all search
//        SearchPanel.threadCount=1;
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}