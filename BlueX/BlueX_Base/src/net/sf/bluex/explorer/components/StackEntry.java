/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.components;

import java.io.File;

/**
 *
 * @author Blue
 */
public class StackEntry {
    private File file;
    private int pos;

    public StackEntry(File file, int pos) {
        this.file = file;
        this.pos = pos;
    }

    public File getFile() {
        return file;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String toString() {
        if(file!=null)
            return file.toString();
        else
            return "null";
    }
}
