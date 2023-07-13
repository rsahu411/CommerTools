package com.CommerceTool.StateMachine;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.state.State;
import com.commercetools.api.models.state.StateDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateRepository {

    @Autowired
    ProjectApiRoot apiRoot;

    public State createState(StateDraft stateDraft) {

        return apiRoot.states().post(stateDraft).executeBlocking().getBody();
    }
}
