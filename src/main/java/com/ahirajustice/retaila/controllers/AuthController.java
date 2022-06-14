package com.ahirajustice.retaila.controllers;

import com.ahirajustice.retaila.requests.auth.ExternalLoginRequest;
import com.ahirajustice.retaila.requests.auth.ForgotPasswordRequest;
import com.ahirajustice.retaila.requests.auth.LoginRequest;
import com.ahirajustice.retaila.requests.auth.ResetPasswordRequest;
import com.ahirajustice.retaila.services.auth.AuthService;
import com.ahirajustice.retaila.viewmodels.auth.LoginResponse;
import com.ahirajustice.retaila.viewmodels.error.ErrorResponse;
import com.ahirajustice.retaila.viewmodels.error.ValidationErrorResponse;
import com.ahirajustice.retaila.viewmodels.user.UserViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse externalLogin(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Login")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)) }),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
            }
    )
    @RequestMapping(path = "/external-login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody ExternalLoginRequest request) {
        return authService.externalLogin(request);
    }

    @Operation(summary = "Forgot Password")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
            }
    )
    @RequestMapping(path = "/forgot-password", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
    }

    @Operation(summary = "Reset Password")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewModel.class)) }),
                    @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
            }
    )
    @RequestMapping(path = "/reset-password", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel resetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }

}
