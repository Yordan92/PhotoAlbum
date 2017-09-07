package com.example.demo.repository;

import com.example.demo.model.Picture;

import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends CrudRepository<Picture, Long>{

}
