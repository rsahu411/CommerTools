package com.CommerceTool.StateMachine;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.state.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    @Autowired
    StateRepository repository;

    @Autowired
    ProjectApiRoot apiRoot;


    public State createState(StateDTO stateDTO)
    {


        StateDraft stateDraft = StateDraft
                .builder()
                .key(stateDTO.getKey())
                .type(stateDTO.getType())
             //   .name(LocalizedString.ofEnglish(stateDetails.getName()))
                .roles(stateDTO.getRoles())
                .initial(stateDTO.isInitial())
                .build();

        return repository.createState(stateDraft);
    }



    public StatePagedQueryResponse getAllSate() {

        StatePagedQueryResponse state = apiRoot
                .states()
                .get()
                .executeBlocking()
                .getBody();

        return state;
    }


    public void deleteState(String stateId,long version)
    {
        State state = apiRoot
                .states()
                .withId(stateId)
                .delete(version)
                .executeBlocking()
                .getBody();

    }



    // Set Transitions
    public State setTransition(StateDTO stateDTO, String id)
    {
        State state = apiRoot.states().withId(id).get().executeBlocking().getBody();

        StateUpdate stateUpdate = StateUpdate
                .builder()
                .version(state.getVersion())
                .actions(StateUpdateAction.setTransitionsBuilder()
                        .transitions(stateDTO.getTransitions())
                        .build())
                .build();

        State updatedState = apiRoot
                .states()
                .withId(state.getId())
                .post(stateUpdate)
                .executeBlocking()
                .getBody();
        return updatedState;
    }
}
