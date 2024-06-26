package com.juliancambraia.crudspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.juliancambraia.crudspring.dto.CourseDTO;
import com.juliancambraia.crudspring.dto.CoursePageDto;
import com.juliancambraia.crudspring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/api/courses")
@Validated
public class CourseController {

  private final CourseService service;

  public CourseController(CourseService service) {
    this.service = service;
  }

  @GetMapping
  public CoursePageDto findAll(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
      @Positive @Max(100) @RequestParam(defaultValue = "10") int pageSize) {
    return service.list(page, pageSize);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public CourseDTO create(@RequestBody @Valid CourseDTO request) {
    return service.create(request);
  }

  @GetMapping("/{id}")
  public CourseDTO findById(@PathVariable("id") @NotNull @Positive Long id) {
    return service.findById(id);
  }

  @PutMapping("/{id}")
  public CourseDTO update(@RequestBody @Valid @NotNull CourseDTO request,
      @PathVariable("id") @NotNull @Positive Long id) {

    return service.update(request, id);

  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") @NotNull @Positive Long id) {
    service.delete(id);
  }
}
