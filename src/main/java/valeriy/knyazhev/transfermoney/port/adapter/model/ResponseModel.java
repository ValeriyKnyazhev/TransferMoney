package valeriy.knyazhev.transfermoney.port.adapter.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Valeriy Knyazhev
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel {

    public final String info;

    public final String error;

    private ResponseModel(String info, String error) {
        this.info = info;
        this.error = error;
    }

    public static ResponseModel info(String info) {
        return new ResponseModel(info, null);
    }

    public static ResponseModel error(String error) {
        return new ResponseModel(null, error);
    }

}

