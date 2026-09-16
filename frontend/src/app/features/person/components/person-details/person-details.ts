import { Component, inject, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { Store } from '@ngrx/store';
import { PersonState } from '../../store/person.models';
import { createAdditionalSkill, createAddress, createDegree, createEmail, createNationality, createPhoneNumber, createProfession, createSoftSkill, createWorkExperience, deleteAdditionalSkill, deleteAddress, deleteDegree, deleteEmail, deleteNationality, deletePhoneNumber, deleteProfession, deleteSoftSkill, deleteWorkExperience, updateAdditionalSkill, updateAddress, updateDegree, updateEmail, updateGeneralData, updateNationality, updatePhoneNumber, updateProfession, updateSoftSkill, updateWorkExperience } from '../../store/person.actions';
import { NgbdAccordionStatic } from '../../../../shared/components/bootstrap/accordion-static/accordion-static';
import { PersonDegreesForm } from '../forms/skills/person-degrees-form/person-degrees-form';
import { PersonGeneralForm } from '../forms/contact/person-general-form/person-general-form';
import { PersonNationalitiesForm } from '../forms/contact/person-nationalities-form/person-nationalities-form';

import { PersonPhonesForm } from '../forms/contact/person-phones-form/person-phones-form';
import { PersonEmailsForm } from '../forms/contact/person-emails-form/person-emails-form';
import { PersonWorkExperience } from '../forms/skills/person-work-experience/person-work-experience';
import { PersonAddressesForm } from '../forms/contact/person-addresses-form/person-addresses-form';
import { PersonSoftSkillsForm } from '../forms/skills/person-soft-skills-form/person-soft-skills-form';
import { PersonAdditionalSkillsForm } from '../forms/skills/person-additional-skills-form/person-additional-skills-form';
import { PersonData, PersonDto } from '../../../../types/dto/person/person-dto';
import { PersonAddressDto, PersonCountryDto, PersonEmailAddressDto, PersonPhoneNumberDto } from '../../../../types/dto/person/person-contact.dto';
import { PersonAdditionalHardSkillDtoId, PersonAddressDtoId, PersonCountryDtoId, PersonDegreeDtoId, PersonEmailAddressDtoId, PersonPhoneNumberDtoId, PersonProfessionQualificationDtoId, PersonSoftSkillDtoId, PersonWorkExperienceDtoId } from '../../../../types/dto/person/person-id.dto';
import { PersonAdditionalHardSkillDto, PersonDegreeDto, PersonProfessionQualificationDto, PersonSoftSkillDto, PersonWorkExperienceDto } from '../../../../types/dto/person/person-skill.dto';
import { PersonProfessionsForm } from '../forms/skills/person-professions-form/person-professions-form';

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
    PersonGeneralForm,
    PersonNationalitiesForm,
    PersonPhonesForm,
    PersonEmailsForm,
    PersonAddressesForm,
    PersonDegreesForm,
    PersonProfessionsForm,
    PersonSoftSkillsForm,
    PersonAdditionalSkillsForm,
    PersonWorkExperience,
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
    { id: 'workExperience', title: 'Workexperience' },
  ];

  onSaveGeneralData(personData: PersonData): void {
    const personId = personData.id ?? this.person?.person?.id;

    if (personId !== null && personId !== undefined) {
      console.log('Zpdate PersonData:', personData);
      this.store.dispatch(updateGeneralData({ data: personData }));
    }
  }

  onSaveNationality(event: { data: PersonCountryDto; isNew: boolean }): void {
    const action = event.isNew
      ? createNationality({ data: event.data })
      : updateNationality({ data: event.data });
  
    this.store.dispatch(action);
  }

  onDeleteNationality(id: PersonCountryDtoId): void {
    this.store.dispatch(deleteNationality({ id: id }));
  }

  onSaveAddress(address: PersonAddressDto): void {
    const action = address.id?.addressId
      ? updateAddress({data: address})
      : createAddress({data: address})
    
    this.store.dispatch(action);
  }

  onDeleteAddress(id: PersonAddressDtoId): void {
    this.store.dispatch(deleteAddress({ id: id }));
  }

  onSavePhone(phone: PersonPhoneNumberDto): void {
    const action = phone.id?.phoneNumberId
      ? updatePhoneNumber({data: phone})
      : createPhoneNumber({data: phone})

    this.store.dispatch(action);
  }

  onDeletePhone(id: PersonPhoneNumberDtoId): void {
    this.store.dispatch(deletePhoneNumber({ id: id}));
  }

  onSaveEmail(email: PersonEmailAddressDto): void {
    const action = email.id?.emailAddressId
      ? updateEmail({data: email})
      : createEmail({data: email})

    this.store.dispatch(action);
  }
  
  onDeleteEmail(id: PersonEmailAddressDtoId): void {
    this.store.dispatch(deleteEmail({ id: id }));
  }

  onSaveDegree(degree: PersonDegreeDto): void {
    const action = degree.id?.degreeId
      ? updateDegree({data: degree})
      : createDegree({data: degree})

    this.store.dispatch(action);
  }

  onDeleteDegree(id: PersonDegreeDtoId): void {
    this.store.dispatch(deleteDegree({ id: id }));
  }

  onSaveAdditionalSkill(additionalSkill: PersonAdditionalHardSkillDto): void {
    const action = additionalSkill.id?.additionalHardSkillId
      ? updateAdditionalSkill({data: additionalSkill})
      : createAdditionalSkill({data: additionalSkill})

    this.store.dispatch(action);
  }

  onDeleteAdditionalSkill(id: PersonAdditionalHardSkillDtoId): void {
    this.store.dispatch(deleteAdditionalSkill({ id: id }));
  }

  onSaveProfession(profession: PersonProfessionQualificationDto): void {
    const action = profession.id?.professionQualificationId
      ? updateProfession({data: profession})
      : createProfession({data: profession})

    this.store.dispatch(action);
  }

  onDeleteProfession(id: PersonProfessionQualificationDtoId): void {
    this.store.dispatch(deleteProfession({ id: id }));
  }

  onSaveSoftSkill(softSkill: PersonSoftSkillDto): void {
    const action = softSkill.id?.softSkillId
      ? updateSoftSkill({data: softSkill})
      : createSoftSkill({data: softSkill})

    this.store.dispatch(action);
  }

  onDeleteSoftSkill(id: PersonSoftSkillDtoId): void {
    this.store.dispatch(deleteSoftSkill({ id: id }));
  }

  onSaveWorkExperience(workExperience: PersonWorkExperienceDto): void {
    const action = workExperience.id?.workExperienceId
      ? updateWorkExperience({data: workExperience})
      : createWorkExperience({data: workExperience});


    this.store.dispatch(action);
  }

  onDeleteWorkExperience(id: PersonWorkExperienceDtoId): void {
    this.store.dispatch(deleteWorkExperience({ id: id }));
  }

  onMergeWorkexperience(): void {
    
  }
}