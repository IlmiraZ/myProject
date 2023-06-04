package ru.ilmira.myProject.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import ru.ilmira.myProject.persist.model.EPostCondition;
import ru.ilmira.myProject.persist.model.Post;
import ru.ilmira.myProject.persist.model.Tag;

import java.util.List;


public class PostSpecification {

    public static Specification<Post> id(long postId) {
        return (root, query, builder) ->
                builder.equal(root.get("id"), postId);
    }

    public static Specification<Post> topic(String topic) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("topic").get("name")), topic.toLowerCase());
    }

    public static Specification<Post> hasTags(List<String> tags) {
        return (root, query, builder) -> {
            query.distinct(true);
            Join<Post, Tag> joinPostTag = root.join("tags", JoinType.LEFT);
            return joinPostTag.get("name").in(tags);
        };
    }

    public static Specification<Post> condition(EPostCondition condition) {
        return (root, query, builder) ->
                builder.equal(root.get("condition"), condition);
    }

    /**
     * Returns the configured {@code Specification<Post>},
     * excluded provided {@code EPostCondition}.
     *
     * @param condition, {@code EPostCondition} - must be excluded
     * @return configured {@code Specification<Post>}
     */
    public static Specification<Post> excludeCondition(EPostCondition condition) {
        return (root, query, builder) ->
                builder.notEqual(root.get("condition"), condition);
    }

    public static Specification<Post> fetchTags() {
        return (root, query, builder) -> {
            query.distinct(true);
            root.fetch("tags", JoinType.LEFT);
            return builder.conjunction();
        };
    }

    public static Specification<Post> fetchPictures() {
        return (root, query, builder) -> {
            query.distinct(true);
            root.fetch("pictures", JoinType.LEFT);
            return builder.conjunction();
        };
    }

    /**
     * Finds a posts by user ID
     *
     */
    public static Specification<Post> userId(long userId) {
        return (root, query, builder) ->
                builder.equal(root.get("owner").get("id"), userId);
    }


    /**
     * Finds a posts by username
     *
     */
    public static Specification<Post> username(String username) {
        return (root, query, builder) ->
                builder.equal(root.get("owner").get("username"), username);
    }
}
