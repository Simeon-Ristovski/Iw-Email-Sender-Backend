package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.StatusDto;
import com.iwEmailSender.iwemailsender.Dto.Input.StatusDtoInsert;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.AlreadyExistsException;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.StatusMapper;
import com.iwEmailSender.iwemailsender.Model.Status;
import com.iwEmailSender.iwemailsender.Repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class StatusService {

    private final StatusRepository statusRepository;


    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

//
    public List<StatusDto> findAll(){
        List<StatusDto> list=new ArrayList<>();
        for (Status status : statusRepository.findAll()) {
            StatusDto statusDto = StatusMapper.INSTANCE.mapStatusToDto(status);
            list.add(statusDto);
        }
        return list;
    }
    public StatusDto findById(Long id){
        return StatusMapper.INSTANCE.mapStatusToDto(statusRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The status with that id doesn't exist in Database!")));
    }

    public void addStatus(StatusDtoInsert statusDtoInsert){
        if(!statusRepository.existsByStatusName(statusDtoInsert.getStatusName())){
            Status status = StatusMapper.INSTANCE.mapDtoToStatus(statusDtoInsert);
            status.setUuid(UUID.randomUUID());
            statusRepository.save(status);
        }else {
            throw new AlreadyExistsException("The status already exists in Database!");
        }

    }

    public void deleteStatus(Long id){
        if(statusRepository.existsById(id)){
            statusRepository.delete(statusRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The status with that id doesn't exist in Database!")));
        }else {
            throw new ResourceNotFoundException("The status doesn't exists in Database");
        }
    }
}
