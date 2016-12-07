package de.dhbw.backendTasks.userparty;

import de.dhbw.model.CommitmentState;
import de.dhbw.model.Party;

/**
 * Created by Tobias Berner on 24.11.2016.
 */

public interface SetCommitmentState {

    void onSuccessCommitmentState(CommitmentState newCommitmentState);

    void onFailCommitmentState();

}
