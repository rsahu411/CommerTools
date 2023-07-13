package com.CommerceTool.Stores;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {

    private String name;
    private String key;
    private List<String> languages;
    private List<DistributionChannelDTO> distributionChannel;
    private List<SupplyChannelDto> supplyChannel;
    private List<CountriesDTO> country;
    private List<ProductSelectionDTO> productSelection;
    private List<ActionDTO> actions;
}
