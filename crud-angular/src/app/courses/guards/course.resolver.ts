import { Inject, inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { Course } from '../model/course';
import { CoursesService } from '../services/courses.service';

export const courseResolver: ResolveFn<Course> = (route, state) => {
  if (route.params && route.params['id']) {
    return inject(CoursesService).findById(route.params['id']);
  }

  return { _id: '', name: '', category: '', lessons: [] };
};
