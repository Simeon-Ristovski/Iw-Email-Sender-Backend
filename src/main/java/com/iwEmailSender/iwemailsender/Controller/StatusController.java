package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetitionDto;
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
    public ResponseEntity<List<StatusDto>> findAll() {
        List<StatusDto> list= statusService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StatusDto> findById(@PathVariable Long id) {
        StatusDto statusDto= statusService.findById(id);
        return new ResponseEntity<>(statusDto,HttpStatus.OK);
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
