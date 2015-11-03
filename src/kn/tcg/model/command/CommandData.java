package kn.tcg.model.command;

import java.util.Arrays;
import java.util.List;

public class CommandData {
    private String[] actionCommands = {"0", "1", "2", "3", "4", "5", "6", "exit"};
    private String[] standbyCommands = {"0", "1", "2", "3", "4", "5", "6", "exit", "quit", "attack", "heal", "end", "x", "q", "a", "h"};
    private String[] startCommands = {"0", "1", "start", "quit", "s", "q"};

    public List getActionCommands(){
        return Arrays.asList(actionCommands);
    }

    public List getStandbyCommands(){
        return Arrays.asList(standbyCommands);
    }

    public List getStartCommands(){
        return Arrays.asList(startCommands);
    }
}
