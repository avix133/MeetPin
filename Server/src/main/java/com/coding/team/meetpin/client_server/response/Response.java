package com.coding.team.meetpin.client_server.response;

import java.io.Serializable;

import com.coding.team.meetpin.client_server.request.RequestType;

/**
 * Description of class:
 * <p>
 * Created on 23 May 2018 (21:07)
 *
 * @author dawid
 */
public interface Response extends Serializable {
    RequestType getType();
    Object getPayload();
}
