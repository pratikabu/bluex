/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HelpWindow.java
 *
 * Created on Sep 27, 2009, 8:05:13 PM
 */

package net.sf.bluex.boundary;

import net.sf.bluex.controller.FileModule;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.components.Stack;
import net.sf.bluex.components.Info;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.components.InfoIcon;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import net.sf.bluex.threads.InterruptableThread;

/**
 *
 * @author Blue
 */
public class HelpWindow extends javax.swing.JFrame implements HyperlinkListener, Runnable{
    private static HelpWindow hw;

    private URL currentURL;

    private Stack stURL=new Stack();

    private InterruptableThread t;

    private String status;
    private String goToURL;

    private InfoIcon i2;

    /** Creates new form HelpWindow */
    public HelpWindow() {
        initComponents();
        jToolBar2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        jToolBar3.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        jEditorPane1.addHyperlinkListener(this);
        
        jButton1.setIcon(UsefulMethods.getIcon("back.gif"));
        jButton1.setText("");
        
        jButton2.setText("");
        jButton2.setIcon(UsefulMethods.getIcon("forward.gif"));

        jButton3.setText("");
        jButton3.setIcon(UsefulMethods.getIcon("home.gif"));

        jButton6.setText("");
        jButton6.setIcon(UsefulMethods.getIcon("refresh.gif"));

        jButton5.setText("");
        jButton5.setIcon(UsefulMethods.getIcon("goto.gif"));

        jButton4.setText("");
        jButton4.setIcon(UsefulMethods.getIcon("delete.gif"));

        setBounds(50, 50, 850, 600);
        this.setLocationByPlatform(true);
        this.setIconImage(UsefulMethods.getImage(FileModule.APP_ICON));

        i2=InfoIcon.getNewInstance();
        jToolBar3.add(i2);
        lbLinkDetail.setText("");

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ew){
                jButton4ActionPerformed(null);
            }
        });

        this.setVisible(true);
    }

    private void renderPage(URL currentURL){
        Object lastURL=null;
        try{
            lastURL=stURL.elementAt(stURL.getBackPosition());
        }catch(ArrayIndexOutOfBoundsException y){
            try{
                lastURL=new URL("file:///foo/bar");
            }catch(Exception e){}
        }
        
        if(lastURL==null || !currentURL.toString().equals(lastURL.toString()))
            stURL.push(currentURL);
        renderPageWithoutEntry(currentURL);
    }

    private void renderPageWithoutEntry(URL currentURL){
        this.currentURL=currentURL;
//        goToURL=currentURL.toString();

        if(t!=null && t.isAlive()){
            t=null;//force fully stop the thread.. can't help it yaar..
        }

        if(t==null || !t.isAlive()){
            t=InterruptableThread.getMyThread(this, "URL Loading Thread");
            t.start();
        }
    }

    public void run() {
        boolean error=false;
        status="Loading...";
        lbStatus.setText("Loading...");
        ensureBackForwardButtons();
        jEditorPane1.setContentType("text/html");
        try{
            jEditorPane1.setPage(currentURL);
        }catch(UnknownServiceException use){
            Info info=new Info("How to mail?", "Use any mail server like:\n" +
                    "http://www.gmail.com, http://mail.yahoo.com, etc.\n" +
                    "just click on the Compose New Mail link or button." +
                    " In the \"To\" text box write \"pratikabu@gmail.com\" and write down the content of" +
                    "message in the text area provided.\n" +
                    "Thank you.");
            i2.showInfo(info);
        }catch(ConnectException ec){
            Info info=new Info("Connection timed out", "The host to which you are trying is not responding." +
                    " The host may be too busy.\n" +
                    "Please try again later.");
            i2.showInfo(info);
            error=true;
        }catch(UnknownHostException ec){
            Info info=new Info("Could not identify the host", "The help center is unable to identify the host:\n" +
                    currentURL.toString()+"\n" +
                    "Try again.");
            i2.showInfo(info);
            error=true;
        }catch(IOException e){
            error=true;
        }

        status="Done";
        lbStatus.setText(status);
        if(error)
            showErrorPage();
    }

    public static void showHelpWindow(String path){
        if(path.trim().isEmpty())
            path=FileModule.HELP_FOLDER+File.separatorChar+"index.html";
        File file=new File(path);
        try{
            showHelpWindow(file.toURI().toURL());
        }catch(Exception e){
            //show error page
        }
    }

    public static void showHelpWindow(URL url){
        if(hw==null)
            hw=new HelpWindow();

        if(hw.isVisible())
            hw.requestFocus();

        if(!hw.isVisible())
            hw.setVisible(true);

        hw.renderPage(url);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnCopy = new javax.swing.JMenuItem();
        mnRefresh = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        mnGoToPage = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        lbStatus = new javax.swing.JLabel();
        jToolBar3 = new javax.swing.JToolBar();
        lbLinkDetail = new javax.swing.JLabel();

        mnCopy.setMnemonic('C');
        mnCopy.setText("Copy");
        mnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCopyActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnCopy);

        mnRefresh.setMnemonic('R');
        mnRefresh.setText("Refresh");
        mnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRefreshActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnRefresh);
        jPopupMenu1.add(jSeparator2);

        mnGoToPage.setMnemonic('G');
        mnGoToPage.setText("Go To Page");
        mnGoToPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGoToPageActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnGoToPage);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Help Center");
        setBounds(new java.awt.Rectangle(50, 50, 600, 400));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setText("Back");
        jButton1.setToolTipText("Back");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Forward");
        jButton2.setToolTipText("Forward");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator1);

        jButton3.setText("Home");
        jButton3.setToolTipText("Show Help Center Start Page");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton6.setText("Refresh");
        jButton6.setToolTipText("Refresh Current Page");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton5.setText("Go to");
        jButton5.setToolTipText("Go To Page");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator3);

        jButton4.setText("Quit");
        jButton4.setToolTipText("Quit from the Help Center");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jEditorPane1.setEditable(false);
        jEditorPane1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(jEditorPane1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        lbStatus.setText("Status");
        jToolBar2.add(lbStatus);

        jPanel1.add(jToolBar2, java.awt.BorderLayout.LINE_START);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        lbLinkDetail.setText("Link Detail");
        jToolBar3.add(lbLinkDetail);

        jPanel1.add(jToolBar3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        renderPageWithoutEntry((URL)stURL.getBackItem());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
//        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        renderPageWithoutEntry((URL)stURL.getForwardItem());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
            String path=FileModule.HELP_FOLDER+File.separatorChar+"index.html";
            File file=new File(path);
            renderPage(file.toURI().toURL());//home page
        }catch(MalformedURLException d){}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCopyActionPerformed
        // TODO add your handling code here:
        jEditorPane1.copy();
    }//GEN-LAST:event_mnCopyActionPerformed

    private void mnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRefreshActionPerformed
        // TODO add your handling code here:
        renderPageWithoutEntry(currentURL);
    }//GEN-LAST:event_mnRefreshActionPerformed

    private void mnGoToPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGoToPageActionPerformed
        // TODO add your handling code here:
        goToURL=JOptionPane.showInputDialog(this, "Enter url to go:", goToURL);
        try{
            if(goToURL!=null){
                URL url=new URL(goToURL);
                openURL(url);
            }
        }catch(MalformedURLException e){
            Info info=new Info("Invalid URL", "The URL: \""+goToURL+"\" that you are trying to reach is invalid.\n" +
                    "Please try again.");
//            JOptionPane.showMessageDialog(this, "Not a valid url. Try again later");
            i2.showInfo(info);
        }
    }//GEN-LAST:event_mnGoToPageActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        mnGoToPageActionPerformed(null);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        mnRefreshActionPerformed(null);
    }//GEN-LAST:event_jButton6ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JLabel lbLinkDetail;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JMenuItem mnCopy;
    private javax.swing.JMenuItem mnGoToPage;
    private javax.swing.JMenuItem mnRefresh;
    // End of variables declaration//GEN-END:variables

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                HTMLDocument doc = (HTMLDocument) jEditorPane1.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                openURL(e.getURL());
            }
        }else if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
            lbLinkDetail.setText(e.getDescription());
        }else if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
            lbLinkDetail.setText("");
        }
    }

    private void ensureBackForwardButtons() {
        jButton1.setEnabled(stURL.ensureBack());
        jButton2.setEnabled(stURL.ensureForward());
    }

    private void showErrorPage() {
        try{
            String path=FileModule.HELP_FOLDER+File.separatorChar+"error.html";
            File file=new File(path);
            renderPageWithoutEntry(file.toURI().toURL());//error page
        }catch(MalformedURLException d){
            d.printStackTrace();
        }
    }

    private void openURL(URL url) {
        boolean redirectURL=Boolean.parseBoolean(BlueXStatics.prop.getProperty("redirectURL"));
        if(!url.getProtocol().equalsIgnoreCase("http") || !redirectURL){
            renderPage(url);
        }else
            newComponents.components.UsefulMethods.openPageInBrowser(url.toString());
    }

}
