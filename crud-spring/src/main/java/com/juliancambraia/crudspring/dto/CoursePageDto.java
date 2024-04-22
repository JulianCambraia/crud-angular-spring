package com.juliancambraia.crudspring.dto;

import java.util.List;

public record CoursePageDto(List<CourseDTO> courses, long totalElements, int totalPages) {

}
