package de.dhbw.backendTasks.party;

import de.dhbw.model.PartyDisplay;

/**
 * Created by Tobias Berner on 20.11.2016.
 */

public interface AdressValidate {

    void onSuccessAdressValidate(PartyDisplay partyDisplay);

    void onFailAdressValidate(PartyDisplay partyDisplay);


}
