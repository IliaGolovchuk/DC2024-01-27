package by.bsuir.restapi.controller;

import by.bsuir.restapi.model.dto.request.PostRequestDto;
import by.bsuir.restapi.model.dto.response.PostResponseDto;
import by.bsuir.restapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> get(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(postService.get(id));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(dto));
    }

    @PutMapping
    public ResponseEntity<PostResponseDto> update(@Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable Long id) {
        postService.delete(id);
    }
}
