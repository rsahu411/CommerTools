package com.CommerceTool.StateMachine;

import com.commercetools.api.models.state.State;
import com.commercetools.api.models.state.StatePagedQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/states")
public class StateControl {

    @Autowired
    StateService service ;

    @PostMapping
    public State createState(@RequestBody StateDTO stateDTO)
    {
        return service.createState(stateDTO);
    }

    @GetMapping
    public StatePagedQueryResponse getAllState()
    {
        return service.getAllSate();
    }

    @DeleteMapping("/{stateId}")
    public void deleteState(@PathVariable String stateId, @RequestParam(required = true,defaultValue = "1L") long version)
    {
        service.deleteState(stateId,version);
    }


    // Set Transitions
    @PostMapping("/set-transition/{stateId}")
    public State setTransition(@RequestBody StateDTO stateDTO, @PathVariable String stateId)
    {
        return service.setTransition(stateDTO,stateId);
    }
}
