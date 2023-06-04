package ru.ilmira.myProject.service.dto;

import ru.ilmira.myProject.persist.model.Post;

/**
 * Mapper interface for Post and PostDto.
 *
 */
public interface PostMapper {

    /**
     * Transform Post object to PostDto.
     *
     * @param post - database entity.
     * @return PostDto - representation of the Post object in the service layer.
     */
    PostDto fromPost(Post post);
}
