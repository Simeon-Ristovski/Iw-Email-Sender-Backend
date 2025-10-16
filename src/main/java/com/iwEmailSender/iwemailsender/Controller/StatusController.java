package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.StatusDto;
import com.iwEmailSender.iwemailsender.Dto.Input.StatusDtoInsert;

import com.iwEmailSender.iwemailsender.Service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statuses")
public class StatusController {

    private final StatusService statusService;


    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("")
    public List<StatusDto> findAllDto(){
        return statusService.findAll();
    }
    @GetMapping("/{id}")
    public StatusDto findByIdDto(@PathVariable Long id){
        return statusService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<String> addNewStatus(@RequestBody StatusDtoInsert statusDtoInsert){
            statusService.addStatus(statusDtoInsert);
        return new ResponseEntity<>("Successfully added Status", HttpStatus.CREATED);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deleteStatus(@PathVariable Long id)  {
            statusService.deleteStatus(id);
            return new ResponseEntity<>("Successfully deleted Status", HttpStatus.OK);
    }

}
