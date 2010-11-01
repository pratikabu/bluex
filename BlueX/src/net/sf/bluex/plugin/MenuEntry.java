/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.plugin;

/**
 *
 * @author Blue
 */
public class MenuEntry {
    private String location;
    private String text;
    private String eventClass;

    public String getEventClass() {
        return eventClass;
    }

    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    public String getText() {
        return text;
    }

    public void setText(String pluginName) {
        this.text = pluginName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return getLocation()+", "+getText()+", "+getEventClass();
    }
}
