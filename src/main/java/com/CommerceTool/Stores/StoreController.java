package com.CommerceTool.Stores;


import com.commercetools.api.models.store.Store;
import com.commercetools.api.models.store.StorePagedQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService service;

    // Create Store
    @PostMapping
    public Store createStore(@RequestBody StoreDTO storeDTO)
    {
        return service.createStore(storeDTO);
    }


    // Update Store By Id
    @PostMapping("/updates/{storeId}")
    public Store updateStore(@RequestBody StoreDTO storeDTO,@PathVariable String storeId)
    {
        return service.updateStore(storeDTO,storeId);
    }


    // Query Stores
    @GetMapping
    public StorePagedQueryResponse getAllStores(@RequestParam(defaultValue = "20",required = false) String limit)
    {
        return service.getAllStores(limit);
    }


    // Get Store By id
    @GetMapping("/{storeId}")
    public Store getStoreById(@PathVariable String storeId)
    {
        return service.getAllStoreById(storeId);
    }



    // Get Store By id
    @DeleteMapping("/{storeId}")
    public Store deleteStoreById(@PathVariable String storeId,@RequestParam(defaultValue = "1")String version)
    {
        return service.deleteStoreById(storeId,version);
    }
}
