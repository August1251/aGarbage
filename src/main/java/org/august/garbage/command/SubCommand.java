package org.august.garbage.command;

public abstract class SubCommand {

    public abstract void execute();
    public abstract String getCommandName();
    public abstract String getPermission();

}