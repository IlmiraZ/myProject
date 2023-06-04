package ru.ilmira.myProject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ilmira.myProject.payload.request.PostDataRequest;
import ru.ilmira.myProject.payload.response.MessageResponse;
import ru.ilmira.myProject.persist.model.EPostCondition;
import ru.ilmira.myProject.service.dto.PostDto;

import java.util.Optional;

/**
 * Interface describing the contract of interaction with the PostDto entity.
 * @see PostDto
 *
 */
@Service
public interface PostService {

    Page<PostDto> findAll(Optional<String> username,
                          Optional<String> topic,
                          Optional<String> tag,
                          Optional<String> condition,
                          Optional<String> excludeCondition,
                          Optional<Integer> page,
                          Optional<Integer> size,
                          Optional<String> sortField,
                          Optional<Sort.Direction> direction);

    Optional<PostDto> findById(long postId);

    PostDto save(String username, PostDataRequest postDataRequest);

    Optional<PostDto> getRandomPost(EPostCondition postCondition);

    MessageResponse deleteById(Long postId);
    MessageResponse delete(String username, long postId);
    MessageResponse deleteAny(String username, long postId);

    MessageResponse hideById(Long postId);
    MessageResponse hide(String username, long postId);

    MessageResponse publish(String username, long postId);

    MessageResponse ban(String username, long postId);
}
