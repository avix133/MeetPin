package com.coding.team.meetpin.client_server.request.impl;

import com.coding.team.meetpin.client_server.request.Request;
import com.coding.team.meetpin.client_server.request.RequestResolver;
import com.coding.team.meetpin.client_server.request.RequestType;
import com.coding.team.meetpin.client_server.response.Response;
import com.coding.team.meetpin.client_server.response.impl.DefaultResponse;
import com.coding.team.meetpin.dao.model.PinAnswer;
import com.coding.team.meetpin.dao.model.User;
import com.coding.team.meetpin.dao.repository.AnswerRepository;
import com.coding.team.meetpin.dao.repository.PinRepository;
import com.coding.team.meetpin.dao.repository.RelationshipRepository;
import com.coding.team.meetpin.dao.repository.UserRepository;
import com.coding.team.meetpin.util.DatabaseUtils;
import jdk.nashorn.internal.objects.AccessorPropertyDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description of class:
 * <p>
 * Created on 23 May 2018 (20:08)
 *
 * @author dawid
 */
@Component
public class DefaultRequestResolver implements RequestResolver {
    private static final Logger logger = LogManager.getLogger();

    //needs to have DataService
    @Autowired
    private PinRepository pinRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Response resolve(final Request request) {
        Response response = null;
        switch (request.getType()) {
            case PIN_DATA: {
                response = getPinData((PinDataRequest) request);
                break;
            }
            case ADD_PIN: {
                response = addPin((AddPinRequest) request);
                break;
            }
            case AUTHENTICATE: {
                response = authenticate((AuthenticationRequest) request);
                break;
            }
            case GLOBAL_PINS: {
                response = getGlobalPins();
                break;
            }
            case DISPLAY_PINS: {
                response = getDisplayPins((DisplayPinRequest) request);
                break;
            }
            case ADDRESSED_TO_ME_PINS: {
                response = getPinsAddressedToMe((AddressedToMePinRequest) request);
                break;
            }
            case FRIEND_LIST: {
                response = getFriendList((FriendListRequest) request);
                break;
            }
            case PENDING_INVITATIONS: {
                response = getPendingInvitations((PendingInvitationsRequest) request);
                break;
            }
            case INVITE_FRIEND: {
                response = inviteFriend((InviteFriendRequest) request);
                break;
            }

            case REMOVE_FRIEND: {
                response = removeFriend((RemoveFriendRequest) request);
                break;
            }

            case ACCEPT_EVENT: {
                response = acceptEvent((AcceptEventRequest) request);
                break;
            }
        }
        return response;
    }

    private Response getPinData(PinDataRequest pinDataRequest) {
        return new DefaultResponse(RequestType.PIN_DATA, pinRepository.findPinById(pinDataRequest.getPinId()));
    }

    private Response getGlobalPins() {
        return new DefaultResponse(RequestType.GLOBAL_PINS, pinRepository.fetchGlobalPins().toString());
    }

    private Response getDisplayPins(DisplayPinRequest displayPin) {
        return new DefaultResponse(RequestType.DISPLAY_PINS, pinRepository.fetchDisplayPins(displayPin.getClientId()));
    }

    private Response addPin(AddPinRequest addPinRequest) {
        return new DefaultResponse(RequestType.ADD_PIN, pinRepository.save(addPinRequest.getPin()));
    }

    private Response getPinsAddressedToMe(AddressedToMePinRequest addressed) {
        return new DefaultResponse(RequestType.ADDRESSED_TO_ME_PINS, pinRepository.fetchPinsAddressedToMe(addressed.getClientId()));
    }

    private Response getFriendList(FriendListRequest friendList) {
        return new DefaultResponse(RequestType.FRIEND_LIST, relationshipRepository.fetchFriendList(friendList.getClientId()).toString());
    }

    private Response getPendingInvitations(PendingInvitationsRequest pendingInvitations) {
        return new DefaultResponse(RequestType.PENDING_INVITATIONS, relationshipRepository.fetchPendingInvitations(pendingInvitations.getClientId()).toString());
    }

    private Response inviteFriend(InviteFriendRequest inviteFriend) {
        return new DefaultResponse(RequestType.INVITE_FRIEND, relationshipRepository.inviteFriend(inviteFriend.getClientId(), inviteFriend.getUserEmail()));
    }

    private Response removeFriend(RemoveFriendRequest removeFriend) {
        return new DefaultResponse(RequestType.REMOVE_FRIEND, relationshipRepository.removeFriend(removeFriend.getClientId(), removeFriend.getUserId()));
    }

    private Response acceptEvent(AcceptEventRequest acceptEventRequest) {
        answerRepository.save(new PinAnswer())
        return new DefaultResponse(RequestType.REMOVE_FRIEND, );
    }

    private Response authenticate(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            user = userRepository.save(new User(DatabaseUtils.getUsernameFromEmail(email), email));
        return new DefaultResponse(RequestType.AUTHENTICATE, user.getId());
    }

}
