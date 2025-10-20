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

    @GetMapping("/{uuid}")
    public ExeceptionEntityDto findByUuid(@PathVariable UUID uuid){
        return exceptionEntityService.findByUuid(uuid);
    }

    @GetMapping()
    public List<ExeceptionEntityDto> findAll(){
        return exceptionEntityService.findAllExcepitons();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExceptio(@PathVariable Long id){
            exceptionEntityService.deletException(id);
        return new ResponseEntity<>("Exception deleted from Database", HttpStatus.CREATED);

    }
    @DeleteMapping()
    public ResponseEntity<String> deleteAllExceptions() {
        exceptionEntityService.deleteAllExceptions();
        return new ResponseEntity<>("Deleted all exceptions!",HttpStatus.OK);

    }
}
