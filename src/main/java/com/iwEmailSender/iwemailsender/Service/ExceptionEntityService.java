package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.ExeceptionEntityDto;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.ExceptionEntityMapper;
import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import com.iwEmailSender.iwemailsender.Repository.ExceptionEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExceptionEntityService {

    private final ExceptionEntityRepository exceptionEntityRepository;


    public ExceptionEntityService(ExceptionEntityRepository exceptionEntityRepository) {
        this.exceptionEntityRepository = exceptionEntityRepository;
    }
    public ExeceptionEntityDto findByUuid(UUID uuid){
        if(exceptionEntityRepository.existsByUuid(uuid)){
            ExceptionEntity exceptionEntity=exceptionEntityRepository.findByUuid(uuid);
            return ExceptionEntityMapper.INSTANCE.mapExceptionEntityToDto(exceptionEntity);
        }else {
         throw new ResourceNotFoundException("The exception with that id doesn't exist in Database!");
        }
    }

    public List<ExeceptionEntityDto> findAllExceptions() {
        List<ExeceptionEntityDto> list = new ArrayList<>();
        for (ExceptionEntity exception : exceptionEntityRepository.findAll()) {
            list.add(ExceptionEntityMapper.INSTANCE.mapExceptionEntityToDto(exception));
        }
        return list;
    }

    public void deleteException(Long id){
        if (exceptionEntityRepository.existsById(id)) {
            exceptionEntityRepository.delete(exceptionEntityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The exception with that id doesn't exist in Database!")));
        } else {
            throw new ResourceNotFoundException("Exception doesn't exist in Database!");
        }
    }

    public void deleteAllExceptions(){
        if (!exceptionEntityRepository.findAll().isEmpty()) {
            for (ExceptionEntity exception : exceptionEntityRepository.findAll()) {
                deleteException(exception.getId());
            }
        }else {
            throw new ResourceNotFoundException("No exceptions in Database!");
        }
    }
}
