
package net.sf.bluex.plugin;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * This class contains the meta data associated with the plugin to be installed or
 * which is allready installed.
 * @author Blue
 */
public class PluginMetaData implements java.io.Serializable{

    /** The name of the plugin to be shown is stored here */
    private String name, author, jarFileName, pluginClass, pluginDescription;

    /** the size of the jar file */
    private int size;

    /** date when the plugin is released */
    private Date releaseDate;

    private Version version, minimumBaseWindowVersion;

    private boolean helpAvailable;

    private Vector<String> changes=new Vector<String>();;

    private Vector<Dependency> dependencies=new Vector<Dependency>();;

    private Vector<OtherEntry> otherEntries=new Vector<OtherEntry>();;

    private Vector<ModuleDetail> moduleInstalled=new Vector<ModuleDetail>();

    public static final String EXTENSION=".bxp";

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isHelpAvailable() {
        return helpAvailable;
    }

    public void setHelpAvailable(boolean helpAvailable) {
        this.helpAvailable = helpAvailable;
    }

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public Version getMinimumBaseWindowVersion() {
        return minimumBaseWindowVersion;
    }

    public void setMinimumBaseWindowVersion(Version minimumBaseWindowVersion) {
        this.minimumBaseWindowVersion = minimumBaseWindowVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPluginClass() {
        return pluginClass;
    }

    public void setPluginClass(String pluginClass) {
        this.pluginClass = pluginClass;
    }

    public String getPluginDescription() {
        return pluginDescription;
    }

    public void setPluginDescription(String pluginDescription) {
        this.pluginDescription = pluginDescription;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public boolean addChange(String change){
        if(change!=null && !change.equals("")){
            changes.add(change);
            return true;
        }
        return false;
    }

    public Vector<String> getChanges() {
        return changes;
    }

    public boolean addDependency(Dependency dependency){
        if(dependency!=null){
            dependencies.add(dependency);
            return true;
        }
        return false;
    }

    public Vector<Dependency> getDependencies() {
        return dependencies;
    }

    public boolean addModule(ModuleDetail md){
        if(md!=null){
            moduleInstalled.add(md);
            return true;
        }
        return false;
    }

    public Vector<ModuleDetail> getModuleInstalled() {
        return moduleInstalled;
    }

    public boolean addOtherEntry(OtherEntry oe){
        if(oe!=null){
            otherEntries.add(oe);
            return true;
        }
        return false;
    }

    public Vector<OtherEntry> getOtherEntries() {
        return otherEntries;
    }

    @Override
    public String toString(){
        StringBuffer sb=new StringBuffer("");
        sb.append("Plugin Name: "+getName()+"\n");
        DateFormat df=DateFormat.getDateInstance(DateFormat.MEDIUM);
        String relase="NA";
        if(getReleaseDate()!=null)
            relase=df.format(getReleaseDate());
        sb.append("Release Data: "+relase+"\n");
        sb.append("Author Name: "+getAuthor()+"\n\n");
        sb.append("Description\n");
        sb.append("----------------\n");
        sb.append(getPluginDescription()+"\n\n");
        if(getChanges().size()>0){
            sb.append("Changes:\n");
            sb.append("----------------");
            for(String change : getChanges())
                sb.append("\n-"+change);
        }
        sb.append("\n\nHelp Available: "+(isHelpAvailable()?"Yes":"No"));
        
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PluginMetaData){
            PluginMetaData pmd=(PluginMetaData)obj;
            if(this.getName().equals(pmd.getName()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
