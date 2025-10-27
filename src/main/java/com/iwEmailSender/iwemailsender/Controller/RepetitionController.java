package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetitionDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RepetitionDtoInsert;
import com.iwEmailSender.iwemailsender.Service.RepetitionService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repetitions")
public class RepetitionController {

    private final RepetitionService repetitionService;


    public RepetitionController(RepetitionService repetitionService) {
        this.repetitionService = repetitionService;
    }

    @GetMapping("")
    public List<RepetitionDto> findAllDto(){
        return repetitionService.findAll();
    }

    @GetMapping("/{id}")
    public RepetitionDto findByIdDto(@PathVariable Long id){
        return repetitionService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<String> addNewRepetition(@RequestBody RepetitionDtoInsert repetitionDtoInsert){
        repetitionService.addRepetition(repetitionDtoInsert);
        return new ResponseEntity<>("Successfully added new repetition", HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteRepetition(@PathVariable Long id) throws BadRequestException {
        repetitionService.deleteRepetition(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);

    }

}
