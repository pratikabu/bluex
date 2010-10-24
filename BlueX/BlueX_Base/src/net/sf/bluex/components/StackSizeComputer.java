/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

/**
 *
 * @author Blue
 */
public class StackSizeComputer {

    //thread related objects
    private boolean active=true;

    /**
     * this method will get the size of all the folders in the stack
     */
    public net.sf.bluex.components.SeletionDetail
            getAllFolderLengthOfStack(net.sf.bluex.components.Stack stData) {
        //creat a selection detail object so that multiple data could be stored
        net.sf.bluex.components.SeletionDetail sd=
                new net.sf.bluex.components.SeletionDetail();

        //create a new stack with the same enteries as in the passed stack
        net.sf.bluex.components.Stack stCopy=new net.sf.bluex.components.Stack();

        for(int i=0;i<stData.size();i++){//copy each element
            stCopy.push(stData.elementAt(i));
        }

        //create a fileSystemView object to detect whether it is a drive or folder so that appropriate tasks can be done
        javax.swing.filechooser.FileSystemView fsv=javax.swing.filechooser.FileSystemView.getFileSystemView();

        //file object
        java.io.File file;

        //long variable to store the size
        long totalSize=0;

        while(active && (file=(java.io.File)stCopy.pop())!=null){
            try{
                if(fsv.isDrive(file)){//ensures drive
                    totalSize+=file.getTotalSpace()-file.getFreeSpace();
                }
                else if(file.isDirectory()){//ensures drive
                    totalSize+=getFolderLength(file,sd);
                    sd.incrFoldersCount();
                }
                else{//ensure file
                    totalSize+=file.length();
                    sd.incrFilesCount();
                }
            }catch(Exception e){
                //any of the fsv exception should be catched
            }
        }

        sd.addSize(totalSize);

        //return selection detail
        return sd;
    }

    /**
     * This method will find the size of the provided folder in long type
     */
    public long getFolderLength(java.io.File parentFile,net.sf.bluex.components.SeletionDetail sd){
        //make the long variable which will conatain the information about the length
        long folSize=0;

        //make a statck
        net.sf.bluex.components.Stack stFolders=new net.sf.bluex.components.Stack();

        /////////////put the parentFile in the stack
        stFolders.push(parentFile);
        //////////////

        //do getSize operation

        /////////////////////////////////////////////////////////////////////////
        java.io.File parentFolder=null;
        while(active && (parentFolder=(java.io.File)stFolders.pop())!=null){

            //create a instance of files object which contains all files and folders for corresoponding parent folder
            java.io.File[] file=net.sf.bluex.controller.FolderIntruder.getFilesFolders(parentFolder);

            //if the file object is not null then go inside else outside return.
            if(file!=null){

                //do the job of iteration
                for(java.io.File tempFile : file){
                    if(!active)//break from the loop if active is false
                        break;

                    try{
                        if(tempFile.isFile()){//ensures file
                            folSize+=tempFile.length();
                            sd.incrFilesCount();
                        }
                        else if(tempFile.isDirectory()){//ensures folder
                            //add to stack
                            stFolders.push(tempFile);
                            sd.incrFoldersCount();
                        }
                    }catch(NullPointerException npe){
                        //pass the exception
                    }
                }
            }
        }
        //return the size of folder
        return folSize;
    }

    public void stop(){
        active=false;
    }

    public void activate(){
        active=true;
    }
}
