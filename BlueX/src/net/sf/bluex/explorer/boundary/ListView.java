/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.boundary;

import net.sf.bluex.explorer.components.AbstractViewer;
import net.sf.bluex.explorer.components.FilePopUpMenu;
import net.sf.bluex.explorer.components.MyListRenderer;
import net.sf.bluex.threads.UninterruptableThread;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JScrollPane;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXFile;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.controller.FolderIntruder;

/**
 *
 * @author Blue
 */
public class ListView extends AbstractViewer implements Runnable, MouseListener, KeyListener{

    //displaying object
    private JList jList;

    private File nextFile;

    private boolean dataUpdated;

    UninterruptableThread t;

    public ListView(BaseWindow bw){
        super(bw);
        initComponents();
        placeComponents();
    }

    /*************************
     **** INSTANCE METHODS ***
     *************************/

    private void initComponents() {
        //jList init
        jList=new JList();
        jList.setLayoutOrientation(jList.VERTICAL_WRAP);
        jList.setCellRenderer(new MyListRenderer());
        jList.setVisibleRowCount(-1);
        jList.addKeyListener(this);
        jList.addMouseListener(this);
    }

    private void placeComponents(){
        JScrollPane jsp=new JScrollPane(jList);
        jsp.setBorder(null);
        jsp.addMouseListener(this);
        this.setLayout(new BorderLayout());
        this.add(jsp,BorderLayout.CENTER);
    }

    public boolean updateContents(File file) {
        count=0; hidden=0;
        setTitle(file);
        nextFile=file;
//        t=UninterruptableThread.getMyThread(this, "Explorer Thread");
//        t.start();
        run();
        return dataUpdated;
    }

    @Override
    public void addFile(File uri) {
    }

    @Override
    public Vector<File> getSelectedFiles() {
        int[] rows=jList.getSelectedIndices();
        Vector<File> vectSelected=new Vector<File>();

        for(int index : rows)
            vectSelected.add((File)jList.getModel().getElementAt(index));

        if(vectSelected.size()>0)
            return vectSelected;
        else
            return null;
    }

    public boolean isDataUpdated() {
        return dataUpdated;
    }

    @Override
    public void run() {
        //starting method called
        processStarted(currentFile, nextFile);

        //change the current uri
        currentFile=nextFile;
        if(nextFile!=null)
            dataUpdated=doFillJob(nextFile);
        else
            dataUpdated=doFillJob(null);

        //ending method called
        processFinished(currentFile);
        
        bw.setFileCount(count, hidden);
    }

    public int getTotalCounts() {
        return jList.getModel().getSize();
    }

    @Override
    public boolean safeToClose() {
        return true;
    }

    @Override
    public String getTabName() {
        return "List View";
    }

    private synchronized boolean doFillJob(File file){
        Vector<File> data=new Vector<File>();
        if(file!=null){//if files and folders mode is selected
            //create a instance of files object which contains all files and folders for corresoponding parent folder
            File[] fileArr=FolderIntruder.getFilesFolders(file);

            //if the file object is not null then go inside else outside return.
            if(fileArr!=null){
                BlueXFile[] bxFiles=BlueXFile.getArrayOfBlueXFiles(fileArr);
                Arrays.sort(bxFiles);//sort the files got

                for(BlueXFile tempFile : bxFiles){
                    //check whether file or folder
                    if(tempFile.getFile().isHidden()){
                        hidden++;
                        if(!showHidden)
                            continue;
                    }else
                        count++;

                    data.add(tempFile.getFile());
                }
            }
        }
        else{//if it is null
            //iterate through each root
            for(BlueXFile tempFile : BlueXFile.getRoots()){
                data.add(tempFile.getFile());
            }
        }

        //set data
        jList.setListData(data);
        return true;
    }

    @Override
    public void setFocus() {
        jList.requestFocus();
    }

    @Override
    public Vector<File> getAllFiles() {
        return new Vector<File>();//empty yaar
    }

    @Override
    public void clearData(){
        jList.setListData(new BlueXFile[]{});
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==jList){//when clicked inside the table
            if(e.getClickCount()==2 && e.getButton()==MouseEvent.BUTTON1){
                open();
            }

            else if(e.getButton()==e.BUTTON3){
                
                int[] rows=jList.getSelectedIndices();
                int row=jList.locationToIndex(e.getPoint());

                boolean changeSelection=true;

                for(int i=0;i<rows.length;i++)
                    if(rows[i]==row){
                        changeSelection=false;
                        break;
                    }

                if(changeSelection)
                    jList.setSelectedIndex(row);


                FilePopUpMenu.getPopupMenu(this).show(jList, e.getPoint().x, e.getPoint().y);
            }
        }
        else{//for the jscroll pane
            jList.clearSelection();
            setFocus();

            if(e.getButton()==e.BUTTON3){
                FilePopUpMenu.getPopupMenu(this).show((Component)e.getSource(), e.getPoint().x, e.getPoint().y);
            }
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_C && e.isControlDown()){
            e.consume();
            copy();
        }else if(e.getKeyCode()==KeyEvent.VK_V && e.isControlDown()){
            e.consume();
            paste();
        }else if(e.getKeyCode()==KeyEvent.VK_DELETE){
            e.consume();
            delete();
        }else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            if(canGoUp())
                up();
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT && e.isAltDown()){
            if(canGoForward())
                forward();
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT && e.isAltDown()){
            if(canGoBack())
                back();
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            open();
        }else if(e.getKeyCode()==KeyEvent.VK_F5){
            if(e.getModifiersEx()==e.ALT_DOWN_MASK)
                showProperties();
            else
                refresh();
        }else if(e.getKeyCode()==KeyEvent.VK_F2){
            rename();
        }else if(e.getKeyCode()==KeyEvent.VK_CONTEXT_MENU){
            FilePopUpMenu.getPopupMenu(this).show(jList, 200, 30);
        }
    }
    public void keyReleased(KeyEvent e) {}

    @Override
    public void selectAll() {
        int[] indices=new int[jList.getModel().getSize()];
        for(int i=0; i<jList.getModel().getSize(); i++)
            indices[i]=i;
        jList.setSelectedIndices(indices);
    }

    @Override
    public void invertSelection() {
        int[] selected=jList.getSelectedIndices();
        int[] indices=new int[jList.getModel().getSize()-selected.length];
        int index=0;
        for(int i=0; i<jList.getModel().getSize(); i++)
            for(int si : selected)
                if(i!=si)
                    indices[index++]=i;
        jList.setSelectedIndices(indices);
    }

    @Override
    public int getSelectedRow() {
        int row=0;
        for(int i : jList.getSelectedIndices())
            if(i==jList.getSelectedIndex())
                return row;
            else
                row++;
        return -1;
    }

    @Override
    protected void setSelectedRow(int pos) {
        jList.setSelectedIndex(pos);
    }
}
