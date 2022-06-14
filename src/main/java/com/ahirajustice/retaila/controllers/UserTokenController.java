package com.ahirajustice.retaila.controllers;

import com.ahirajustice.retaila.requests.usertoken.VerifyUserTokenRequest;
import com.ahirajustice.retaila.services.usertoken.UserTokenService;
import com.ahirajustice.retaila.viewmodels.error.ErrorResponse;
import com.ahirajustice.retaila.viewmodels.error.ValidationErrorResponse;
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

@Tag(name = "User Token")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user-tokens")
public class UserTokenController {

    private final UserTokenService userTokenService;

    @Operation(summary = "Verify User Token")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @RequestMapping(path = "/verify", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyOtp(@RequestBody VerifyUserTokenRequest request) {
        userTokenService.verifyToken(request);
    }
}
