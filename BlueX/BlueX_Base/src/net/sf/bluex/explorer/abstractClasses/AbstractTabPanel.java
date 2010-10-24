/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.abstractClasses;

import net.sf.bluex.explorer.components.MyTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Blue
 */
public abstract class AbstractTabPanel extends JPanel implements ActionListener{
    protected JPanel pNorth,pNSouth,pNSWest,pNSEast;
    protected JPanel pCenter;
    protected JPanel pSouth,pSWest,pSEast;
    protected JTable jTable;
    protected JButton cmdSEast1,cmdSEast2,cmdSWest1,cmdSWest2;
    protected JLabel lbPanelHead;
    //static memebers
    public final static Color NPBG_COLOR=new Color(176,176,176);
    public static final Color CENTER_COLOR=Color.WHITE;
    //constructor
    
    public AbstractTabPanel(){
        initComponents();
        placeComponents();
    }
    
    protected void initComponents(){
        //panels init
        //north
        pNorth=new JPanel(new BorderLayout());
        //pNSouth
        pNSouth=new JPanel(new BorderLayout());
        pNSouth.setBackground(NPBG_COLOR);
        //pNSWest
        pNSWest=new JPanel(new FlowLayout(FlowLayout.LEFT));
        pNSWest.setBackground(NPBG_COLOR);
        //pNSEast
        pNSEast=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pNSEast.setBackground(NPBG_COLOR);
        //pCenter
        ///////////
        pCenter=new JPanel(new BorderLayout());
        pCenter.setBackground(CENTER_COLOR);
        //pSouth
        pSouth=new JPanel(new BorderLayout());
        pSouth.setBackground(CENTER_COLOR);
        //pSWest
        pSWest=new JPanel(new FlowLayout(FlowLayout.LEFT));
        pSWest.setBackground(CENTER_COLOR);
        //pSEast
        pSEast=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pSEast.setBackground(CENTER_COLOR);
        ///////////////
        
        //label init
        lbPanelHead=new JLabel("Tab Panel");
        lbPanelHead.setForeground(CENTER_COLOR);
        //button init
        //SouthEast buttons
        cmdSEast1=new JButton("cmdEast1");
        cmdSEast1.addActionListener(this);
        cmdSEast1.setBackground(CENTER_COLOR);

        cmdSEast2=new JButton("cmdEast2");
        cmdSEast2.addActionListener(this);
        cmdSEast2.setBackground(CENTER_COLOR);
        
        //SouthWest buttons
        cmdSWest1=new JButton("cmdWest1");
        cmdSWest1.addActionListener(this);
        cmdSWest1.setBackground(CENTER_COLOR);

        cmdSWest2=new JButton("cmdWest1");
        cmdSWest2.addActionListener(this);
        cmdSWest2.setBackground(CENTER_COLOR);
        ////////////

        //jTable
        jTable=new JTable(new MyTableModel());
        jTable.setShowGrid(false);
        ////////////////

        //set Layout of panel
        setLayout(new BorderLayout());
    }
    
    protected void placeComponents(){
        placeNorthPanel();
        placeCenterPanel();
        placeSouthPanel();
        //place major components
        add(pNorth,BorderLayout.NORTH);
        add(pCenter,BorderLayout.CENTER);
        add(pSouth,BorderLayout.SOUTH);
    }
    
    protected void placeNorthPanel(){
        pNSWest.add(lbPanelHead);
        ///
        pNSouth.add(pNSWest,BorderLayout.WEST);
        pNSouth.add(pNSEast,BorderLayout.EAST);
        ///
        pNorth.add(pNSouth,BorderLayout.SOUTH);
    }
    
    protected void placeCenterPanel(){
        JScrollPane jspTable=new JScrollPane(jTable);
        pCenter.add(jspTable,BorderLayout.CENTER);
        jspTable.getParent().setBackground(CENTER_COLOR);
    }
    
    protected void placeSouthPanel(){
        //pSWest addition
        pSWest.add(cmdSWest1);
        pSWest.add(cmdSWest2);
        //pSEast additon
        pSEast.add(cmdSEast1);
        pSEast.add(cmdSEast2);
        //pSouth addition
        pSouth.add(pSWest,BorderLayout.WEST);
        pSouth.add(pSEast,BorderLayout.EAST);
        
        //remove paint focus
//        com.gmail.pratikabu.blueX.controller.UsefulMethods.removeFocusPaint(pSWest);
//        com.gmail.pratikabu.blueX.controller.UsefulMethods.removeFocusPaint(pSEast);
    }
    
    //protected abstracted functioning methods
    protected abstract void doCmdSEast1();
    
    protected abstract void doCmdSEast2();
    
    protected abstract void doCmdSWest1();
    
    protected abstract void doCmdSWest2();
    
    /**
     * public methods
     */
    public void setPanelHead(String lbHead){
        this.lbPanelHead.setText(lbHead);
    }
    
    public void setCmdSEast1Text(String text){
        cmdSEast1.setText(text);
    }
    
    public void setCmdSEast2Text(String text){
        cmdSEast2.setText(text);
    }
    
    public void setCmdSWest1Text(String text){
        cmdSWest1.setText(text);
    }
    
    public void setCmdSWest2Text(String text){
        cmdSWest2.setText(text);
    }
    
    public void setCmdSEast1Visible(boolean visible){
        cmdSEast1.setVisible(visible);
    }
    
    public void setCmdSEast2Visible(boolean visible){
        cmdSEast2.setVisible(visible);
    }
    
    public void setCmdSWest1Visible(boolean visible){
        cmdSWest1.setVisible(visible);
    }
    
    public void setCmdSWest2Visible(boolean visible){
        cmdSWest2.setVisible(visible);
    }

    //action Listener event
    public void actionPerformed(ActionEvent e) {
        Object obj=e.getSource();
        if(obj==cmdSEast1){
            doCmdSEast1();
        }
        else if(obj==cmdSEast2){
            doCmdSEast2();
        }
        else if(obj==cmdSWest1){
            doCmdSWest1();
        }
        else if(obj==cmdSWest2){
            doCmdSWest2();
        }
    }
}
