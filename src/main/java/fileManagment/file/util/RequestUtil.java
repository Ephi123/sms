package fileManagment.file.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.BiConsumer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import javax.security.auth.login.CredentialExpiredException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiFunction;

import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class RequestUtil {
    public static final BiFunction< Exception,HttpStatus, String> errorReason = (e, status) -> {
        if(status.isSameCodeAs(FORBIDDEN)) return "You do not have enough permission";
        if(status.isSameCodeAs(UNAUTHORIZED)) return "you are not logged in";
        if(e instanceof DisabledException || e instanceof LockedException || e instanceof BadCredentialsException || e instanceof CredentialExpiredException || e instanceof ApiException){
            return e.getMessage();
        }
        if(status.is5xxServerError()){
            return "An internal server error occurred";
        }
        else
            return "An error occurred. please try again. ";
    };


    private static  final BiConsumer<HttpServletResponse, Response> writeResponse = (httpResponse, response) -> {
        try{
            var outStream = httpResponse.getOutputStream();
            new ObjectMapper().writeValue(outStream,response);
            outStream.flush();
            System.out.println("demo from error response" + response);
        }
        catch (Exception e){
            throw new ApiException(e.getMessage());
        }
    };


    public static Response getResponse(HttpServletRequest request, Map<?, ?> data, String message, HttpStatus status){
        return new Response(LocalDateTime.now().toString(),status.value(), request.getServletPath(), HttpStatus.valueOf(status.value()),  message, EMPTY, data);
    }

   public static void  handleErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception exception,HttpStatus status){
        if(exception instanceof AccessDeniedException){
            var apiResponse = getErrorResponse(request,response,exception,FORBIDDEN);
            writeResponse.accept(response,apiResponse);
        }
        else {
            var apiResponse = getErrorResponse(request,response,exception,status);
            writeResponse.accept(response,apiResponse);
        }
    }

    private static Response getErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception ex ,HttpStatus httpStatus) {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
     return   new Response(LocalDateTime.now().toString(),httpStatus.value(),request.getServletPath(),HttpStatus.valueOf(httpStatus.value()),errorReason.apply(ex,httpStatus),getRootCauseMessage(ex),emptyMap());

    }

    public static URI getUrRI(String path) {
        return URI.create(path);
    }

}
