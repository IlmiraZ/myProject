package ru.ilmira.myProject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilmira.myProject.repositories.TopicRepository;
import ru.ilmira.myProject.service.dto.TopicDto;
import ru.ilmira.myProject.service.dto.TopicMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Override
    public List<TopicDto> findAll() {

        return topicRepository.findAll()
                .stream()
                .map(topicMapper::fromTopic)
                .collect(Collectors.toList());
    }
}
