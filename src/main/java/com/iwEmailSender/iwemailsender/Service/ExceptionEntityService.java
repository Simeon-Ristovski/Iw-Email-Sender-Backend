package com.iwEmailSender.iwemailsender.Service;

import com.iwEmailSender.iwemailsender.Dto.Output.ExeceptionEntityDto;
import com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions.ResourceNotFoundException;
import com.iwEmailSender.iwemailsender.Mappers.ExceptionEntityMapper;
import com.iwEmailSender.iwemailsender.Model.ExceptionEntity;
import com.iwEmailSender.iwemailsender.Repository.ExceptionEntityRepository;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExceptionEntityService {

    private final ExceptionEntityRepository exceptionEntityRepository;


    public ExceptionEntityService(ExceptionEntityRepository exceptionEntityRepository) {
        this.exceptionEntityRepository = exceptionEntityRepository;
    }

    public ExeceptionEntityDto findById(Long id) {
        ExceptionEntity exceptionEntity = exceptionEntityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The exception with that id doesn't exist in Database!"));
        return new ExeceptionEntityDto(exceptionEntity.getUuid(), exceptionEntity.getId_job(), exceptionEntity.getMessage(), exceptionEntity.getDateOfException());
    }

    public List<ExeceptionEntityDto> findAllExcepitons() {
        List<ExeceptionEntityDto> list = new ArrayList<>();
        for (ExceptionEntity exception : exceptionEntityRepository.findAll()) {
            list.add(ExceptionEntityMapper.INSTANCE.mapExceptionEntityToDto(exception));
        }
        return list;
    }

    public void deletException(Long id) throws Exception {
        if (exceptionEntityRepository.existsById(id)) {
            exceptionEntityRepository.delete(exceptionEntityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The exception with that id doesn't exist in Database!")));
        } else {
            throw new ResourceNotFoundException("Exception doesn't exist in Database!");
        }
    }

    public void deleteAllExceptions() throws Exception {
        if (!exceptionEntityRepository.findAll().isEmpty()) {
            for (ExceptionEntity exception : exceptionEntityRepository.findAll()) {
                deletException(exception.getId());
            }
        }else {
            throw new ResourceNotFoundException("No exceptions in Database!");
        }
    }
}
