package com.ahirajustice.retail.controllers;

import java.util.List;

import com.ahirajustice.retail.dtos.role.RoleCreateDto;
import com.ahirajustice.retail.dtos.role.RoleUpdateDto;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.exceptions.ValidationException;
import com.ahirajustice.retail.services.role.RoleService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.role.RoleCreateDtoValidator;
import com.ahirajustice.retail.validators.role.RoleUpdateDtoValidator;
import com.ahirajustice.retail.viewmodels.error.ErrorResponse;
import com.ahirajustice.retail.viewmodels.error.ValidationErrorResponse;
import com.ahirajustice.retail.viewmodels.role.RoleViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Roles")
@RestController
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Operation(summary = "Get Roles", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleViewModel.class))) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RoleViewModel> getRoles() throws ForbiddenException{
        List<RoleViewModel> roles = roleService.getRoles();
        return roles;
    }

    @Operation(summary = "Get Role", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoleViewModel.class)) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RoleViewModel getRole(@PathVariable long id) throws NotFoundException, ForbiddenException {
        RoleViewModel role = roleService.getRole(id);
        return role;
    }

    @Operation(summary = "Create Role", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoleViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) }) })
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RoleViewModel createRole(@RequestBody RoleCreateDto roleDto)
            throws BadRequestException, ForbiddenException, ValidationException {
        ValidatorUtils<RoleCreateDto> validator = new ValidatorUtils<RoleCreateDto>();
        validator.validate(new RoleCreateDtoValidator(), roleDto);

        RoleViewModel createdRole = roleService.createRole(roleDto);
        return createdRole;
    }

    @Operation(summary = "Update Role", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoleViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) }) })
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public RoleViewModel updateRole(@PathVariable long id, @RequestBody RoleUpdateDto roleDto)
            throws BadRequestException, ForbiddenException, NotFoundException, ValidationException {
        ValidatorUtils<RoleUpdateDto> validator = new ValidatorUtils<RoleUpdateDto>();
        validator.validate(new RoleUpdateDtoValidator(), roleDto);

        if (id != roleDto.getId()) {
            throw new BadRequestException("identifier mismatch");
        }

        RoleViewModel updatedRole = roleService.updateRole(roleDto);
        return updatedRole;
    }

}
