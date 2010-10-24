/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.plugin;

/**
 *
 * @author Blue
 */
public class Dependency {
    private String jarFileName;
    private Version minimumDependencyVersion;

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public Version getMinimumDependencyVersion() {
        return minimumDependencyVersion;
    }

    public void setMinimumDependencyVersion(Version minimumDependencyVersion) {
        this.minimumDependencyVersion = minimumDependencyVersion;
    }

    @Override
    public String toString() {
        return getJarFileName()+", Min Ver: "+getMinimumDependencyVersion();
    }
}
