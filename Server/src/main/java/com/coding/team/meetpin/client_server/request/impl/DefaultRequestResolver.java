package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestResolver;
import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.client_server.response.Response;
import com.coding.team.meetpin.client_server.response.impl.DefaultResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description of class:
 * <p>
 * Created on 23 May 2018 (20:08)
 *
 * @author dawid
 */
public class DefaultRequestResolver implements RequestResolver {
    private static final Logger logger = LogManager.getLogger();


    //needs to have DataService

    public DefaultRequestResolver( /*DataService dataService*/) {
    }

    @Override
    public Response resolve(final Request request) {
        Response response = null;
        switch (request.getType()) {
            case PIN_DATA: {
                response = getPinData((PinDataRequest) request);
                break;
            }
            case ADD_PIN: {
//                response = addPin((AddPinRequest)request)
                break;
            }
            case AUTHENTICATE: {
//                response = authenticate((AuthenticationRequest)request);
                break;
            }
        }

        return response;

    }

    private Response getPinData(PinDataRequest pinDataRequest) {
//        return new DefaultResponse(RequestType.PIN_DATA, dataService.fetchPinForId());
        return new DefaultResponse(RequestType.PIN_DATA, "Some pin payload " + pinDataRequest.getPinId());
    }
}
