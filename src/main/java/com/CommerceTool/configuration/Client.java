package com.CommerceTool.configuration;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Client {



//    @Bean
//    public static ProjectApiRoot createApiClient() {
//        final ProjectApiRoot apiRoot = ApiRootBuilder.of()
//                .defaultClient(ClientCredentials.of()
//                                .withClientId("R2Ej6YdxloABZhyhyI1cdK4k")
//                                .withClientSecret("Te3RagwOOmAMHDfgZVJOGPtdou3aLfyc")
//                                .build(),
//                        ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1)
//                .build("rishabhjune_ct");
//
//        return apiRoot;
//    }


    @Bean
    public static ProjectApiRoot createApiClient() {
        final ProjectApiRoot apiRoot = ApiRootBuilder.of()
                .defaultClient(ClientCredentials.of()
                                .withClientId("fhoofBcLHlxxsiFnivje37ld")
                                .withClientSecret("LAhSxo3dDP_3yC0uQ45P_X9pTmtn6ZtJ")
                                .build(),
                        ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1)
                .build("myntra-copy");

        return apiRoot;
    }



}
