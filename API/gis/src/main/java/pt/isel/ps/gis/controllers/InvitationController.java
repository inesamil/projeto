package pt.isel.ps.gis.controllers;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.InvitationService;
import pt.isel.ps.gis.components.AuthenticationFacade;
import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.exceptions.EntityException;
import pt.isel.ps.gis.exceptions.EntityNotFoundException;
import pt.isel.ps.gis.exceptions.NotFoundException;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.model.inputModel.InvitationInputModel;
import pt.isel.ps.gis.model.outputModel.InvitationsOutputModel;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    private final AuthenticationFacade authenticationFacade;

    private final MessageSource messageSource;

    public InvitationController(InvitationService invitationService, AuthenticationFacade authenticationFacade, MessageSource messageSource) {
        this.invitationService = invitationService;
        this.authenticationFacade = authenticationFacade;
        this.messageSource = messageSource;
    }

    @PostMapping("")
    public void postInvitation(
            @RequestBody InvitationInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        try {
            invitationService.sentInvitation(body.getUsername(), body.getHouseId(), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
    }

    @PutMapping("/houses/{house-id}/users/{username}")
    public ResponseEntity<InvitationsOutputModel> putInvitation(
            @PathVariable("house-id") Long houseId,
            @PathVariable("username") String username,
            @RequestBody Boolean accept,
            Locale locale
    ) throws BadRequestException, NotFoundException {
        List<Invitation> invitations;
        try {
            if (accept) {
                invitationService.acceptInvitation(username, houseId, locale);
            } else {
                invitationService.declineInvitation(username, houseId, locale);
            }
            invitations = invitationService.getReceivedInvitationsByUserUsername(authenticationFacade.getAuthentication().getName(), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new InvitationsOutputModel(invitations), setSirenContentType(headers),
                HttpStatus.OK);
    }

}
