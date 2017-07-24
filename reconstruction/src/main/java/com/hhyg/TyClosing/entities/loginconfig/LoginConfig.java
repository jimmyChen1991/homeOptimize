package com.hhyg.TyClosing.entities.loginconfig;

/**
 * Created by user on 2017/6/27.
 */

public class LoginConfig {
    private boolean card_active;
    private boolean privilege_active;

    public boolean isCard_active() {
        return card_active;
    }

    public void setCard_active(boolean card_active) {
        this.card_active = card_active;
    }

    public boolean isPrivilege_active() {
        return privilege_active;
    }

    public void setPrivilege_active(boolean privilege_active) {
        this.privilege_active = privilege_active;
    }
}
