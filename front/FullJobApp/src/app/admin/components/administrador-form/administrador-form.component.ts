import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IUsuario } from '../../../core/interfaces/iusuario';
import { UsuarioService } from '../../../core/services/usuario.service';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-administrador-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './administrador-form.component.html',
  styleUrls: ['./administrador-form.component.css'],
})
export class AdministradorFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);
  private authService = inject(AuthService);
  private router = inject(Router);

  @Input() administrador: IUsuario | null = null;
  @Output() formularioGuardado = new EventEmitter<void>();

  form!: FormGroup;
  isEditMode = false;
  emailParam: string | null = null;

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
    });

    if (this.administrador) {
      this.isEditMode = true;
      this.emailParam = this.administrador.email;
      this.form.patchValue(this.administrador);
    }
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const usuario: IUsuario = this.form.value;

    const finalizar = () => {
      this.form.reset();
      this.router.navigate(['/admin/administradores']);
      this.formularioGuardado.emit();
    };

    if (this.isEditMode) {
      this.usuarioService
        .actualizarAdmin(this.emailParam!, usuario)
        .subscribe(finalizar);
    } else {
      this.authService.registrarAdmin(usuario).subscribe(finalizar);
    }
  }
}
