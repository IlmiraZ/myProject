package ru.ilmira.myProject.service;

import org.springframework.stereotype.Service;
import ru.ilmira.myProject.service.dto.TopicDto;

import java.util.List;

@Service
public interface TopicService {
    List<TopicDto> findAll();

}
