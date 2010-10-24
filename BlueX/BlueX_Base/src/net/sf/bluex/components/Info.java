/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

/**
 *
 * @author Blue
 */
public class Info {
    private String message, description;

    public Info(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }
}
