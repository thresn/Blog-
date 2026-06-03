package com.BlogProject.Blog.services.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BlogProject.Blog.domain.Tag;
import com.BlogProject.Blog.repositories.TagRepository;
import com.BlogProject.Blog.services.TagService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();

    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
        Set<String> existingTagsSet = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());

        List<Tag> newCreatedTagsDontExist = tagNames.stream().filter(name -> !existingTagsSet.contains(name))
                .map(name -> Tag.builder().name(name).posts(new HashSet<>()).build()).toList();

        List<Tag> savedTags = new ArrayList<>();

        if (!newCreatedTagsDontExist.isEmpty()) {
            savedTags = tagRepository.saveAll(newCreatedTagsDontExist);
        }

        savedTags.addAll(existingTags);

        return savedTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if (!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Bu Tag'e sahip post var silinemiyor.");
            }
            tagRepository.deleteById(id);

        });
    }


    @Override
    public List<Tag> getTagByIds(Set<UUID> ids) {
        List<Tag> foundedTags=tagRepository.findAllById(ids);

        if(foundedTags.size() != ids.size()){
            throw new EntityNotFoundException("taglar exist değil");
        }

        return foundedTags;
    }

    @Override
    public Tag getTagById(UUID id) {//id si x olan tagı getir
      return tagRepository.findById(id).orElseThrow(()->new EntityNotFoundException("tag bulunamadı"));
    }

}
