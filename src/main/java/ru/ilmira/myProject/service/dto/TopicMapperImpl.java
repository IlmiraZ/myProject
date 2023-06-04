package ru.ilmira.myProject.service.dto;

import org.springframework.stereotype.Component;
import ru.ilmira.myProject.persist.model.Topic;

/**
 * TopicMapperImpl.
 *
 */
@Component
public class TopicMapperImpl implements TopicMapper{
    @Override
    public TopicDto fromTopic(Topic topic) {
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }
}
