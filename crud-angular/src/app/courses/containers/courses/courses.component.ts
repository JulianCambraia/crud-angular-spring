import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';
import { CoursePage } from './../../model/course-page';

import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { CoursesListComponent } from '../../components/courses-list/courses-list.component';
import { NgIf, AsyncPipe } from '@angular/common';
import { MatToolbar } from '@angular/material/toolbar';
import { MatCard, MatCardContent } from '@angular/material/card';

@Component({
    selector: 'app-courses',
    templateUrl: './courses.component.html',
    styleUrl: './courses.component.scss',
    standalone: true,
    imports: [
        MatCard,
        MatCardContent,
        MatToolbar,
        NgIf,
        CoursesListComponent,
        MatPaginator,
        MatProgressSpinner,
        AsyncPipe,
    ],
})
export class CoursesComponent implements OnInit {
  courses$: Observable<CoursePage> | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  pageIndex = 0;
  pageSize = 10;
  //@Output() refresh;

  displayedColumns = ['_id', 'name', 'category', 'actions'];

  constructor(
    private courseService: CoursesService,
    private dialog: MatDialog,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.refresh();
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.courses$ = this.courseService
      .list(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError((error) => {
          this.onError('Erro ao carregar cursos.');
          return of({ courses: [], totalElements: 0 } as CoursePage);
        })
      );
  }
  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg,
    });
  }

  ngOnInit(): void {
    // throw new Error('Method not implemented.');
  }

  onAdd() {
    console.log('onAdd');
    this.router.navigate(['new'], { relativeTo: this.activatedRoute });
  }

  onEdit(course: Course) {
    console.log('onEdit', course);
    this.router.navigate(['edit', course._id], {
      relativeTo: this.activatedRoute,
    });
  }

  onDelete(course: Course) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Tem certeza que deseja remover este curso?',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.courseService.remove(course._id).subscribe(
          () => {
            this.refresh();
            this.snackBar.open('Curso removido com sucesso.', 'X', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
            });
          },
          () => this.onError('Erro ao tentar remover curso.')
        );
      }
    });
  }
}
