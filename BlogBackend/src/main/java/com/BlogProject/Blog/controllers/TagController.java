package com.BlogProject.Blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BlogProject.Blog.domain.Tag;
import com.BlogProject.Blog.domain.dtos.CreateTagRequest;
import com.BlogProject.Blog.domain.dtos.TagDto;
import com.BlogProject.Blog.mappers.TagMapper;
import com.BlogProject.Blog.services.TagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "api/v1/tags")
@RequiredArgsConstructor

public class TagController {

    private final TagMapper tagMapper;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        List<TagDto> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createtags(@RequestBody @Valid CreateTagRequest createTagRequest) {
        List<Tag> savedTagsList = tagService.createTags(createTagRequest.getNames());
        List<TagDto> createdTagResponses = savedTagsList.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(
                createdTagResponses,
                HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();

    }

}
