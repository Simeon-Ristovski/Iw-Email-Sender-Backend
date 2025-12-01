package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.ExeceptionEntityDto;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.ExceptionEntityMapper;
import com.iwEmailSender.iwemailsender.Model.EmailJob;
import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import com.iwEmailSender.iwemailsender.Repository.EmailJobRepository;
import com.iwEmailSender.iwemailsender.Repository.ExceptionEntityRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExceptionEntityService {
    private final ExceptionEntityRepository exceptionEntityRepository;
    private final EmailJobRepository emailJobRepository;
    public ExceptionEntityService(ExceptionEntityRepository exceptionEntityRepository, EmailJobRepository emailJobRepository) {
        this.exceptionEntityRepository = exceptionEntityRepository;
        this.emailJobRepository = emailJobRepository;
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
            ExeceptionEntityDto entityDto=ExceptionEntityMapper.INSTANCE.mapExceptionEntityToDto(exception);
            EmailJob emailJob;
            if(emailJobRepository.existsById(exception.getIdJob())){
                emailJob=emailJobRepository.findById(exception.getIdJob())
                        .orElseThrow(()->new ResourceNotFoundException("Email job is not found!"));
                entityDto.setJobUUUID(emailJob.getUuid());
            }else {
                entityDto.setJobUUUID(null);
            }
            list.add(entityDto);
        }
        return list;
    }
    public void deleteException(UUID uuid){
        if (exceptionEntityRepository.existsByUuid(uuid)) {
            exceptionEntityRepository.delete(exceptionEntityRepository.findByUuid(uuid));
        } else {
            throw new ResourceNotFoundException("Exception doesn't exist in Database!");
        }
    }
    public void deleteAllExceptions(){
        if (!exceptionEntityRepository.findAll().isEmpty()) {
            for (ExceptionEntity exception : exceptionEntityRepository.findAll()) {
                deleteException(exception.getUuid());
            }
        }else {
            throw new ResourceNotFoundException("No exceptions in Database!");
        }
    }
}
