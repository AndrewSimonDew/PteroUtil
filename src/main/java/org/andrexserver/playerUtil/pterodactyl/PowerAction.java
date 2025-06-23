package org.andrexserver.playerUtil.pterodactyl;

public enum PowerAction {
    START("start"), STOP("stop"), RESTART("restart");
    private final String actionName;
    PowerAction(String actionName) {
        this.actionName = actionName;
    }
    public String getActionName() {
        return actionName;
    }
}

