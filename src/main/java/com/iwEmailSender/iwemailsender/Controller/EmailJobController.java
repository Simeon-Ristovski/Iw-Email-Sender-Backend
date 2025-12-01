package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobTimeToSendNextInsertDto;
import com.iwEmailSender.iwemailsender.Dto.Input.IsActiveInsert;
import com.iwEmailSender.iwemailsender.Dto.Input.MaxNumOfTriesInsert;
import com.iwEmailSender.iwemailsender.Dto.Output.EmailJobDto;
import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobDtoInsert;
import com.iwEmailSender.iwemailsender.Service.EmailJobService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/emailjobs")
public class EmailJobController {
    private final EmailJobService emailJobService;
    public EmailJobController(EmailJobService emailJobService) {
        this.emailJobService = emailJobService;
    }
    @GetMapping("")
    public ResponseEntity<List<EmailJobDto>> findAll() {
        List<EmailJobDto> list= emailJobService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmailJobDto> findById(@PathVariable Long id) {
        EmailJobDto emailJobDto= emailJobService.findById(id);
        return new ResponseEntity<>(emailJobDto,HttpStatus.OK);
    }
    @GetMapping("/acc/{uuid}")
    public  ResponseEntity<List<EmailJobDto>>  getByAccount(@PathVariable UUID uuid) {
        List<EmailJobDto> list= emailJobService.findAllForAcc(uuid);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @DeleteMapping("/acc/{uuid}")
    public  ResponseEntity<?>deleteAllForAcc(@PathVariable UUID uuid) {
        emailJobService.deleteAllForAcc(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/account_id/{uuid}")
    public ResponseEntity<String> addEmailJob(@PathVariable UUID uuid, @RequestBody EmailJobDtoInsert emailJobDtoInsert) throws BadRequestException {
        emailJobService.addEmailJob(uuid, emailJobDtoInsert);
        emailJobService.enableOrDisabled();
        return new ResponseEntity<>("Successfully added EmailJob",HttpStatus.CREATED);
    }
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteEmailJob(@PathVariable UUID uuid) {
        emailJobService.deleteEmailJob(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllEmailJobs(){
        emailJobService.deleteAllEmailJobs();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/acc/{uuid_acc}/emailid/{uuid_emailJob}")
    public ResponseEntity<String> editEmailJob(@PathVariable UUID uuid_acc, @PathVariable UUID uuid_emailJob, @RequestBody EmailJobDtoInsert emailJobDtoInsert) throws BadRequestException {
        emailJobService.editEmailJob(uuid_acc, uuid_emailJob, emailJobDtoInsert);
        emailJobService.enableOrDisabled();
        return new ResponseEntity<>("Successfully edited EmailJob",HttpStatus.OK);
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<String> setJobActiveOrDeactive(@PathVariable UUID uuid,@RequestBody IsActiveInsert isActiveInsert){
        emailJobService.setJobActiveOrDeactive(uuid,isActiveInsert);
        return new ResponseEntity<>("Successfully edited EmailJob",HttpStatus.OK);
    }
    @PutMapping("/number-of-failed-trys/{uuid}")
    private ResponseEntity<String> editMaxNumOdFailedTrys(@PathVariable UUID uuid, @RequestBody MaxNumOfTriesInsert num) throws BadRequestException {
        emailJobService.editMaxNumOfTrys(uuid,num);
        return new ResponseEntity<>("Successfully edited EmailJob",HttpStatus.OK);
    }
    @PostMapping("/{uuid_acc}/repeat/{uuid}")
    public ResponseEntity<String> repeatEmailJob(@PathVariable UUID uuid_acc, @PathVariable UUID uuid, @RequestBody EmailJobTimeToSendNextInsertDto emailJobTimeToSendNextInsertDto) throws BadRequestException {
        emailJobService.repeatTheSameEmailJob(uuid_acc, uuid,emailJobTimeToSendNextInsertDto);
        emailJobService.enableOrDisabled();
        return new ResponseEntity<>("Email send again",HttpStatus.OK);
    }
    @GetMapping("/start")
    public ResponseEntity<String> startScheduler() {
        emailJobService.enableScheduler();
        return new ResponseEntity<>("Scheduler started.",HttpStatus.OK);
    }
    @GetMapping("/stop")
    public ResponseEntity<String> stopScheduler() {
        emailJobService.disableScheduler();
        return new ResponseEntity<>("Scheduler stopped.", HttpStatus.OK);
    }
}
