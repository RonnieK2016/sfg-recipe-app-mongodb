package com.udemy.sfg.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImageFile(String recipeId, MultipartFile file);
}
