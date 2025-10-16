package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.RepetisionDto;
import com.iwEmailSender.iwemailsender.Dto.Input.RepetisionDtoInsert;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.AlreadyExistsException;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.RepetisionMapper;
import com.iwEmailSender.iwemailsender.Model.Repetision;
import com.iwEmailSender.iwemailsender.Repository.RepetisionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class RepetisionService {
    private  final RepetisionRepository repetisionRepository;

    public RepetisionService(RepetisionRepository repetisionRepository) {
        this.repetisionRepository = repetisionRepository;

    }

    public List<RepetisionDto> findAll(){
        List<RepetisionDto> list=new ArrayList<>();
        for (Repetision repetision : repetisionRepository.findAll()) {
            RepetisionDto repetisionDto= RepetisionMapper.INSTANCE.mapRepetisionToDto(repetision);
            list.add(repetisionDto);
        }
        return list;
    }
    public RepetisionDto findById(Long id){
        return RepetisionMapper.INSTANCE.mapRepetisionToDto(repetisionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The repetision with that id doesn't exist in Database!")));
    }

    public void addRepetision(RepetisionDtoInsert repetisionDtoInsert){
        if(!repetisionRepository.existsByRepetisionName(repetisionDtoInsert.getRepetisionName())){
            Repetision repetision= RepetisionMapper.INSTANCE.mapDtoToRepetision(repetisionDtoInsert);
            repetision.setUuid(UUID.randomUUID());
            repetisionRepository.save(repetision);
        }else {
            throw new AlreadyExistsException("The repetision already exists in Database!");
        }
    }
    public void deleteRepetision(Long id){
        if(repetisionRepository.existsById(id)){
            Repetision repetision= repetisionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The repetision with that id doesn't exist in Database!"));
            repetision.getListOfEmailJobs().clear();
            repetisionRepository.delete(repetision);
        }else {
            throw new ResourceNotFoundException("The repetision with that id doesn't exist in Database!");
        }

    }




}
