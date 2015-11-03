package kn.tcg.model.actions;

import kn.tcg.model.exceptions.ActionException;
import kn.tcg.model.gamedata.data.GameData;

/**
 * Created by rait.avastu on 9/7/2015.
 */
public interface ActionsInterface {
    String performAction(GameData gameData, int command) throws ActionException;
}
