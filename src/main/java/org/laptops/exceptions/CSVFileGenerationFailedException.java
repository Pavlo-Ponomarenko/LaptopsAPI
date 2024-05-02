package org.laptops.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class CSVFileGenerationFailedException extends RuntimeException {

    public CSVFileGenerationFailedException() {
        super("Report file generation for laptops failed");
    }
}
