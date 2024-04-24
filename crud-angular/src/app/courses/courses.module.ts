import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { ReactiveFormsModule } from '@angular/forms';


import { CoursesListComponent } from './components/courses-list/courses-list.component';
import { CourseFormComponent } from './containers/course-form/course-form.component';
import { CoursesComponent } from './containers/courses/courses.component';
import { CoursesRoutingModule } from './courses-routing.module';
import { MatPaginatorModule } from '@angular/material/paginator';


@NgModule({
    imports: [
    CommonModule,
    CoursesRoutingModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    CoursesComponent,
    CourseFormComponent,
    CoursesListComponent
]
})
export class CoursesModule { }
