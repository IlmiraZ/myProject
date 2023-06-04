package ru.ilmira.myProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ilmira.myProject.exception.ForbiddenException;
import ru.ilmira.myProject.exception.UserNotFoundException;
import ru.ilmira.myProject.persist.model.EUserCondition;
import ru.ilmira.myProject.service.PictureService;
import ru.ilmira.myProject.service.UserService;
import ru.ilmira.myProject.service.dto.PictureData;
import ru.ilmira.myProject.service.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/upload-pictures")
@RequiredArgsConstructor
public class PictureController {

    public static final String USER_NOT_FOUND_TEMPLATE = "User [%s] not found.";
    public static final String USER_NOT_ACTIVE_TEMPLATE = "User [%s] is not active.";

    private final PictureService pictureService;
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name="bearerAuth")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_MODERATOR', 'SCOPE_ROLE_ADMIN')")
    @Operation(summary = "Upload pictures to the storage, returns images ids", tags = "Pictures")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "When request body invalid"),
            @ApiResponse(responseCode = "401", description = "When not authorized"),
            @ApiResponse(responseCode = "403", description = "When user not ACTIVE"),
            @ApiResponse(responseCode = "404", description = "When user not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<List<PictureData>> uploadPictures(
            @Parameter(
                    description = "Files to be uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
            )
            @RequestParam("pictures") List<MultipartFile> pictures) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserDto user = userService.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND_TEMPLATE, username));
        });

        if (!user.getCondition().equals(EUserCondition.ACTIVE.name())) {
            throw new ForbiddenException(String.format(USER_NOT_ACTIVE_TEMPLATE, username));
        }

        return ResponseEntity.ok(pictureService.savePictures(pictures));
    }
}
