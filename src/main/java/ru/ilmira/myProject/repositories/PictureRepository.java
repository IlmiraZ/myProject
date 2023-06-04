package ru.ilmira.myProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilmira.myProject.persist.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
