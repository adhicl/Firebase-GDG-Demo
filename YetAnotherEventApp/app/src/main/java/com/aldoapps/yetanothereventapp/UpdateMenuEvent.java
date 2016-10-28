package com.aldoapps.yetanothereventapp;

/**
 * Created by aldo on 10/29/16.
 */
public class UpdateMenuEvent {

    private RestoMenu restoMenu;

    public UpdateMenuEvent(RestoMenu restoMenu) {
        this.restoMenu = restoMenu;
    }

    public RestoMenu getRestoMenu() {
        return restoMenu;
    }

}
