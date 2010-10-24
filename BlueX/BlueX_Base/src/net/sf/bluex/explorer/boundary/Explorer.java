/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.boundary;

import net.sf.bluex.explorer.abstractClasses.AbstractExplorer;
import net.sf.bluex.components.Stack;
import net.sf.bluex.controller.UsefulMethods;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.controller.FileModule;
import net.sf.bluex.explorer.components.AbstractViewer;

/**
 *
 * @author Blue
 */
public class Explorer extends AbstractExplorer{

    //stack objects
    private Stack stScanBasket;

    public Explorer(BaseWindow bw){
        super(bw);
    }

    @Override
    protected void doOpenDefault() {
        av.open();
    }

    @Override
    public void doProcessJob() {
        if(stScanBasket!=null && stScanBasket.size()!=0)
            new ProcessDialog(null, stScanBasket);
    }

    @Override
    protected void doAddVirusJob() {
        Vector<File> files=av.getSelectedFiles();
        if(files!=null){
            int choice=JOptionPane.showConfirmDialog(this, "Are you sure you want to add "+files.size()+" file in database?", "Add to Virus DB", JOptionPane.YES_NO_OPTION);
            if(choice==JOptionPane.YES_OPTION){
                String fileName[]=new String[files.size()];
                int index=0;
                for(File tempURI : files){
                    fileName[index]=tempURI.getName();
                    fileName[index++]+="|";
                }
                net.sf.bluex.controller.FileModule.writeLumpFiles(bw,fileName);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Select any file to continue.");
    }

    @Override
    protected void doGoToJob() {
        File tempFile=new File(jcbPath.getText());

        if(tempFile.exists()){
            if(tempFile.isDirectory())//when it is a directory
                av.goToURI(tempFile);
            else{//when it is a file do the default open work
                //open it with the default editor
                UsefulMethods.openAssociatedFile(tempFile);
            }
        }else
            if(jcbPath.getText().equalsIgnoreCase("roots"))
                av.goToURI(null);
        else{
            JOptionPane.showMessageDialog(bw, "Cannot resolve the path.", "Path not found", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void doAddToScanBasketJob() {
        //ensure the stack of scan basket not null
        if(stScanBasket==null)
            //init the scanbasket object
            stScanBasket=new net.sf.bluex.components.Stack();

        //iterate through every indexed file and add it to scan basket
        for(File file : av.getSelectedFiles())
            stScanBasket.push(file);
    }

    @Override
    protected void doSearchJob() {
        //open the find window
        SearchDialog.openSearchBoxFor(av.getCurrentURI());
    }

    @Override
    protected void doStopAnyThreadJob() {
//        stop explorer thread
    }

    @Override
    protected void doShowFavorites() {
        //show the favorite item in the explorer
        try{
            this.goToURI((File)jList1.getSelectedValue());
        }catch(NullPointerException e){
            this.goToURI(null);
        }
        jList1.clearSelection();
    }

    @Override
    public boolean safeToClose() {
        return av.safeToClose();
    }

    @Override
    public String getTabName() {
        return "Explorer";
    }

    public void goToURI(File file){
        av.goToURI(file);
    }

    public AbstractViewer getAV(){
        return av;
    }

    public void addToFavorites(Vector<File> files){
        //do add
        if(files!=null){
            for(File file : files){
                BlueXStatics.fav.add(file.getAbsolutePath());
            }
        }
        BlueXStatics.fav.store(new File(FileModule.FAVORITES_FILE));

        //refresh
        initFavorites();
    }

    public void removeFromFavorites(File file){
        //do remove
        if(file!=null){
            int choice=JOptionPane.showConfirmDialog(bw, "<html><body>Do you want to remove <b>"+file.getName()+"" +
                    "</b><br/>from the Favorites Menu?", "Removing", JOptionPane.YES_NO_OPTION);

            if(choice==JOptionPane.YES_OPTION){
                BlueXStatics.fav.remove(file.getAbsolutePath());
                BlueXStatics.fav.store(new File(FileModule.FAVORITES_FILE));
            }
        }

        //refresh
        initFavorites();
    }
}
