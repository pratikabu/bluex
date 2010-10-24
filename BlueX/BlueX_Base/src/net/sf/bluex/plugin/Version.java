/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.plugin;

import java.util.StringTokenizer;

/**
 *
 * @author Blue
 */
public class Version {
    private int archRev, internalRev, minorRev;

    public Version() {
        //dummy
    }

    public Version(String version){
        StringTokenizer st=new StringTokenizer(version, ".");
        
        archRev=Integer.parseInt(st.nextToken());
        internalRev=Integer.parseInt(st.nextToken());
        minorRev=Integer.parseInt(st.nextToken());
    }

    public Version(int archRev, int internalRev, int minorRev) {
        this.archRev = archRev;
        this.internalRev = internalRev;
        this.minorRev = minorRev;
    }

    public int getArchRev() {
        return archRev;
    }

    public void setArchRev(int archRev) {
        this.archRev = archRev;
    }

    public int getInternalRev() {
        return internalRev;
    }

    public void setInternalRev(int internalRev) {
        this.internalRev = internalRev;
    }

    public int getMinorRev() {
        return minorRev;
    }

    public void setMinorRev(int minorRev) {
        this.minorRev = minorRev;
    }

    @Override
    public String toString() {
        return archRev+"."+internalRev+"."+minorRev;
    }

    public static final int GREATER=1, EQUALS=0, LESSER=-1;
    /**
     * implied by its name it compares version1 with version2 and give results GREATER, EQUAL, LESSER
     * @param ver1
     * @param ver2
     * @return
     */
    public static int compareVersions(Version ver1, Version ver2){
        //check for updates
        if(ver1.getArchRev()>ver2.getArchRev())
            return GREATER;
        else if(ver1.getArchRev()==ver2.getArchRev()){
            if(ver1.getInternalRev()>ver2.getInternalRev())
                return GREATER;
            else if(ver2.getInternalRev()==ver1.getInternalRev()){
                if(ver1.getMinorRev()>ver2.getMinorRev())
                    return GREATER;
                else if(ver1.getMinorRev()==ver2.getMinorRev())
                    return EQUALS;
            }
        }
        return LESSER;
    }
}
