package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetitionDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RepetitionDtoInsert;
import com.iwEmailSender.iwemailsender.Service.RepetitionService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/repetitions")
public class RepetitionController {
    private final RepetitionService repetitionService;
    public RepetitionController(RepetitionService repetitionService) {
        this.repetitionService = repetitionService;
    }
    @GetMapping("")
    public ResponseEntity<List<RepetitionDto>> findAll() {
        List<RepetitionDto> list= repetitionService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RepetitionDto> findById(@PathVariable Long id) {
        RepetitionDto repetitionDto= repetitionService.findById(id);
        return new ResponseEntity<>(repetitionDto,HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<String> addNewRepetition(@RequestBody RepetitionDtoInsert repetitionDtoInsert){
        repetitionService.addRepetition(repetitionDtoInsert);
        return new ResponseEntity<>("Successfully added new repetition", HttpStatus.CREATED);
    }
    @DeleteMapping("/{uuid}")
    private ResponseEntity<?> deleteRepetition(@PathVariable UUID uuid) throws BadRequestException {
        repetitionService.deleteRepetition(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
