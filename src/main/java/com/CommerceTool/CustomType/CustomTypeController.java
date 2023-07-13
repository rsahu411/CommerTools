package com.CommerceTool.CustomType;

import com.commercetools.api.models.type.Type;
import com.commercetools.api.models.type.TypePagedQueryResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/custom-types")
public class CustomTypeController {


    @Autowired
    private CustomTypeService service;

    //Create Custom-Type
    @PostMapping
    public Type createCustomType(@RequestBody CustomTypeDTO customTypeDTO)
    {
        return service.createCustomType(customTypeDTO);
    }


    // Update the custom-type
    @PostMapping("/updates/{id}")
    public Type updateCustomType(@RequestBody CustomTypeDTO customTypeDTO, @PathVariable String id)
    {
        return service.updateCustomType(customTypeDTO,id);
    }




    // Get All Custom-Type
    @GetMapping
    public TypePagedQueryResponse getAllCustomType(@RequestParam String Limit)
    {
        return service.getAllCustomType(Limit);
    }




    // Get Custom-Type By id
    @GetMapping("/{id}")
    public Type getCustomTypeById(@PathVariable @NotNull String id)
    {
        return service.getCustomTypeById(id);
    }




    // Delete Custom-Type
    @DeleteMapping("/{id}")
    public Type deleteCustomType(@PathVariable String id,@RequestParam(required = false,defaultValue = "1") long version)
    {
        return service.deleteCustomType(id,version);
    }
}
