package de.dhbw.backendTasks.party;

import de.dhbw.model.Party;
import de.dhbw.model.PartyDisplay;

/**
 * Created by Tobias Berner on 06.11.2016.
 */

public interface PostParty {
    void onSuccessPostParty(Party party);

    void onFailPostParty(PartyDisplay party);
}
