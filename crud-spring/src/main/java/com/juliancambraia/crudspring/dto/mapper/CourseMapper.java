package com.juliancambraia.crudspring.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.juliancambraia.crudspring.dto.CourseDTO;
import com.juliancambraia.crudspring.dto.LessonDTO;
import com.juliancambraia.crudspring.enums.Category;
import com.juliancambraia.crudspring.model.Course;
import com.juliancambraia.crudspring.model.Lesson;

@Component
public class CourseMapper {

  public CourseDTO toDto(Course course) {
    if (course == null) {
      return null;
    }

    List<LessonDTO> lessons = course.getLessons()
        .stream()
        .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
        .collect(Collectors.toList());

    return new CourseDTO(course.getId(), course.getName(), course.getCategory().getName(),
        lessons);
  }

  public Course toEntity(CourseDTO courseDTO) {
    if (courseDTO == null) {
      return null;
    }

    Course course = new Course();
    if (courseDTO.id() != null) {
      course.setId(courseDTO.id());
    }

    course.setName(courseDTO.name());
    course.setCategory(convertCategoryValue(courseDTO.category()));

    Set<Lesson> lessons = courseDTO.lessons().stream()
        .map(lessonDTO -> {
          var lesson = new Lesson();
          if (lesson.getId() > 0) {
            lesson.setId(lessonDTO._id());
          }
          lesson.setId(lessonDTO._id());
          lesson.setName(lessonDTO.name());
          lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
          lesson.setCourse(course);
          return lesson;
        }).collect(Collectors.toSet());

    course.setLessons(lessons);

    return course;
  }

  public Category convertCategoryValue(String value) {
    if (value == null) {
      return null;
    }

    return switch (value) {
      case "Front-end" -> Category.FRONT_END;
      case "Back-end" -> Category.BACK_END;
      default -> throw new IllegalArgumentException("Categoria inválida:" + value);
    };
  }

  public Lesson convertLessonDTOToLesson(LessonDTO lessonDTO) {
    Lesson lesson = new Lesson();
    lesson.setId(lessonDTO._id());
    lesson.setName(lessonDTO.name());
    lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
    return lesson;
  }

}
