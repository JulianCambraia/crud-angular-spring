import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Course } from '../../model/course';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss'
})
export class CoursesListComponent {

  @Input() courses: Course[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter();
  @Output() delete = new EventEmitter();


  readonly displayedColumns = ['_id','name', 'category', 'actions'];

  constructor(){

  }

  onAdd() {
    console.log('onAdd');
    this.add.emit(true);
  }

  onEdit(course: Course) {
    console.log(course);
    this.edit.emit(course);
  }

  onDelete(course: Course) {
    console.log(course);
    this.delete.emit(course);
  }

}
