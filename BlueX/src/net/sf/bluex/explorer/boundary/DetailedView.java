package net.sf.bluex.explorer.boundary;

import net.sf.bluex.controller.FolderIntruder;
import net.sf.bluex.explorer.components.AbstractViewer;
import net.sf.bluex.explorer.components.FilePopUpMenu;
import net.sf.bluex.controller.UsefulMethods;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXFile;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.explorer.components.MyTable;

/**
 *
 * @author Blue
 */
public class DetailedView extends AbstractViewer implements Runnable, KeyListener, MouseListener{

    //displaying object
    private MyTable jTable;

    private File nextFile;

    private boolean dataUpdated;

    UninterruptableThread t;

    public DetailedView(BaseWindow bw){
        super(bw);
        initComponents();
        placeComponents();
    }

    /*************************
     **** INSTANCE METHODS ***
     *************************/

    private void initComponents() {
        //jtable init
        jTable=new MyTable();
        jTable.addKeyListener(this);
        jTable.addMouseListener(this);
    }

    private void placeComponents(){
        JScrollPane jsp=new JScrollPane(jTable);
        jsp.setBorder(null);
        jsp.addMouseListener(this);
        jTable.getParent().setBackground(BlueXStatics.tableBackground);
        this.setLayout(new BorderLayout());
        this.add(jsp,BorderLayout.CENTER);
    }

    public boolean updateContents(File file) {
        count=0; hidden=0;
        nextFile=file;
//        t=UninterruptableThread.getMyThread(this, "Explorer Thread");
//        t.start();
        run();
        setTitle(file);
        return dataUpdated;
    }

    @Override
    public void addFile(File file) {
    }

    @Override
    public Vector<File> getSelectedFiles() {
        int[] rows=jTable.getSelectedRows();
        Vector<File> vectSelected=new Vector<File>();

        for(int index : rows){
            vectSelected.add(jTable.getModel().getFileAt(index).getFile());
        }

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
        clearData();

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
        return jTable.getModel().getRowCount();
    }

    @Override
    public boolean safeToClose() {
        return true;
    }

    @Override
    public String getTabName() {
        return "Detailed View";
    }

    private synchronized boolean doFillJob(File files){
        Vector<Object> data=null;
        if(files!=null){//if files and folders mode is selected
            //create a instance of files object which contains all files and folders for corresoponding parent folder
            File[] fileArr=FolderIntruder.getFilesFolders(files);
            //if the file object is not null then go inside else outside return.
            if(fileArr!=null){
                BlueXFile[] bxFiles=BlueXFile.getArrayOfBlueXFiles(fileArr);
                Arrays.sort(bxFiles);//sort the files got

                for(BlueXFile bxf : bxFiles){
                    //check whether file or folder
                    if(bxf.getFile().isHidden()){
                        hidden++;
                        if(!showHidden)
                            continue;
                    }else
                        count++;
                    
                    data=new Vector<Object>();
                    data.add(bxf);
                    if(bxf.getFile().isFile())
                        data.add(UsefulMethods.getFileSize(bxf.getFile().length()));
                    else
                        data.add("");
                    data.add(BlueXStatics.getFileDescription(bxf.getFile()));
                    jTable.getModel().addRow(data);
                }
            }
        }
        else{//if it is null
            //make vector of vector object to get the data to be displayed
            //iterate through each root
            for(BlueXFile bxf : BlueXFile.getRoots()){
                data=new Vector<Object>();
                data.add(bxf);
                String totalSpace="";
                try {
                    if (BlueXStatics.fsv.isDrive(bxf.getFile())) {
                        totalSpace = UsefulMethods.getFileSize(bxf.getFile().getTotalSpace());
                    }
                } catch (Throwable e) {
                }
                data.add(totalSpace);
                data.add(BlueXStatics.getFileDescription(bxf.getFile()));
                count++;
                jTable.getModel().addRow(data);
            }
        }
        return true;
    }

    @Override
    public void setFocus() {
        jTable.requestFocus();
    }

    @Override
    public Vector<File> getAllFiles() {
        return new Vector<File>();//return empty it is of no use yaar
    }

    @Override
    public void clearData(){
        jTable.getModel().clear();
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==jTable){//when clicked inside the table
            if(e.getClickCount()==2 && e.getButton()==MouseEvent.BUTTON1){
                open();
            }

            else if(e.getButton()==e.BUTTON3){
                
                int[] rows=jTable.getSelectedRows();
                int row=jTable.rowAtPoint(e.getPoint());

                boolean changeSelection=true;

                for(int i=0;i<rows.length;i++)
                    if(rows[i]==row){
                        changeSelection=false;
                        break;
                    }

                if(changeSelection)
                    jTable.setRowSelectionInterval(row, row);


                FilePopUpMenu.getPopupMenu(this).show(jTable, e.getPoint().x, e.getPoint().y);
            }
        }
        else{//for the jscroll pane

            jTable.clearSelection();
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
            e.consume();
            open();
        }else if(e.getKeyCode()==KeyEvent.VK_F5){
            if(e.getModifiersEx()==e.ALT_DOWN_MASK)
                showProperties();
            else
                refresh();
        }else if(e.getKeyCode()==KeyEvent.VK_F2){
            rename();
        }else if(e.getKeyCode()==KeyEvent.VK_CONTEXT_MENU){
            FilePopUpMenu.getPopupMenu(this).show(jTable, 200, 30);
        }
    }
    public void keyReleased(KeyEvent e) {}

    @Override
    public void selectAll() {
        jTable.selectAll();
    }

    @Override
    public void invertSelection() {
        JOptionPane.showMessageDialog(bw, "Not available in Detailed View.");
    }

    @Override
    public int getSelectedRow() {
        int row=0;
        for(int i : jTable.getSelectedRows())
            if(i==jTable.getSelectedRow())
                return row;
            else
                row++;
        return -1;
    }

    @Override
    protected void setSelectedRow(int pos) {
        jTable.setRowSelectionInterval(pos, pos);
    }
}
