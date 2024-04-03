package com.blog.app.category.service;

import com.blog.app.category.dto.CategoryDto;
import com.blog.app.category.entity.Category;
import com.blog.app.category.repository.CategoryRepository;
import com.blog.app.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category existingCategory = getCategoryEntityById(categoryId);

        existingCategory.setTitle(categoryDto.getTitle());
        existingCategory.setDescription(categoryDto.getDescription());

        return modelMapper.map(categoryRepository.save(existingCategory), CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = getCategoryEntityById(categoryId);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        return categoryRepository.findAll().stream().map((category -> modelMapper.map(category, CategoryDto.class))).collect(Collectors.toList());
    }

    private Category getCategoryEntityById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category", "category id", id));
    }
}
