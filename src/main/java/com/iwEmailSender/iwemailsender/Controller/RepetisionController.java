package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetisionDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RepetisionDtoInsert;
import com.iwEmailSender.iwemailsender.Service.RepetisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repetisions")
public class RepetisionController {

    private final RepetisionService repetisionService;


    public RepetisionController(RepetisionService repetisionService) {
        this.repetisionService = repetisionService;
    }

    @GetMapping("")
    public List<RepetisionDto> findAllDto(){
        return repetisionService.findAll();
    }

    @GetMapping("/{id}")
    public RepetisionDto findByIdDto(@PathVariable Long id){
        return repetisionService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<String> addNewRepetision(@RequestBody RepetisionDtoInsert repetisionDtoInsert){
        repetisionService.addRepetision(repetisionDtoInsert);
        return new ResponseEntity<>("Successfully added new repetision", HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteRepetision(@PathVariable Long id){
        repetisionService.deleteRepetision(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);

    }

}
