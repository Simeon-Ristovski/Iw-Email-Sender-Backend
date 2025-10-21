package com.iwEmailSender.iwemailsender.Controller;

import com.iwEmailSender.iwemailsender.Dto.Output.EmailJobDto;
import com.iwEmailSender.iwemailsender.Dto.Input.EmailJobDtoInsert;
import com.iwEmailSender.iwemailsender.Service.EmailJobService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emailjobs")
public class EmailJobController {
    private final EmailJobService emailJobService;


    public EmailJobController(EmailJobService emailJobService) {
        this.emailJobService = emailJobService;
    }

    @GetMapping("")
    public List<EmailJobDto> findAll() {
        return emailJobService.findAll();
    }

    @GetMapping("/{id}")
    public EmailJobDto fingById(@PathVariable Long id) {
        return emailJobService.findById(id);
    }

    @PostMapping("/account_id/{id}")
    public ResponseEntity<String> addEmailJob(@PathVariable Long id, @RequestBody EmailJobDtoInsert emailJobDtoInsert){
        emailJobService.addEmailJob(id, emailJobDtoInsert);
        emailJobService.enableOrDisabled();
        return new ResponseEntity<>("Successfully added EmailJob",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmailJob(@PathVariable Long id) {
        emailJobService.deleteEmailJob(id);
        return new ResponseEntity<>("Successfully deleted EmailJob",HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllEmailJobs(){
        emailJobService.deleteAllEmailJobs();
        return new ResponseEntity<>("Deleted all Email jobs!",HttpStatus.OK);
    }

    @PutMapping("/acc/{id_acc}/emailid/{id_emailJob}")
    public ResponseEntity<String> editEmailJob(@PathVariable Long id_acc, @PathVariable Long id_emailJob, @RequestBody EmailJobDtoInsert emailJobDtoInsert)  {
        emailJobService.editEmailJob(id_acc, id_emailJob, emailJobDtoInsert);
        emailJobService.enableOrDisabled();
        return new ResponseEntity<>("Successfully edited EmailJob",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> setJobActiveOrDeactive(@PathVariable Long id,@RequestBody Boolean status){
        emailJobService.setJobActiveOrDeactive(id,status);
        return new ResponseEntity<>("Successfully edited EmailJob",HttpStatus.OK);
    }
    @PutMapping("/number-of-failed-trys/{id}")
    private ResponseEntity<String> editMaxNumOdFailedTrys(@PathVariable Long id, @RequestBody Integer num){
        emailJobService.editMaxNumOfTrys(id,num);
        return new ResponseEntity<>("Successfully edited EmailJob",HttpStatus.OK);
    }
    @PostMapping("/{id_acc}/repeat/{id}")
    public ResponseEntity<String> repeatEmailJob(@PathVariable Long id_acc, @PathVariable Long id) {
        emailJobService.repeatTheSameEmailJob(id_acc, id);
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
