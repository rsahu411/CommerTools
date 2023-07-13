package com.CommerceTool.Stores;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.store.Store;
import com.commercetools.api.models.store.StoreDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreRepository {

    @Autowired
    private ProjectApiRoot apiRoot;
    public Store createStore(StoreDraft storeDraft) {

       return apiRoot
               .stores()
               .post(storeDraft)
               .executeBlocking()
               .getBody();
    }
}
