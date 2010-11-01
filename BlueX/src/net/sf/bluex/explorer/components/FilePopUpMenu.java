
package net.sf.bluex.explorer.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.boundary.OpenWith;
import net.sf.bluex.components.BlueXFile;
import net.sf.bluex.explorer.boundary.CopyTo;

/**
 *
 * @author Blue
 */
public class FilePopUpMenu extends JPopupMenu implements ActionListener{
    private JMenuItem pmnFLCopy;
    private JMenuItem pmnFLPaste;
    private JMenuItem pmnFLOpen;
    private JMenuItem pmnFLDelete;
    private JMenuItem pmnFLAddToScanBasket;
    private JMenuItem pmnFLProcess;
    private JMenuItem pmnFLRename;
    private JMenuItem pmnFLProperties;
    private JMenuItem pmnFLRequestOS;
    private JMenuItem pmnFLSendTo;
    private JMenuItem pmnFLAddToFavorites;
    private JMenuItem pmnFLOpenInAnotherInstance;
    private JMenuItem pmnFLOpenWith;

    //other menu
    private JMenuItem jpmAddToFavorites, jpmNewFolder, jpmRefresh, jpmPaste;

    //file
    private JMenuItem jpmRemoveFromFavorites;

    private int viewType=1;

    private Vector<Component> vectComponents=new Vector<Component>();

    //abstract viewer object
    private AbstractViewer av;

    //or
    private File file;

    private FilePopUpMenu(AbstractViewer av){
        this.av=av;
        if(av.getSelectedFiles()==null || av.getSelectedFiles().size()==0)
            viewType=2;
        init();
    }

    private FilePopUpMenu(AbstractViewer av, File file){
        this.av=av;
        this.file=file;
        viewType=3;
        init();
    }

    private void init(){
        initObects();
        placeObjects();
    }

    private void initObects() {
        if(viewType==1){
            pmnFLOpen=new JMenuItem("<html><body><b>Open</b></body></html>");
            pmnFLOpen.setMnemonic('O');
            pmnFLOpen.addActionListener(this);

            pmnFLCopy=new JMenuItem("Copy", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("copy.gif", 2));
            pmnFLCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
            pmnFLCopy.setMnemonic('C');
            pmnFLCopy.addActionListener(this);

            pmnFLPaste=new JMenuItem("Paste", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("paste.gif", 2));
            pmnFLPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
            pmnFLPaste.setMnemonic('P');
            pmnFLPaste.addActionListener(this);
            pmnFLPaste.setEnabled(av.hasPasteItems());

            pmnFLDelete=new JMenuItem("Delete", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("delete.gif", 2));
            pmnFLDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
            pmnFLDelete.setMnemonic('D');
            pmnFLDelete.addActionListener(this);

            pmnFLAddToScanBasket=new JMenuItem("Add to Scan Basket", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("addToScanBasket.gif", 2));
            pmnFLAddToScanBasket.setMnemonic('A');
            pmnFLAddToScanBasket.addActionListener(this);

            pmnFLProcess=new JMenuItem("Process Scan Basket", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("process.gif", 2));
            pmnFLProcess.setMnemonic('y');
            pmnFLProcess.addActionListener(this);

            pmnFLRename=new JMenuItem("Rename");
            pmnFLRename.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
            pmnFLRename.setMnemonic('R');
            pmnFLRename.addActionListener(this);

            pmnFLProperties=new JMenuItem("Properties");
            pmnFLProperties.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.ALT_MASK));
            pmnFLProperties.setMnemonic('s');
            pmnFLProperties.addActionListener(this);

            pmnFLRequestOS=new JMenuItem("Request OS To Run", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("requestOS.gif", 2));
            pmnFLRequestOS.setMnemonic('T');
            pmnFLRequestOS.addActionListener(this);

            //jmenu
            pmnFLSendTo=new JMenuItem("Copy To...");
            pmnFLSendTo.setMnemonic('T');
            pmnFLSendTo.addActionListener(this);

            pmnFLAddToFavorites=new JMenuItem("Add To Favorites");
            pmnFLAddToFavorites.setMnemonic('F');
            pmnFLAddToFavorites.addActionListener(this);

            pmnFLOpenInAnotherInstance=new JMenuItem("Open in Another Instance");
            pmnFLOpenInAnotherInstance.setMnemonic('I');
            pmnFLOpenInAnotherInstance.addActionListener(this);

            pmnFLOpenWith=new JMenuItem("Open With...");
            pmnFLOpenWith.setMnemonic('W');
            pmnFLOpenWith.addActionListener(this);
        }else if(viewType==2){
            jpmAddToFavorites=new javax.swing.JMenuItem("Add Current Folder to Favorites");
            jpmAddToFavorites.addActionListener(this);

            jpmNewFolder=new javax.swing.JMenuItem("New Folder");
            jpmNewFolder.addActionListener(this);

            jpmRefresh=new javax.swing.JMenuItem("Refresh");
            jpmRefresh.addActionListener(this);

            jpmPaste=new javax.swing.JMenuItem("Paste", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("paste.gif", 2));
            jpmPaste.addActionListener(this);
            jpmPaste.setEnabled(av.hasPasteItems());
        }else{
            pmnFLPaste=new JMenuItem("Paste here", net.sf.bluex.controller.UsefulMethods.getIconFromIconSet("paste.gif", 2));
            pmnFLPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
            pmnFLPaste.setMnemonic('P');
            pmnFLPaste.addActionListener(this);
            pmnFLPaste.setEnabled(av.hasPasteItems());

            jpmRemoveFromFavorites=new JMenuItem("Remove From Favorites");//,net.sf.bluex.controller.UsefulMethods.getIcon("paste.gif"));
            jpmRemoveFromFavorites.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
            jpmRemoveFromFavorites.setMnemonic('R');
            jpmRemoveFromFavorites.addActionListener(this);
            jpmRemoveFromFavorites.setEnabled(file!=null);
        }
    }

    private void placeObjects() {
        if(viewType==1){
            vectComponents.add(pmnFLOpen);
            if(av.getSelectedFile().isDirectory())
                vectComponents.add(pmnFLOpenInAnotherInstance);
            vectComponents.add(pmnFLRequestOS);
            if(av.getSelectedFile().isDirectory())
                vectComponents.add(pmnFLAddToFavorites);
            vectComponents.add(new JSeparator());
            if(av.getSelectedFile().isFile())
                vectComponents.add(pmnFLOpenWith);
            vectComponents.add(pmnFLSendTo);
            vectComponents.add(new JSeparator());
            vectComponents.add(pmnFLCopy);
            vectComponents.add(pmnFLPaste);
            vectComponents.add(new JSeparator());
            vectComponents.add(pmnFLDelete);
            vectComponents.add(pmnFLRename);
            vectComponents.add(new JSeparator());
            vectComponents.add(pmnFLAddToScanBasket);
            vectComponents.add(pmnFLProcess);
            vectComponents.add(new JSeparator());
            vectComponents.add(pmnFLProperties);
        }else if(viewType==2){
            vectComponents.add(jpmAddToFavorites);
            vectComponents.add(jpmNewFolder);
            vectComponents.add(jpmRefresh);
            vectComponents.add(new JSeparator());
            vectComponents.add(jpmPaste);
        }else{
            vectComponents.add(pmnFLPaste);
            vectComponents.add(new JSeparator());
            vectComponents.add(jpmRemoveFromFavorites);
        }

        for(Component cmp : vectComponents)
            this.add(cmp);
    }

    public void actionPerformed(ActionEvent e) {
        Object but=e.getSource();

        if(but==pmnFLDelete)
            av.delete();
        else if((but==pmnFLOpen))
            av.open();
        else if(but==pmnFLAddToFavorites)
            av.getBw().getExplorer().addToFavorites(av.getSelectedFiles());
        else if((but==pmnFLCopy))
            av.copy();
        else if(but==pmnFLPaste){
            if(viewType==1)
                av.paste();
            else
                av.paste(file);
        }else if(but==pmnFLRequestOS)
            av.requestOSToRun();
        else if(but==pmnFLRename)
            av.rename();
        else if(but==pmnFLProperties)
            av.showProperties();
        else if(but==pmnFLAddToScanBasket)
            av.getBw().getExplorer().doAddToScanBasketJob();
        else if(but==pmnFLProcess)
            av.getBw().getExplorer().doProcessJob();
        else if(but==pmnFLSendTo)
            CopyTo.showCopytoBox(av);
        else if(but==jpmPaste)
            av.paste();
        else if(but==jpmAddToFavorites){
            if(av.getCurrentURI()!=null){
                Vector<File> vect=new Vector<File>();
                vect.add(av.getCurrentURI());
                av.getBw().getExplorer().addToFavorites(vect);
            }
        }else if(but==jpmRefresh)
            av.refresh();
        else if(but==jpmNewFolder)
            av.newFolder();
        else if(but==jpmPaste)
            av.paste();
        else if(but==jpmRemoveFromFavorites)
            av.getBw().getExplorer().removeFromFavorites(file);
        else if(but==pmnFLOpenInAnotherInstance){
            for(File temp : av.getSelectedFiles()){
                if(temp.isDirectory())
                    new BaseWindow(temp);
            }
        }else if(but==pmnFLOpenWith){
            OpenWith.showOpenWith(av.getBw(), new BlueXFile(av.getSelectedFile()));
        }
    }

    public static FilePopUpMenu getPopupMenu(AbstractViewer av){
        return new FilePopUpMenu(av);
    }

    public static FilePopUpMenu getPopupMenu(AbstractViewer av, File file){
        return new FilePopUpMenu(av, file);
    }

    public static Vector<Component> getComponentsAdded(AbstractViewer av){
        return new FilePopUpMenu(av).vectComponents;
    }
}
