package ru.ilmira.myProject.service.dto;

import ru.ilmira.myProject.persist.model.Topic;

public interface TopicMapper {
    TopicDto fromTopic(Topic topic);
}
