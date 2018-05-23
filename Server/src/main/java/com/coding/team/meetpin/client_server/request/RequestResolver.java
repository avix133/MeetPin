package com.coding.team.meetpin.client_server.request;

import com.coding.team.meetpin.client_server.response.Response;

/**
 * Description of class:
 * <p>
 * Created on 23 May 2018 (21:12)
 *
 * @author dawid
 */
public interface RequestResolver {
    Response resolve(Request request);
}
