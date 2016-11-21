package de.dhbw.backendTasks.party;

import de.dhbw.model.PartyDisplay;

/**
 * Created by Tobias Berner on 20.11.2016.
 */

public interface AdressValidate {

    void onSuccessAddressValidate(PartyDisplay partyDisplay);

    void onFailAddressValidate(PartyDisplay partyDisplay);


}
