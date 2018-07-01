package pt.isel.ps.gis.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.InvitationService;
import pt.isel.ps.gis.exceptions.*;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.model.inputModel.InvitationInputModel;
import pt.isel.ps.gis.model.outputModel.InvitationsOutputModel;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/invitations")
public class InvitationController {

    private static final String ACCEPT_ERROR_MSG = "Accept must be non-null";

    private final InvitationService invitationService;
    private final MessageSource messageSource;

    public InvitationController(InvitationService invitationService, MessageSource messageSource) {
        this.invitationService = invitationService;
        this.messageSource = messageSource;
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<InvitationsOutputModel> getInvitations(
            @PathVariable("username") String username
    ) throws BadRequestException, NotFoundException {
        List<Invitation> invitations;
        try {
            invitations = invitationService.getReceivedInvitationsByUserUsername(username);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new InvitationsOutputModel(invitations), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @PostMapping("/houses/{house-id}")
    public ResponseEntity postInvitation(
            @PathVariable("house-id") long houseId,
            @RequestBody InvitationInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ConflictException, ForbiddenException {
        try {
            invitationService.sentInvitation(body.getUsername(), houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getMessage());
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/houses/{house-id}/users/{username}")
    public ResponseEntity putInvitation(
            @PathVariable("house-id") Long houseId,
            @PathVariable("username") String username,
            @RequestBody Boolean accept, // TODO criar um input model para receber o accept
            Locale locale
    ) throws BadRequestException, NotFoundException {
        //TODO: por isto num método no serviço
        if (accept == null)
            throw new BadRequestException(ACCEPT_ERROR_MSG, messageSource.getMessage("body_Error_Msg", null, locale));
        try {
            if (accept)
                invitationService.acceptInvitation(username, houseId, locale);
            else
                invitationService.declineInvitation(username, houseId, locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getMessage());
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
