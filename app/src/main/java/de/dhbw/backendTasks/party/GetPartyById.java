package de.dhbw.backendTasks.party;

import de.dhbw.model.Party;

/**
 * Created by Tobias Berner on 06.11.2016.
 */

public interface GetPartyById {
    void onSuccessGetPartyById(Party party);

    void onFailGetPartyById(Party party);
}
