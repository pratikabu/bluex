/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.fileViewer;

import java.io.File;

/**
 *
 * @author Blue
 */
public interface FileFilter {
    public boolean accept(File currentFile);
}
