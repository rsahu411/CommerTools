package com.CommerceTool.Channels;

import com.commercetools.api.models.channel.Channel;
import com.commercetools.api.models.channel.ChannelDraft;
import com.commercetools.api.models.channel.ChannelRoleEnum;
import com.commercetools.api.models.common.LocalizedString;
import org.springframework.beans.factory.annotation.Autowired;

public class ChannelService {

    @Autowired
    private ChannelRepository repository;

    public Channel createChannel(ChannelDTO channelDTO)
    {
        ChannelDraft channel = ChannelDraft
                .builder()
                .key(channelDTO.getKey())
                .roles(ChannelRoleEnum.findEnum(channelDTO.getRole()))
                .name(LocalizedString.ofEnglish(channelDTO.getName()))
                .description(LocalizedString.ofEnglish(channelDTO.getDescription()))
                .build();

        return  repository.createChannel(channel);

    }



}
