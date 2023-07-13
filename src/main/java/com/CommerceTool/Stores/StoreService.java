package com.CommerceTool.Stores;

import com.CommerceTool.exceptions.InvalidActionException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.channel.ChannelResourceIdentifier;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product_selection.ProductSelectionResourceIdentifier;
import com.commercetools.api.models.store.*;
import com.commercetools.api.models.store_country.StoreCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository repository;
    @Autowired
    private ProjectApiRoot apiRoot;

    // Create Store
    public Store createStore(StoreDTO storeDTO) {

        StoreDraft storeDraft = StoreDraft
                .builder()
                .key(storeDTO.getKey())
                .name(LocalizedString.of(Locale.US,storeDTO.getName()))
                .languages(storeDTO.getLanguages())
                .countries(createStoreCountry(storeDTO))                   // list of country
                .distributionChannels(createDistributionChannel(storeDTO)) // list of distribution channel
                .supplyChannels(createSupplyChannel(storeDTO))             // list of supply channel
            //    .productSelections(createProductSelection(storeDTO))       // list of product selection
                .build();
        return repository.createStore(storeDraft);
    }



    // Update Store By Id
    public Store updateStore(StoreDTO storeDTO, String storeId)
    {
        Store store = apiRoot.stores().withId(storeId).get().executeBlocking().getBody();

        List<StoreUpdateAction> updateActions = storeDTO.getActions()
                .stream()
                .map(actionDTO ->{
                return switch (actionDTO.getAction()) {
                    case "addCountry" -> StoreUpdateAction
                            .addCountryBuilder()
                            .country(StoreCountry.builder().code(actionDTO.getCode()).build())
                            .build();
                    case "addDistributionChannel" -> StoreUpdateAction
                            .addDistributionChannelBuilder()
                            .distributionChannel(ChannelResourceIdentifier.builder().id(actionDTO.getId()).build())
                            .build();
                    case "addSupplyChannel" -> StoreUpdateAction
                            .addSupplyChannelBuilder()
                            .supplyChannel(ChannelResourceIdentifier.builder().id(actionDTO.getId()).build())
                            .build();
                    case "addProductSelection" -> StoreUpdateAction
                            .addProductSelectionBuilder()
                            .productSelection(ProductSelectionResourceIdentifier.builder().id(actionDTO.getId()).build())
                            .active(actionDTO.getActive())
                            .build();
                    default -> throw new InvalidActionException(actionDTO.getAction());
                };
                }
               ).collect(Collectors.toList());

        StoreUpdate storeUpdate = StoreUpdate
                .builder()
                .version(store.getVersion())
                .actions(updateActions)
                .build();
        Store updatedStore = apiRoot
                .stores()
                .withId(store.getId())
                .post(storeUpdate)
                .executeBlocking()
                .getBody();
        return updatedStore;
    }




    // List of Store Country
    public List<StoreCountry> createStoreCountry(StoreDTO storeDTO)
    {
        List<StoreCountry> storeCountry = storeDTO.getCountry()
                .stream()
                .map(countriesDTO -> StoreCountry.builder().code(countriesDTO.getCode()).build())
                .collect(Collectors.toList());
        return storeCountry;
    }

    // create Distribution Channel
    public List<ChannelResourceIdentifier> createDistributionChannel(StoreDTO storeDTO)
    {
        List<ChannelResourceIdentifier> distributionChannel = storeDTO.getDistributionChannel()
                .stream()
                .map(distributionChannelDTO -> {
                    return ChannelResourceIdentifier
                            .builder()
                            .id(distributionChannelDTO.getId())
                         //   .key(distributionChannelDTO.getKey())
                            .build();
                })
                .collect(Collectors.toList());

        return distributionChannel;
    }


    // create Supply Channel
    public List<ChannelResourceIdentifier> createSupplyChannel(StoreDTO storeDTO)
    {
        List<ChannelResourceIdentifier> supplyChannel = storeDTO.getSupplyChannel()
                .stream()
                .map(supplyChannelDto -> {
                    return ChannelResourceIdentifier
                            .builder()
                            .id(supplyChannelDto.getId())
                        //    .key(supplyChannelDto.getKey())
                            .build();
                })
                .collect(Collectors.toList());

        return supplyChannel;
    }


    // create Product Selection
    public List<ProductSelectionSettingDraft> createProductSelection(StoreDTO storeDTO)
    {
        List<ProductSelectionSettingDraft> productSelection = storeDTO.getProductSelection()
                .stream()
                .map(productSelectionDTO -> {
                    return ProductSelectionSettingDraftBuilder
                            .of()
                            .productSelection(ProductSelectionResourceIdentifier
                                    .builder()
                                    .id(productSelectionDTO.getId())
                                    .build())
                            .build();
                })
                .collect(Collectors.toList());

        return productSelection;
    }



    // Query stores
    public StorePagedQueryResponse getAllStores(String limit)
    {
        StorePagedQueryResponse queryResponse = apiRoot
                .stores()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
        return queryResponse;
    }



    // Get Store By Id
    public Store getAllStoreById(String storeId)
    {
        Store store = apiRoot
                .stores()
                .withId(storeId)
                .get()
                .executeBlocking()
                .getBody();
        return store;
    }


    // Delete Store By Id
    public Store deleteStoreById(String storeId,String version)
    {
        Store store = apiRoot
                .stores()
                .withId(storeId)
                .delete(version)
                .executeBlocking()
                .getBody();
        return store;
    }
}