// src/app/shared/services/message-map.service.ts

import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';

type ErrorMessageFn = (error: any) => string;
type ErrorMessageMap = { [key: string]: ErrorMessageFn };
export type IndividualMessageMap = { [fieldId: string]: { [errorKey: string]: string } };

@Injectable({ providedIn: 'root' })
export class MessageMapService {

  // ZENTRALE DEFINITION ALLER GLOBALEN FEHLERMELDUNGEN
  private readonly globalErrorMaps: { [key: string]: ErrorMessageMap } = {
    'email': {
      'required': () => 'Globale Meldung: Die E-Mail-Adresse ist Pflicht.',
      'email': () => 'Globale Meldung: Das E-Mail-Format ist ungültig.',
    },
    'password': {
      'required': () => 'Globale Meldung: Das Passwortfeld darf nicht leer sein.',
      'minlength': (error: any) => 
        `Globale Meldung: Das Passwort muss mindestens ${error.requiredLength} Zeichen haben.`,
    },
  };

  /**
   * Liefert die priorisierte Fehlermeldung (Individuell > Global).
   */
  public getPrioritizedErrorMessage(
    control: AbstractControl,
    fieldId: string,
    individualMessages: IndividualMessageMap | null
  ): string | null {
    
    if (control.invalid && control.errors) {
      const firstErrorKey = Object.keys(control.errors)[0];
      const errorData = control.errors[firstErrorKey];
      
      // 1. VERSUCH: Individuelle Nachricht finden
      const customMessage = individualMessages?.[fieldId]?.[firstErrorKey];
      if (customMessage) {
          return customMessage;
      }

      // 2. FALLBACK: Globale Nachricht verwenden
      const fieldErrorMap = this.globalErrorMaps[fieldId];
      if (!fieldErrorMap) return 'Unbekannter Fehler.';

      const messageFn = fieldErrorMap[firstErrorKey];
      
      return messageFn ? messageFn(errorData) : 'Unbekannter Fehler.';
    }

    return null;
  }
}