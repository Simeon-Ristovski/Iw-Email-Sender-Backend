package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.ExeceptionEntityDto;
import com.iwEmailSender.iwemailsender.Service.ExceptionEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exceptions")
public class ExceptionEntityController {
    private final ExceptionEntityService exceptionEntityService;
    public ExceptionEntityController(ExceptionEntityService exceptionEntityService) {
        this.exceptionEntityService = exceptionEntityService;
    }
    @GetMapping("")
    public ResponseEntity<List<ExeceptionEntityDto>> findAll() {
        List<ExeceptionEntityDto> list= exceptionEntityService.findAllExceptions();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<ExeceptionEntityDto> findByUuid(@PathVariable UUID uuid) {
        ExeceptionEntityDto execeptionEntityDto= exceptionEntityService.findByUuid(uuid);
        return new ResponseEntity<>(execeptionEntityDto,HttpStatus.OK);
    }
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteException(@PathVariable UUID uuid){
            exceptionEntityService.deleteException(uuid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllExceptions() {
        exceptionEntityService.deleteAllExceptions();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
