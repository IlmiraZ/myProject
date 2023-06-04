package ru.ilmira.myProject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ilmira.myProject.persist.model.Picture;
import ru.ilmira.myProject.service.dto.PictureData;
import ru.ilmira.myProject.service.dto.PictureDto;

import java.util.List;
import java.util.Optional;

/**
 * Interface PictureService
 *
 * @see PictureDto
 */
@Service
public interface PictureService {

    Optional<PictureDto> getPictureDataById(long id);

    String createPicture(byte[] pictureData);

    List<PictureData> savePictures(List<MultipartFile> pictures);

    Optional<Picture> getPictureById(long id);
}
