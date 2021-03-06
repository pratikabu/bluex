/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ErrorIcon.java
 *
 * Created on Sep 26, 2009, 12:12:30 AM
 */

package net.sf.bluex.components;

import net.sf.bluex.boundary.InfoDetail;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.threads.InterruptableThread;

/**
 *
 * @author Blue
 */
public class InfoIcon extends javax.swing.JPanel implements Runnable {
    private int count=8;
    private boolean doNotWarn;
    private InterruptableThread t;
    private Info info;

    private static InfoIcon inf;

    /** Creates new form ErrorIcon */
    private InfoIcon() {
        initComponents();

        jLabel1.setText("   ");
    }

    public static InfoIcon getNewInstance(){
        return new InfoIcon();
    }

    public static InfoIcon getInfoIcon(){
        if(inf==null)
            inf=new InfoIcon();
        return inf;
    }

    public void showInfo(Info info){
            this.info=info;
            jLabel1.setToolTipText("<html><body><b>"+info.getMessage()+"</b><br/>" +
                    "Double Click here to view the details.</body></html>");
            doNotWarn=false;
            t=InterruptableThread.getMyThread(this, "Information Thread");
            t.start();
    }

    public static void info(Info info){
        if(inf!=null){
            inf.info=info;
            inf.jLabel1.setToolTipText("<html><body><b>"+info.getMessage()+"</b><br/>" +
                    "Double Click here to view the details.</body></html>");
            inf.doNotWarn=false;
            inf.t=InterruptableThread.getMyThread(inf, "Information Thread");
            inf.t.start();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText("Q");
        jLabel1.setToolTipText("Double Click here to view the Exception occurred in the Exception Manager");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            InfoDetail.showExceptionDetail(null, info);

            doNotWarn=true;
            if(t!=null)
                while(t.isAlive());

            jLabel1.setIcon(null);
        }
    }//GEN-LAST:event_jLabel1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    public void run() {
        for(int i=0; i<count; i++){
            if(!doNotWarn){
                jLabel1.setIcon(null);
                jLabel1.setText("   ");
                try{
                    Thread.sleep(700);
                }catch(Exception e){
                    //pass
                }
                jLabel1.setText("");
                jLabel1.setIcon(UsefulMethods.getIcon("info.gif"));
                try{
                    Thread.sleep(700);
                }catch(Exception e){
                    //pass
                }
            }
        }
    }

}
