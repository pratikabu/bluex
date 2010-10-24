/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

/**
 *
 * @author Blue
 */
public class SeletionDetail {
    private long totalSize,filesCount,foldersCount;
    
    public long getFilesCount(){
        return filesCount;
    }
    
    public void incrFilesCount(){
        filesCount++;
    }
    
    public long getFoldersCount(){
        return foldersCount;
    }
    
    public void incrFoldersCount(){
        foldersCount++;
    }
    
    public long getTotalSize(){
        return totalSize;
    }
    
    public void addSize(long size){
        totalSize+=size;
    }

    public void addSelectionDetail(SeletionDetail sd){
        this.filesCount+=sd.filesCount;
        this.foldersCount+=sd.foldersCount;
        this.totalSize+=sd.totalSize;
    }
}
