package ru.ilmira.myProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilmira.myProject.service.TopicService;
import ru.ilmira.myProject.service.dto.TopicDto;

import java.util.List;



@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping
    @Operation(summary = "Returns all topics", tags = "Topics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "When topic not found"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<List<TopicDto>> findAll() {
        return ResponseEntity.ok(topicService.findAll());
    }
}
