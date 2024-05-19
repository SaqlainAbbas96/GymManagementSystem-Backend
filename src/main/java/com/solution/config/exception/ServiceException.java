package com.solution.config.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
public class ServiceException extends BaseRuntimeException {

    public ServiceException(String errorMessage, Throwable e) {
        super(errorMessage);
        if(e instanceof RecordNotFoundException) {
            throw new RecordNotFoundException(errorMessage, ((RecordNotFoundException) e).httpStatus);
        } else if(e instanceof BadRequestException) {
            throw new BadRequestException(errorMessage);
        } else if(e instanceof DuplicateRecordException) {
            throw new DuplicateRecordException(errorMessage);
        } else if(e instanceof InvalidDataAccessResourceUsageException) {
            throw new DbConnectionException("Database connection failed, try again later");
        } else if(e instanceof ConstraintViolationException) {
            throw new ConstraintViolationException(new HashSet<>(((ConstraintViolationException) e).getConstraintViolations()));
        } else if(e instanceof InternalServiceException) {
            throw new InternalServiceException(errorMessage);
        } else {
            throw new BaseException("Some error occurred, try again");
        }
    }
}
