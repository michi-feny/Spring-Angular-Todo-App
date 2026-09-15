// src/app/login/login.component.ts

//import { Component, OnInit } from '@angular/core';
//import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
//import { CommonModule } from '@angular/common'; 
//import { ReactiveFormsModule } from '@angular/forms'; 
//import { MessageMapService, IndividualMessageMap } from '../shared/services/message-map.service'; 
//
//// Hilfs-Interface, das der Getter zurückgibt
//interface FieldState {
//  message: string | null;
//  isInvalid: boolean;
//  isValid: boolean;
//}
//
//@Component({
//  selector: 'app-login',
//  standalone: true,
//  imports: [CommonModule, ReactiveFormsModule],
//  templateUrl: './login.component.html',
//})
//export class LoginComponent {
//
//  // 1. Angular Validatoren Definition
//  loginForm: FormGroup = new FormGroup({
//    email: new FormControl('', [Validators.required, Validators.email]),
//    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
//  });
//
//  // 2. Individuelle Nachrichten für diese Komponente
//  private individualErrorMessages: IndividualMessageMap = {
//    'password': {
//      'required': 'Individuelle Meldung: Ein Passwortfeld darf hier nicht leer bleiben.', 
//      'minlength': 'Individuelle Meldung: Das Passwort ist hier EXAKT 8 Zeichen lang.', 
//    },
//    'email': {
//      'required': 'Individuelle Meldung: E-Mail ist für die Registrierung zwingend.',
//    }
//  };
//
//  constructor(private messageMapService: MessageMapService) {}
//
//  private getControl(name: string): AbstractControl | null {
//    return this.loginForm.get(name);
//  }
//
//  // --- GETTER FÜR EMAIL ---
//  get emailState(): FieldState {
//    const control = this.getControl('email');
//    if (!control) return { message: null, isInvalid: false, isValid: false };
//    
//    const shouldShowError = control.invalid && (control.touched || control.dirty);
//
//    const errorMessage = shouldShowError 
//      ? this.messageMapService.getPrioritizedErrorMessage(control, 'email', this.individualErrorMessages)
//      : null;
//
//    return {
//      message: errorMessage,
//      isInvalid: !!errorMessage, 
//      isValid: control.valid && !shouldShowError 
//    };
//  }
//  
//  // --- GETTER FÜR PASSWORT ---
//  get passwordState(): FieldState {
//    const control = this.getControl('password');
//    if (!control) return { message: null, isInvalid: false, isValid: false };
//
//    const shouldShowError = control.invalid && (control.touched || control.dirty);
//
//    const errorMessage = shouldShowError 
//      ? this.messageMapService.getPrioritizedErrorMessage(control, 'password', this.individualErrorMessages)
//      : null;
//
//    return {
//      message: errorMessage,
//      isInvalid: !!errorMessage,
//      isValid: control.valid && !shouldShowError
//    };
//  }
//
//  onSubmit(): void {
//    this.loginForm.markAllAsTouched();
//    if (this.loginForm.invalid) {
//      console.warn("Validierung fehlgeschlagen.");
//      return;
//    }
//    console.log('Login erfolgreich mit:', this.loginForm.value);
//  }
//}