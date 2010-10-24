/*
 * To change

            @Override
            public boolean safeToClose() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getTabName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AbstractNewExplorer.java
 *
 * Created on Sep 29, 2009, 10:54:01 AM
 */

package net.sf.bluex.explorer.abstractClasses;

import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.Vector;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.components.Stack;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.explorer.boundary.DetailedView;
import net.sf.bluex.explorer.boundary.ListView;
import net.sf.bluex.explorer.components.AbstractViewer;
import net.sf.bluex.explorer.components.FilePopUpMenu;
import net.sf.bluex.explorer.components.MyListRenderer;
import net.sf.bluex.plugin.MyPanel;

/**
 *
 * @author Blue
 */
public abstract class AbstractExplorer extends MyPanel {

    //base window object
    protected BaseWindow bw;

    //abstract viewer
    protected AbstractViewer av;

    //Detailed view
    private DetailedView dv;

    //list view
    private ListView lv;

    /** Creates new form AbstractNewExplorer */
    public AbstractExplorer(BaseWindow bw) {
        this.bw=bw;
        initComponents();

        jButton1.setText("");
        jButton1.setIcon(UsefulMethods.getIcon("back.gif"));

        jButton3.setText("");
        jButton3.setIcon(UsefulMethods.getIcon("forward.gif"));

        jButton4.setText("");
        jButton4.setIcon(UsefulMethods.getIcon("up.gif"));

        jButton5.setText("");
        jButton5.setIcon(UsefulMethods.getIcon("copy.gif"));

        jButton6.setText("");
        jButton6.setIcon(UsefulMethods.getIcon("paste.gif"));

        jButton7.setText("");
        jButton7.setIcon(UsefulMethods.getIcon("addVirus.gif"));

        jButton8.setText("");
        jButton8.setIcon(UsefulMethods.getIcon("delete.gif"));

        jButton9.setText("");
        jButton9.setIcon(UsefulMethods.getIcon("refresh.gif"));

        jButton10.setText("");
        jButton10.setIcon(UsefulMethods.getIcon("process.gif"));

        jButton11.setText("");
        jButton11.setIcon(UsefulMethods.getIcon("addToScanBasket.gif"));

        jButton12.setText("");
        jButton12.setIcon(UsefulMethods.getIcon("requestOS.gif"));

        jButton13.setText("");
        jButton13.setIcon(UsefulMethods.getIcon("search.gif"));

        //add the view
        av=getView("Detailed");
        this.pCenter.add(av,"Detailed");

        //init jcb
        initJCB();

        //favorites
        initFavorites();
    }

    protected void changeView(String viewName) {
        File currentURI=null;
        try{
            currentURI=av.getCurrentURI();
        }catch(Exception e) {}

        Stack st=null;
        if(av!=null)
            st=av.getStTraverse();

        av=getView(viewName);

        if(st!=null)
            av.setStTraverse(st);

        pCenter.add(av,viewName);
        ((CardLayout)pCenter.getLayout()).show(pCenter, viewName);
        av.updateContents(currentURI);
    }

    private AbstractViewer getView(String viewType) {
        if(viewType.equals("Detailed")){
            dv=new DetailedView(bw){
                @Override
                public void processStarted(File curretn, File next){
                    bw.setLoadingText("Fetching Data");
                    bw.setLoadingBar(true);
                }

                @Override
                public void processFinished(File current){
                    bw.setLoadingBar(false);

                    //path
                    if(current!=null)
                        jcbPath.setText(current.getAbsolutePath());
                    else
                        jcbPath.setText(BlueXStatics.getFileName(null));

                    //enable disable job
                    jButton1.setEnabled(av.canGoBack());
                    jButton3.setEnabled(av.canGoForward());
                    jButton4.setEnabled(av.canGoUp());
                }
            };
            return dv;
        }else{
            lv=new ListView(bw){
                @Override
                public void processStarted(File curretn, File next){
                    bw.setLoadingText("Fetching Data");
                    bw.setLoadingBar(true);
                }

                @Override
                public void processFinished(File current){
                    bw.setLoadingBar(false);

                    //path
                    if(current!=null)
                        jcbPath.setText(current.getAbsolutePath());
                    else
                        jcbPath.setText(BlueXStatics.getFileName(null));

                    //enable disable job
                    jButton1.setEnabled(av.canGoBack());
                    jButton3.setEnabled(av.canGoForward());
                    jButton4.setEnabled(av.canGoUp());
                }
            };
            return lv;
        }
    }

    /**
     * @protected abstract methods/////////////////////////////////////////////////
     */

    protected abstract void doOpenDefault();

    public abstract void doProcessJob();

    protected abstract void doAddVirusJob();

    protected abstract void doGoToJob();

    public abstract void doAddToScanBasketJob();

    protected abstract void doSearchJob();

    protected abstract void doStopAnyThreadJob();

    protected abstract void doShowFavorites();

    /////////////////////////////////////////////////////////////////////abstract methods ends

    ////protected working methods starts///////////////////////////////////
    protected static Vector<Object> getColumnHead(String thirdColumn){
        Vector<Object> colHead=new Vector<Object>();
        colHead.add("");colHead.add("Name");colHead.add("Size");colHead.add(thirdColumn);colHead.add("Hidden");
        return colHead;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jToolBar2 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jcbPath = new newComponents.MyJTextField()
        ;
        jPanel5 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jButton12 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        pCenter = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        jLabel1.setText("Address:");
        jToolBar3.add(jLabel1);

        jPanel4.add(jToolBar3, java.awt.BorderLayout.WEST);
        jPanel4.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/bluex/resources/go.gif"))); // NOI18N
        jButton2.setToolTipText("Go To selected url");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jPanel4.add(jToolBar2, java.awt.BorderLayout.EAST);

        jcbPath.setToolTipText("Write here to go");
        jcbPath.setDragEnabled(true);
        jcbPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jcbPathFocusGained(evt);
            }
        });
        jcbPath.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcbPathKeyPressed(evt);
            }
        });
        jPanel4.add(jcbPath, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel5.add(jSeparator1, java.awt.BorderLayout.PAGE_END);

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

        jButton3.setText("Forward");
        jButton3.setToolTipText("Forward");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setText("Up");
        jButton4.setToolTipText("Up");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator3);

        jButton5.setText("Copy");
        jButton5.setToolTipText("Copy");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton6.setText("Paste");
        jButton6.setToolTipText("Paste");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton8.setText("Del");
        jButton8.setToolTipText("Delete");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton8);
        jToolBar1.add(jSeparator5);

        jButton7.setText("Add2DB");
        jButton7.setToolTipText("Add to Virus Database");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);

        jButton9.setText("Refresh");
        jButton9.setToolTipText("Refresh");
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton9);

        jButton13.setText("Search");
        jButton13.setToolTipText("Search");
        jButton13.setFocusable(false);
        jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton13);
        jToolBar1.add(jSeparator4);

        jButton10.setText("Process");
        jButton10.setToolTipText("Process Scan Basket");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton10);

        jButton11.setText("SB");
        jButton11.setToolTipText("Add to Scan Basket");
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton11);
        jToolBar1.add(jSeparator6);

        buttonGroup1.add(jToggleButton1);
        jToggleButton1.setSelected(true);
        jToggleButton1.setText("Detail");
        jToggleButton1.setToolTipText("Detailed View");
        jToggleButton1.setFocusable(false);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton1);

        buttonGroup1.add(jToggleButton2);
        jToggleButton2.setText("List");
        jToggleButton2.setToolTipText("List View");
        jToggleButton2.setFocusable(false);
        jToggleButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton2);
        jToolBar1.add(jSeparator7);

        jButton12.setText("ROS");
        jButton12.setToolTipText("Request OS to Run");
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton12);

        jPanel5.add(jToolBar1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(100);
        jSplitPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jSplitPane1MouseEntered(evt);
            }
        });

        pCenter.setLayout(new java.awt.CardLayout());
        jSplitPane1.setRightComponent(pCenter);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Favorites:");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel3.add(jLabel2, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setCellRenderer(new MyListRenderer());
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jList1FocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel3);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jSplitPane1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSplitPane1MouseEntered
        // TODO add your handling code here:
//        jSplitPane1.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
    }//GEN-LAST:event_jSplitPane1MouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        av.back();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        av.forward();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        av.up();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        av.copy();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        av.paste();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        av.delete();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        doAddVirusJob();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        av.refresh();
//        initJCB();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        doSearchJob();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        doProcessJob();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        doAddToScanBasketJob();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        av.requestOSToRun();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        changeView("Detailed");
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        changeView("List");
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        doGoToJob();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jcbPathFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbPathFocusGained
        // TODO add your handling code here:
        jcbPath.selectAll();
    }//GEN-LAST:event_jcbPathFocusGained

    private void jcbPathKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbPathKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER)
            doGoToJob();
    }//GEN-LAST:event_jcbPathKeyPressed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if(evt.getButton()==evt.BUTTON1 && evt.getClickCount()==2)
            doShowFavorites();
        else if(evt.getButton()==evt.BUTTON3){
            int[] rows=jList1.getSelectedIndices();
            int row=jList1.locationToIndex(evt.getPoint());

            boolean changeSelection=true;

            for(int i=0;i<rows.length;i++)
                if(rows[i]==row){
                    changeSelection=false;
                    break;
                }

            if(changeSelection)
                jList1.setSelectedIndex(row);

            File file=null;
            try{
                file=(File)jList1.getSelectedValue();
            }catch(NullPointerException e){}
            FilePopUpMenu.getPopupMenu(av, file).show(jList1, evt.getPoint().x, evt.getPoint().y);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jList1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jList1FocusLost
        jList1.clearSelection();
    }//GEN-LAST:event_jList1FocusLost
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    protected javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    protected javax.swing.JTextField jcbPath;
    protected javax.swing.JPanel pCenter;
    // End of variables declaration//GEN-END:variables

    public void initFavorites(){
        Vector<File> vectFav=new Vector<File>();
        for(String location : BlueXStatics.fav){
            File file=null;
            if(!location.equals("null"))
                file=new File(location);
            
            vectFav.add(file);
        }

        jList1.setListData(vectFav);
    }

    private void initJCB() {
//        jcbPath.removeAllItems();
//
//        //add the roots to combo box
//        for(File file : BlueXStatics.getRoots())
//            jcbPath.addItem(file.getAbsolutePath());
    }
}
