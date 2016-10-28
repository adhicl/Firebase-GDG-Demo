package com.aldoapps.yetanothereventapp;

/**
 * Created by aldo on 10/29/16.
 */
public class DeleteMenuEvent {
    private RestoMenu restoMenu;

    public DeleteMenuEvent(RestoMenu restoMenu) {
        this.restoMenu = restoMenu;
    }

    public RestoMenu getRestoMenu() {
        return restoMenu;
    }
}
