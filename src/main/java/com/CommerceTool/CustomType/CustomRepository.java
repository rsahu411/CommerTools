package com.CommerceTool.CustomType;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.type.Type;
import com.commercetools.api.models.type.TypeDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomRepository {

    @Autowired
    ProjectApiRoot apiRoot;

    public Type createCustomType(TypeDraft typeDraft) {
        return apiRoot.types().post(typeDraft).executeBlocking().getBody();
    }
}
