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
      enabled: [1],
      rol: ['ADMON'],
    });

    if (this.administrador) {
      this.isEditMode = true;
      this.form.patchValue(this.administrador);
    }
  }

  onSubmit() {
    const usuario: IUsuario = this.form.value;
  
    if (this.isEditMode) {
      this.usuarioService
        .actualizarUsuarioPorEmail(this.emailParam!, usuario)
        .subscribe(() => {
          this.formularioGuardado.emit(); 
        });
    } else {
      this.usuarioService.crearUsuario(usuario).subscribe(() => {
        this.formularioGuardado.emit(); 
      });
    }
  }
}
