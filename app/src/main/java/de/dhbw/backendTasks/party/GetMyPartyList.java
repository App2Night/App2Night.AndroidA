package de.dhbw.backendTasks.party;

import de.dhbw.model.Party;

/**
 * Created by Tobias Berner on 22.11.2016.
 */

public interface GetMyPartyList {
    void onSuccessGetMyPartyList(Party parties);

    void onFailGetMyPartyList();

}
