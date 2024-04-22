package com.juliancambraia.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.juliancambraia.crudspring.dto.CourseDTO;
import com.juliancambraia.crudspring.dto.CoursePageDto;
import com.juliancambraia.crudspring.dto.mapper.CourseMapper;
import com.juliancambraia.crudspring.exception.RecordNotFoundException;
import com.juliancambraia.crudspring.model.Course;
import com.juliancambraia.crudspring.model.Lesson;
import com.juliancambraia.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@Service
public class CourseService {
  private final CourseRepository repository;

  private final CourseMapper courseMapper;

  public CourseService(CourseRepository repository, CourseMapper courseMapper) {
    this.repository = repository;
    this.courseMapper = courseMapper;
  }

  public CoursePageDto list(@PositiveOrZero int page, @Positive @Max(100) int size) {
    Page<Course> pageCourse = repository.findAll(PageRequest.of(page, size));
    List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDto).collect(Collectors.toList());
    return new CoursePageDto(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
  }

  public CourseDTO findById(@NotNull @Positive Long id) {
    return repository.findById(id)
        .map(courseMapper::toDto)
        .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CourseDTO create(@Valid @NotNull CourseDTO request) {

    var entity = courseMapper.toEntity(request);

    return courseMapper.toDto(repository.save(entity));
  }

  public CourseDTO update(@Valid @NotNull CourseDTO request,
      @NotNull @Positive Long id) {

    return repository.findById(id)
        .map(actual -> {
          Course course = courseMapper.toEntity(request);
          actual.setName(request.name());
          actual.setCategory(courseMapper.convertCategoryValue(request.category()));

          // actual.setLessons(course.getLessons()); // aqui gera um erro de hibernate -
          // Detached - Multiple representations of the same entity

          mergeLessonsForUpdate(actual, request);

          // actual.getLessons().clear();
          // course.getLessons().forEach(actual.getLessons()::add);

          return repository.save(actual);
        }).map(courseMapper::toDto)
        .orElseThrow(() -> new RecordNotFoundException(id));

  }

  private void mergeLessonsForUpdate(Course updateCourse, @Valid @NotNull CourseDTO requestDto) {
    List<Lesson> lessonsToRemove = updateCourse.getLessons().stream()
        .filter(lesson -> requestDto.lessons().stream()
            .anyMatch(lessonDto -> lessonDto._id() == 0 && lessonDto._id() != lesson.getId()))
        .toList();

    lessonsToRemove.forEach(updateCourse::removeLesson);

    requestDto.lessons().forEach(lessonDto -> {
      if (lessonDto._id() == 0) {
        updateCourse.addLesson(courseMapper.convertLessonDTOToLesson(lessonDto));
      } else {
        updateCourse.getLessons().stream()
            .filter(lesson -> lesson.getId() == lessonDto._id())
            .findAny()
            .ifPresent(lesson -> {
              lesson.setName(lessonDto.name());
              lesson.setYoutubeUrl(lessonDto.youtubeUrl());
            });
      }
    });
  }

  public void delete(@NotNull @Positive Long id) {
    repository.delete(repository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(id)));
  }
}
