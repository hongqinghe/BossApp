package com.android.app.buystoreapp.other;

import java.io.Serializable;

/**
 * Created by 尚帅波 on 2016/9/23.
 */
public class AreaCmdBean implements Serializable {
    private static final long serialVersionUID = 4244426772118883470L;

    public AreaCmdBean(String cmd, int id, int level) {
        this.cmd = cmd;
        this.id = id;
        this.level = level;
    }

    /**
     * cmd : selectAddress
     * id : 210000
     * level : 2
     */

    private String cmd;
    private int id;
    private int level;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
