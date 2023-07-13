package com.CommerceTool.Channels;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.channel.Channel;
import com.commercetools.api.models.channel.ChannelDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelRepository {

    @Autowired
    private ProjectApiRoot apiRoot;

    public Channel createChannel(ChannelDraft channel) {
        return  apiRoot
                .channels()
                .post(channel)
                .executeBlocking()
                .getBody();
    }
}
