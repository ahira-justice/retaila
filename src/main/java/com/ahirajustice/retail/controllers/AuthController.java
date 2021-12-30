package com.ahirajustice.retail.controllers;

import com.ahirajustice.retail.dtos.auth.LoginDto;
import com.ahirajustice.retail.exceptions.UnauthorizedException;
import com.ahirajustice.retail.exceptions.ValidationException;
import com.ahirajustice.retail.services.auth.IAuthService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.auth.LoginDtoValidator;
import com.ahirajustice.retail.viewmodels.auth.LoginResponse;
import com.ahirajustice.retail.viewmodels.error.ErrorResponse;
import com.ahirajustice.retail.viewmodels.error.ValidationErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth")
@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)) }),
            @ApiResponse(responseCode = "401", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) }) })
    @RequestMapping(path = "login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginDto loginDto) throws UnauthorizedException, ValidationException {
        ValidatorUtils<LoginDto> validator = new ValidatorUtils<LoginDto>();
        validator.validate(new LoginDtoValidator(), loginDto);

        LoginResponse token = new LoginResponse();

        if (!authService.authenticateUser(loginDto)) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        token = authService.createAccessToken(loginDto);

        return token;
    }

}
