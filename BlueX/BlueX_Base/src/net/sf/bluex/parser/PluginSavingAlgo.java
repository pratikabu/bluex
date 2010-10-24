package net.sf.bluex.parser;

import java.io.PrintWriter;
import java.util.Vector;
import net.sf.bluex.plugin.Dependency;
import net.sf.bluex.plugin.MenuEntry;
import net.sf.bluex.plugin.ModuleDetail;
import net.sf.bluex.plugin.OtherEntry;
import net.sf.bluex.plugin.PluginMetaData;
import newComponents.DateSelector;

/**
 *
 * @author Blue
 */
class PluginSavingAlgo {

    private Vector<PluginMetaData> vectPMD = new Vector<PluginMetaData>();
    private PrintWriter pw;

    public PluginSavingAlgo(PrintWriter pw) {
        this.pw = pw;
    }

    public PluginSavingAlgo(Vector<PluginMetaData> vectPMD, PrintWriter pw) {
        this.vectPMD = vectPMD;
        this.pw = pw;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }

    public Vector<PluginMetaData> getVectPMD() {
        return vectPMD;
    }

    public void addPlugin(PluginMetaData pmd) {
        if (vectPMD != null) {
            vectPMD.add(pmd);
        }
    }

    public boolean save() {
        boolean saved = false;
        try {
            for (PluginMetaData pmd : vectPMD) {
                pw.println("<plugin>");
                pw.println("<!--general information about the plugin-->");
                pw.println("<name>" + pmd.getName() + "</name>");
                pw.println("<size>" + pmd.getSize() + "</size>");
                pw.println("<author>" + pmd.getAuthor() + "</author>");
                pw.println("<release-date>" + DateSelector.getDateShowable(pmd.getReleaseDate()) + "</release-date>");
                pw.println("<version>" + pmd.getVersion() + "</version>");
                pw.println("<jar-file-name>" + pmd.getJarFileName() + "</jar-file-name>");
                pw.println("<plugin-class>" + pmd.getPluginClass() + "</plugin-class>");
                pw.println("<help-available>" + pmd.isHelpAvailable() + "</help-available>");
                pw.println("<plugin-description>" + pmd.getPluginDescription() + "</plugin-description>");
                pw.println("<changes>");
                for (String change : pmd.getChanges()) {
                    pw.println("<entry>" + change + "</entry>");
                }
                pw.println("</changes>");
                pw.println("<minimum-base-window-version>" + pmd.getMinimumBaseWindowVersion() + "</minimum-base-window-version>");
                for (Dependency dep : pmd.getDependencies()) {
                    pw.println("<dependency>");
                    pw.println("<dependency-jar-file-name>" + dep.getJarFileName() + "</dependency-jar-file-name>");
                    pw.println("<minimum-dependency-version>" + dep.getMinimumDependencyVersion().toString() + "</minimum-dependency-version>");
                    pw.println("</dependency>");
                }
                for (OtherEntry oe : pmd.getOtherEntries()) {
                    pw.println("<other-entry>");
                    pw.println("<plugin-name>" + oe.getPluginName() + "</plugin-name>");
                    for (MenuEntry me : oe.getMenu()) {
                        pw.println("<menu>");
                        pw.println("<location>" + me.getLocation() + "</location>");
                        pw.println("<text>" + me.getText() + "</text>");
                        pw.println("<event-class>" + me.getEventClass() + "</event-class>");
                        pw.println("</menu>");
                    }
                    pw.println("</other-entry>");
                }
                for (ModuleDetail md : pmd.getModuleInstalled()) {
                    pw.println("<module-installed>");
                    pw.println("<mi-type>" + md.getModuleType() + "</mi-type>");
                    if (md.getModuleType() == md.FILE_ASSOCIATION_PLUGIN) {
                        for (String extension : md.getMappingExtension()) {
                            pw.println("<mapping-extension>" + extension + "</mapping-extension>");
                        }
                    }
                    pw.println("</module-installed>");
                }
                pw.println("</plugin>");
            }
            saved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saved;
    }
}
