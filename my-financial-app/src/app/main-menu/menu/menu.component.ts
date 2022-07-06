import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarComponent } from 'src/app/shared/component/snackbar/snackbar.component';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit {

  reportInProgress: string = environment.apiUrl.concat('/report/sheet');

  constructor(private snackBar: MatSnackBar) { }

  ngOnInit(): void {

    if (localStorage.getItem('expense-created')) {
      this.snackBar.openFromComponent(SnackbarComponent, {
        duration: 7000,
        data: '💶 💶 ¡ Gasto creado correctamente ! 💶 💶 '
      });
      localStorage.removeItem('expense-created');
    }
  }

}
