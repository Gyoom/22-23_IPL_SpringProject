package be.vinci.ipl.catflix.gateway.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
    }
}
