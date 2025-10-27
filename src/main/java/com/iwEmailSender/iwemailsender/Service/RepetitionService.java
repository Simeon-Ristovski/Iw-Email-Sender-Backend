package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetitionDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RepetitionDtoInsert;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.AlreadyExistsException;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.RepetitionMapper;
import com.iwEmailSender.iwemailsender.Model.Repetition;
import com.iwEmailSender.iwemailsender.Repository.RepetitionRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class RepetitionService {
    private  final RepetitionRepository repetitionRepository;

    public RepetitionService(RepetitionRepository repetitionRepository) {
        this.repetitionRepository = repetitionRepository;

    }

    public List<RepetitionDto> findAll(){
        List<RepetitionDto> list=new ArrayList<>();
        for (Repetition repetition : repetitionRepository.findAll()) {
            RepetitionDto repetitionDto= RepetitionMapper.INSTANCE.mapRepetitionToDto(repetition);
            list.add(repetitionDto);
        }
        return list;
    }
    public RepetitionDto findById(Long id){
        return RepetitionMapper.INSTANCE.mapRepetitionToDto(repetitionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The repetition with that id doesn't exist in Database!")));
    }

    public void addRepetition(RepetitionDtoInsert repetitionDtoInsert){
        if(!repetitionRepository.existsByRepetitionName(repetitionDtoInsert.getRepetitionName())){
            Repetition repetition= RepetitionMapper.INSTANCE.mapDtoToRepetition(repetitionDtoInsert);
            repetition.setUuid(UUID.randomUUID());
            repetitionRepository.save(repetition);
        }else {
            throw new AlreadyExistsException("The repetition already exists in Database!");
        }
    }
    public void deleteRepetition(Long id) throws BadRequestException {
        if(repetitionRepository.existsById(id)){
            Repetition repetition= repetitionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The repetition with that id doesn't exist in Database!"));
           if(repetition.getListOfEmailJobs().isEmpty()){
               repetition.getListOfEmailJobs().clear();
               repetitionRepository.delete(repetition);
           }else {
               throw new BadRequestException("This repetition is used in email jobs and cannot be deleted!");
           }
        }else {
            throw new ResourceNotFoundException("The repetition with that id doesn't exist in Database!");
        }

    }




}
