import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, FormArray, FormControl as AngularFormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { PersonEmailAddress } from '../../../../../../types/person';
import { InputTypesEnum } from '../../../../../../types/InputTypesEnum';
import { FormControl as AppFormControl } from '../../../../../../shared/components/bootstrap/form-control/form-control';
import { PersonEmailAddressDtoId } from '../../../../../../types/dto/person/related/reference/contact/person-email-address-dto-id';
import { PersonEmailAddressDto } from '../../../../../../types/dto/person/related/contact/mail/person-email-address.dto';

@Component({
  selector: 'app-person-emails-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AppFormControl],
  templateUrl: './person-emails-form.html'
})
export class PersonEmailsForm {
  private fb = inject(FormBuilder);
  public inputTypes = InputTypesEnum;

  @Input({ required: true }) personId!: number | undefined;

  @Input() set emails(values: PersonEmailAddressDto[]) {
    this.setEmailsFormArray(values || []);
  }

  @Output() save = new EventEmitter<PersonEmailAddressDto>();
  @Output() delete = new EventEmitter<{ id: PersonEmailAddressDtoId }>();

  public form: FormGroup = this.fb.group({
    emails: this.fb.array([])
  });

  get emailsArray(): FormArray {
    return this.form.get('emails') as FormArray;
  }

  getGroup(index: number): FormGroup {
    return this.emailsArray.at(index) as FormGroup;
  }

  getControl(group: AbstractControl, path: string): AngularFormControl {
    return group.get(path) as AngularFormControl;
  }

  private setEmailsFormArray(emails: PersonEmailAddressDto[]): void {
    const formGroups = emails.map((item) => this.createEmailGroup(item));
    this.form.setControl('emails', this.fb.array(formGroups));
  }

  private createEmailGroup(item?: Partial<PersonEmailAddressDto>): FormGroup {
    return this.fb.group({
      id: this.fb.group({
        personId: [item?.id?.personId ?? this.personId, [Validators.required, Validators.min(1)]],
        emailAddressId: [item?.id?.emailAddressId ?? item?.emailAddress?.id ?? null]
      }),
      mainEmail: [item?.mainEmail ?? false, Validators.required],
      emailAddress: this.fb.group({
        id: [item?.emailAddress?.id ?? item?.id?.emailAddressId ?? null],
        emailAddress: [item?.emailAddress?.emailAddress ?? '', [Validators.required, Validators.email]]
      })
    });
  }

  addEmail(): void {
    if (!this.personId) return;

    this.emailsArray.push(
      this.createEmailGroup({
        id: {
          personId: this.personId,
          emailAddressId: undefined
        },
        mainEmail: this.emailsArray.length === 0,
        emailAddress: {
          id: undefined,
          emailAddress: ''
        }
      })
    );
  }

  deleteEmail(index: number): void {
    const group = this.getGroup(index);
    const rawValue = group.getRawValue();

    const personId = Number(rawValue.id?.personId || this.personId);
    const emailAddressId = rawValue.id?.emailAddressId ? Number(rawValue.id.emailAddressId) : (rawValue.emailAddress?.id ? Number(rawValue.emailAddress.id) : undefined);

    if (personId && emailAddressId) {
      this.delete.emit({
        id: {
          personId,
          emailAddressId
        }
      });
    } else {
      this.emailsArray.removeAt(index);
    }
  }

  setMainEmail(selectedIndex: number): void {
    this.emailsArray.controls.forEach((control, index) => {
      control.get('mainEmail')?.setValue(index === selectedIndex, { emitEvent: false });
    });
  }

  saveSingleEmail(index: number): void {
    const group = this.getGroup(index);
    if (group.invalid) return;

    const rawValue = group.getRawValue();
    const targetPersonId = rawValue.id?.personId || this.personId;

    if (!targetPersonId) {
      console.error('Speichern abgebrochen: personId ist nicht gesetzt!');
      return;
    }

    const emailStr = rawValue.emailAddress.emailAddress?.trim() ?? '';
    const emailAddressId = rawValue.id?.emailAddressId ? Number(rawValue.id.emailAddressId) : (rawValue.emailAddress?.id ? Number(rawValue.emailAddress.id) : undefined);

    const dto: PersonEmailAddressDto = {
      id: {
        personId: Number(targetPersonId),
        emailAddressId: emailAddressId
      },
      mainEmail: Boolean(rawValue.mainEmail),
      emailAddress: {
        id: emailAddressId,
        emailAddress: emailStr
      }
    };

    this.save.emit(dto);
  }
}