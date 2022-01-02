package com.ahirajustice.retail.controllers;

import java.util.List;

import com.ahirajustice.retail.dtos.user.UserCreateDto;
import com.ahirajustice.retail.dtos.user.UserUpdateDto;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.exceptions.ValidationException;
import com.ahirajustice.retail.services.user.UserService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.user.UserCreateDtoValidator;
import com.ahirajustice.retail.validators.user.UserUpdateDtoValidator;
import com.ahirajustice.retail.viewmodels.error.ErrorResponse;
import com.ahirajustice.retail.viewmodels.error.ValidationErrorResponse;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;

import lombok.RequiredArgsConstructor;
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

@Tag(name = "Users")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get Users", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserViewModel.class))) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserViewModel> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Get User", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewModel.class)) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @Operation(summary = "Create User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) }) })
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewModel createUser(@RequestBody UserCreateDto userDto) {
        ValidatorUtils<UserCreateDto> validator = new ValidatorUtils<>();
        validator.validate(new UserCreateDtoValidator(), userDto);

        return userService.createUser(userDto);
    }

    @Operation(summary = "Update User", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewModel.class)) }),
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
    public UserViewModel updateUser(@PathVariable long id, @RequestBody UserUpdateDto userDto) {
        ValidatorUtils<UserUpdateDto> validator = new ValidatorUtils<>();
        validator.validate(new UserUpdateDtoValidator(), userDto);

        if (id != userDto.getId()) {
            throw new BadRequestException("identifier mismatch");
        }

        return userService.updateUser(userDto);
    }

}
