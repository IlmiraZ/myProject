package ru.ilmira.myProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.ilmira.myProject.exception.PostNotFoundException;
import ru.ilmira.myProject.payload.request.PostDataRequest;
import ru.ilmira.myProject.payload.response.MessageResponse;
import ru.ilmira.myProject.persist.model.EPostCondition;
import ru.ilmira.myProject.service.PostService;
import ru.ilmira.myProject.service.dto.PostDto;

import java.util.Optional;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @Operation(summary = "Returns all published posts with pagination", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<Page<PostDto>> listAll(@RequestParam("username") Optional<String> username,
                                                 @RequestParam("topic") Optional<String> topic,
                                                 @RequestParam("tag") Optional<String> tag,
                                                 @RequestParam("page") Optional<Integer> page,
                                                 @RequestParam("size") Optional<Integer> size,
                                                 @RequestParam("sortField") Optional<String> sortField,
                                                 @RequestParam("sortDir") Optional<Direction> sortDir) {

        return ResponseEntity.ok(
                postService.findAll(
                        username,
                        topic,
                        tag,
                        Optional.of(EPostCondition.PUBLISHED.name()),
                        Optional.empty(),
                        page,
                        size,
                        sortField,
                        sortDir)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns published post by id", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "When post not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<PostDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id = " + id + " not found.")));
    }

    @GetMapping("/random")
    @Operation(summary = "Returns random published post", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "When post not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<PostDto> random() {
        return ResponseEntity.ok(postService.getRandomPost(EPostCondition.PUBLISHED)
                .orElseThrow(() -> new PostNotFoundException("Post not found.")));
    }

    @PostMapping("/save")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_MODERATOR', 'SCOPE_ROLE_ADMIN')")
    @Operation(summary = "Saves a new or updates an existing post", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful saved/updated"),
            @ApiResponse(responseCode = "400", description = "When request body invalid"),
            @ApiResponse(responseCode = "401", description = "When not authorized"),
            @ApiResponse(responseCode = "404", description = "When post or topic not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<PostDto> save(@Valid @RequestBody PostDataRequest postDataRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(postService.save(username, postDataRequest));
    }

    @GetMapping("/own")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_MODERATOR', 'SCOPE_ROLE_ADMIN')")
    @Operation(summary = "Returns own posts with pagination, except for DELETED", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "When bad request"),
            @ApiResponse(responseCode = "401", description = "When not authorized"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<Page<PostDto>> listOwnPosts(@RequestParam("topic") Optional<String> topic,
                                                      @RequestParam("tag") Optional<String> tag,
                                                      @RequestParam("condition") Optional<String> condition,
                                                      @RequestParam("page") Optional<Integer> page,
                                                      @RequestParam("size") Optional<Integer> size,
                                                      @RequestParam("sortField") Optional<String> sortField,
                                                      @RequestParam("sortDir") Optional<Direction> sortDir) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
                postService.findAll(
                        Optional.of(username),
                        topic,
                        tag,
                        condition,
                        Optional.of(EPostCondition.DELETED.name()),
                        page,
                        size,
                        sortField,
                        sortDir)
        );
    }

    @PatchMapping("/delete/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_MODERATOR', 'SCOPE_ROLE_ADMIN')")
    @Operation(summary = "Delete own post. Set post condition to DELETED", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful deleted"),
            @ApiResponse(responseCode = "401", description = "When not authorized"),
            @ApiResponse(responseCode = "412", description = "When preconditionFailed"),
            @ApiResponse(responseCode = "404", description = "When post not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(postService.delete(username, id));
    }

    @PatchMapping("/hide/{id}")
    @SecurityRequirement(name="bearerAuth")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_MODERATOR', 'SCOPE_ROLE_ADMIN')")
    @Operation(summary = "Hides own post. Set post condition to HIDDEN", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "When not authorized"),
            @ApiResponse(responseCode = "403", description = "When forbidden"),
            @ApiResponse(responseCode = "404", description = "When post not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<MessageResponse> hide(@PathVariable("id") Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(postService.hide(username, postId));
    }

    @PatchMapping("/publish/{id}")
    @SecurityRequirement(name="bearerAuth")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_MODERATOR', 'SCOPE_ROLE_ADMIN')")
    @Operation(summary = "Publishes own post. Set post condition to PUBLISHED", tags = "Posts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "When not authorized"),
            @ApiResponse(responseCode = "403", description = "When forbidden"),
            @ApiResponse(responseCode = "404", description = "When post not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<MessageResponse> publish(@PathVariable("id") Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(postService.publish(username, postId));
    }
}
