import { Component, effect, inject, Input, OnInit, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { PersonState } from '../../store/person.models';
import { createPhoneNumber, deletePhoneNumber, updateGeneralData, updatePhoneNumber } from '../../store/person.actions';
import { AdditionalHardSkill, Person, PersonAdditionalHardSkill, PersonAddress, PersonCountry, PersonEmailAddress, PersonPhoneNumber, PersonProfessionQualification, PersonSoftSkill } from '../../../../types/person';
import { NgbdAccordionStatic } from '../../../../shared/components/bootstrap/accordion-static/accordion-static';
import { PersonDegreesForm } from '../forms/skills/person-degrees-form/person-degrees-form';
import { PersonGeneralForm } from '../forms/contact/person-general-form/person-general-form';
import { PersonNationalitiesForm } from '../forms/contact/person-nationalities-form/person-nationalities-form';

import { PersonPhonesForm } from '../forms/contact/person-phones-form/person-phones-form';
import { PersonEmailsForm } from '../forms/contact/person-emails-form/person-emails-form';
import { PersonProfessionsForm } from '../forms/skills/person-professions-form/person-professions-form';
import { PersonSoftSkillsForm } from '../forms/skills/person-soft-skills-form/person-soft-skills-form';
import { PersonAdditionalSkillsForm } from '../forms/skills/person-additional-skills-form/person-additional-skills-form';
import { PersonData } from '../../../../types/dto/person/person-data';
import { PersonOverview as PersonView } from '../../../../types/dto/person/person-overview';
import { PersonDto } from '../../../../types/dto/person/person.dto';
import { PersonPhoneNumberDto } from '../../../../types/dto/person/related/contact/phone/person-phone-number.dto';
import { PersonPhoneNumberDtoId } from '../../../../types/dto/person/related/reference/contact/person-phone-number-dto-id';
import { PersonEmailAddressDtoId } from '../../../../types/dto/person/related/reference/contact/person-email-address-dto-id';
import { PersonEmailAddressDto } from '../../../../types/dto/person/related/contact/mail/person-email-address.dto';
import { PersonAddressDto } from '../../../../types/dto/person/related/contact/address/person-address.dto';
import { PersonAddressDtoId } from '../../../../types/dto/person/related/reference/contact/person-address-dto-id';
import { PersonCountryDtoId } from '../../../../types/dto/person/related/reference/contact/person-country-dto-id';
import { PersonAddressesForm } from '../forms/contact/person-addresses-form/person-addresses-form';
import { PersonCountryDto } from '../../../../types/dto/person/related/contact/country/person-country-dto';

export interface AccordionSection {
  id: string;
  title: string;
  icon?: string;
}

@Component({
  selector: 'app-person-details',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NgbdAccordionStatic,
    //PersonDegreesForm,
    PersonGeneralForm,
    PersonNationalitiesForm,
    PersonPhonesForm,
    PersonEmailsForm,
    PersonAddressesForm
    //PersonProfessionsForm,
    //PersonSoftSkillsForm,
    //PersonAdditionalSkillsForm
  ],
  templateUrl: './person-details.html',
  styleUrl: './person-details.css',
})
export class PersonDetails {
  private store: Store<PersonState> = inject(Store<PersonState>);

  @Input({ required: true }) person!: PersonDto;

  public accordionSections: AccordionSection[] = [
    { id: 'generalData', title: 'Stammdaten & Kontaktdaten' },
    { id: 'nationalitys', title: 'Nationalitäten' },
    { id: 'addresses', title: 'Addressen' },
    { id: 'phones', title: 'Telefonnummern' },
    { id: 'emails', title: 'E-Mails' },
    { id: 'degrees', title: 'Abschlüsse' },
    { id: 'additionalSkills', title: 'Zusätzliche Fähigkeiten' },
    { id: 'softSkills', title: 'Soft Skills' },
    { id: 'professions', title: 'Berufe' },
  ];

  onSaveGeneralData(personData: PersonData): void {
    const personId = personData.id ?? this.person?.person?.id;

    if (personId !== null && personId !== undefined) {
      console.log('Zpdate PersonData:', personData);
      this.store.dispatch(updateGeneralData({ data: personData }));
    }
  }


  onSaveNationality(nationality: PersonCountryDto): void {
    console.log('Speichere Nationalitäten:', nationality);
  }

  onDeleteNationality(event: { id: PersonCountryDtoId }): void {
    console.log('Delete Adressen:', event.id);
  }

  onSaveAddress(address: PersonAddressDto): void {
    console.log('Speichere Adressen:', address);
  }

  onDeleteAddress(event: { id: PersonAddressDtoId }): void {
    console.log('Delete Adressen:', event.id);
  }

  onSavePhone(phone: PersonPhoneNumberDto): void {
    let action;
    if(phone.id.phoneNumberId) {
      console.log('Update Telefonnummer:', phone);
      action = updatePhoneNumber({data: phone})
    }
    else {
      console.log('Speichere Telefonnummer:', phone);
      action = createPhoneNumber({data: phone});
    }

    this.store.dispatch(action);
  }

  onDeletePhone(event: { id: PersonPhoneNumberDtoId }): void {
    this.store.dispatch(deletePhoneNumber({ id: event.id }));
  }

  onSaveEmail(email: PersonEmailAddressDto): void {
      let action;
    if(email.id.emailAddressId) {
      console.log('Update Email:', email);
      //action = updateEMai({data: email})
    }
    else {
      console.log('Speichere Email:', email);
      //action = createPhoneNumber({data: email});
    }

    //this.store.dispatch(action);
  }

  onDeleteEmail(event: { id: PersonEmailAddressDtoId }): void {
    //this.store.dispatch(deletePhoneNumber({ personId: event.id.personId, phoneId: event.id.phoneNumberId! }));
  }




  onSaveDegrees(degreesData: any): void {
    console.log('Speichere Degrees:', degreesData);
  }

  onSaveProfessions(professions: PersonProfessionQualification[]): void {
    console.log('Speichere PersonProfessios:', professions);
  }

  onSaveSoftSkills(softSkills: PersonSoftSkill[]): void {
    console.log('Speichere PersonSoftSkill:', softSkills);
  }

  onSaveAdditionalSkills(additionalSkills: PersonAdditionalHardSkill[]): void {
    console.log('Speichere AdditionalHardSkill:', additionalSkills);
  }
}