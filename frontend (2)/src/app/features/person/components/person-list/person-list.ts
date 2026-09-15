import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NgbdAccordionStatic } from '../../../../shared/components/bootstrap/accordion-static/accordion-static';

import * as PersonSelectors from '../../store/person.selectors';
import * as PersonActions from '../../store/person.actions';
import { map } from 'rxjs';
import { NgbAccordionItem } from '@ng-bootstrap/ng-bootstrap';
import { PersonDetails } from '../person-details/person-details';

@Component({
  selector: 'app-person-list',
  standalone: true,
  imports: [CommonModule, NgbdAccordionStatic, PersonDetails],
  templateUrl: './person-list.html',
  styleUrl: './person-list.css',
})
export class PersonList implements OnInit {
  private store = inject(Store);
  private route = inject(ActivatedRoute);
  private destroyRef = inject(DestroyRef);

  readonly searchResults$ = this.store.select(PersonSelectors.selectPersonList).pipe(
    map((persons) =>
      persons.map((person) => ({
        ...person,
        id: person.id!,
      })) as unknown as NgbAccordionItem[]
    )
  );
  readonly isListLoading$ = this.store.select(PersonSelectors.selectIsListLoading);
  readonly detailsCache$ = this.store.select(PersonSelectors.selectPersonDetailsCache);
  readonly loadingDetailIds$ = this.store.select(PersonSelectors.selectLoadingDetailIds);

  ngOnInit(): void {
    this.route.queryParams
      .pipe(takeUntilDestroyed(this.destroyRef)) // <-- 2. Referenz hier übergeben
      .subscribe(() => {
        this.store.dispatch(PersonActions.loadPersonList());
      });
  }

  onToggleAccordion(id: number): void {
    if (id) {
      this.store.dispatch(PersonActions.togglePersonAccordion({ id }));
    }
  }

  isLoadingDetails(loadingIds: number[] | null, id: number): boolean {
    return !!loadingIds?.includes(id);
  }
}