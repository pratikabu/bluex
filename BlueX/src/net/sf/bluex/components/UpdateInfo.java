package net.sf.bluex.components;

import java.io.Serializable;

/**
 *
 * @author Blue
 */
public class UpdateInfo implements Serializable{
    private int updateType;
    private String name, verison;

    public static final int BASE_WINDOW_UPDATE=1, PLUGIN_UPDATE=2, NEW_PLUGIN_UPDATE=3;

    public UpdateInfo(int updateType, String name, String verison) {
        this.updateType = updateType;
        this.name = name;
        this.verison = verison;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public String getVerison() {
        return verison;
    }

    public void setVerison(String verison) {
        this.verison = verison;
    }
}
