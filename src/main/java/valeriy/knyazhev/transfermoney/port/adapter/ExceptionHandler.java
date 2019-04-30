package valeriy.knyazhev.transfermoney.port.adapter;

import valeriy.knyazhev.transfermoney.application.AccountNotFoundException;
import valeriy.knyazhev.transfermoney.port.adapter.model.ResponseModel;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Valeriy Knyazhev
 */
public class ExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ResponseModel response = ResponseModel.error(ex.getMessage());
        Response.Status responseStatus = defineHttpStatus(ex);
        return Response.status(responseStatus)
            .entity(response)
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

    private Response.Status defineHttpStatus(Throwable ex) {
        if (ex instanceof AccountNotFoundException) {
            return Response.Status.NOT_FOUND;
        } else if (ex instanceof IllegalArgumentException || ex instanceof IllegalStateException) {
            return Response.Status.BAD_REQUEST;
        } else if (ex instanceof NotAllowedException) {
            return Response.Status.METHOD_NOT_ALLOWED;
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }
}