package pt.isel.ps.gis.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ps.gis.bll.InvitationService;
import pt.isel.ps.gis.components.AuthenticationFacade;
import pt.isel.ps.gis.exceptions.*;
import pt.isel.ps.gis.model.Invitation;
import pt.isel.ps.gis.model.inputModel.InvitationInputModel;
import pt.isel.ps.gis.model.inputModel.InvitationInputModelUpdate;
import pt.isel.ps.gis.model.outputModel.InvitationsOutputModel;

import java.util.List;
import java.util.Locale;

import static pt.isel.ps.gis.utils.HeadersUtils.setSirenContentType;

@RestController
@RequestMapping("/v1/invitations")
public class InvitationController {

    private final InvitationService invitationService;
    private final AuthenticationFacade authenticationFacade;

    public InvitationController(InvitationService invitationService, AuthenticationFacade authenticationFacade) {
        this.invitationService = invitationService;
        this.authenticationFacade = authenticationFacade;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/users/{username}")
    public ResponseEntity<InvitationsOutputModel> getInvitations(
            @PathVariable("username") String username
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        List<Invitation> invitations;
        String authenticatedUsername = authenticationFacade.getAuthentication().getName();
        try {
            invitations = invitationService.getReceivedInvitationsByUserUsername(authenticatedUsername, username);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new InvitationsOutputModel(username, invitations), setSirenContentType(headers),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/houses/{house-id}")
    public ResponseEntity postInvitation(
            @PathVariable("house-id") long houseId,
            @RequestBody InvitationInputModel body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ConflictException, ForbiddenException {
        String authenticatedUsername = authenticationFacade.getAuthentication().getName();
        try {
            invitationService.sendInvitation(houseId, authenticatedUsername, body.getUsername(), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityAlreadyExistsException e) {
            throw new ConflictException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/houses/{house-id}/users/{username}")
    public ResponseEntity putInvitation(
            @PathVariable("house-id") Long houseId,
            @PathVariable("username") String username,
            @RequestBody InvitationInputModelUpdate body,
            Locale locale
    ) throws BadRequestException, NotFoundException, ForbiddenException {
        String authenticatedUsername = authenticationFacade.getAuthentication().getName();
        try {
            invitationService.updateInvitation(authenticatedUsername, houseId, username, body.getAccept(), locale);
        } catch (EntityException e) {
            throw new BadRequestException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e.getUserFriendlyMessage());
        } catch (InsufficientPrivilegesException e) {
            throw new ForbiddenException(e.getMessage(), e.getUserFriendlyMessage());
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
