import { Injectable } from '@angular/core';
import { AbstractControl, FormArray, FormGroup } from '@angular/forms';

export interface FormManagerConfig<T> {
  createGroupFn: (item?: Partial<T>) => FormGroup;
  getIdFn: (group: FormGroup) => number | null;
  mainControlPath?: string;
}
@Injectable()
export class FormListManagerService<T> {
  public array!: FormArray;
  private config!: FormManagerConfig<T>;
  public savedMainId: any = null;

  private groupSnapshots = new WeakMap<FormGroup, string>();

  init(array: FormArray, config: FormManagerConfig<T>): void {
    this.array = array;
    this.config = config;
  }

  public syncFromList(items: T[], customMatchFn?: (group: FormGroup, item: T) => boolean): void {
    if (!this.array || !this.config) return;

    if (this.config.mainControlPath) {
      const mainItem = items.find(item => Boolean((item as any)[this.config.mainControlPath!]));
      if (mainItem) {
        const tempGroup = this.config.createGroupFn(mainItem);
        this.savedMainId = this.config.getIdFn(tempGroup);
      } else {
        this.savedMainId = null;
      }
    }
  
    if (this.array.length === 0) {
      this.initFromList(items);
      return;
    }

    const controls = this.array.controls as FormGroup[];

    // Standard-Matcher über getIdFn
    const defaultMatchFn = (group: FormGroup, item: T) => {
      const groupKey = this.config.getIdFn(group);
      const tempGroup = this.config.createGroupFn(item);
      const itemKey = this.config.getIdFn(tempGroup);

      if (!this.hasValidId(groupKey)) {
        return true;
      }


      return this.hasValidId(itemKey) && this.areIdsEqual(groupKey, itemKey);
    };

    const isMatch = customMatchFn || defaultMatchFn;

    // 1. Store-Einträge abgleichen / hinzufügen
    items.forEach(storeItem => {
      const existingGroup = controls.find(group => isMatch(group, storeItem));

      if (existingGroup) {
        // Falls die Zeile noch neu war (keine ID) ODER nicht vom User bearbeitet wurde
        const wasNew = !this.hasValidId(this.config.getIdFn(existingGroup));
    
        if (wasNew || !existingGroup.dirty) {
          const freshGroup = this.config.createGroupFn(storeItem);
          
          existingGroup.patchValue(freshGroup.getRawValue(), { emitEvent: false });
          
          const newSnapshot = JSON.stringify(existingGroup.getRawValue());
          this.groupSnapshots.set(existingGroup, newSnapshot);
  
          this.markAsPristineDeep(existingGroup);
        }
      } else {
        // Eintrag existiert noch nicht im Formular -> Neu anfügen
        const newGroup = this.config.createGroupFn(storeItem);
        this.array.push(newGroup);
        this.registerPristineTracking(newGroup);
      }
    });

    // 2. Im Store gelöschte Einträge aus dem Formular entfernen (nur unberührte, gespeicherte)
    for (let i = controls.length - 1; i >= 0; i--) {
      const group = controls[i] as FormGroup;
      if (this.isSaved(i) && !group.dirty) {
        const existsInStore = items.some(item => isMatch(group, item));
        if (!existsInStore) {
          this.array.removeAt(i);
        }
      }
    }
  }

  /**
   * Registriert das Pristine-Tracking für eine FormGroup.
   */
  public registerPristineTracking(group: FormGroup): void {
    const initialSnapshot = JSON.stringify(group.getRawValue());
    this.groupSnapshots.set(group, initialSnapshot);

    group.valueChanges.subscribe(() => {
      this.checkPristineState(group);
    });
  }

  /**
   * Prüft, ob der aktuelle Wert dem initialen Snapshot entspricht und
   * setzt die Gruppe bei Übereinstimmung wieder auf pristine zurück.
   */
  public checkPristineState(group: FormGroup): void {
    const initialSnapshot = this.groupSnapshots.get(group);
    if (!initialSnapshot) return;

    const currentSnapshot = JSON.stringify(group.getRawValue());
    if (currentSnapshot === initialSnapshot) {
      this.markAsPristineDeep(group);
    }
  }

  public markAsPristineDeep(control: AbstractControl): void {
    if (control instanceof FormGroup || control instanceof FormArray) {
      Object.values(control.controls).forEach(c => this.markAsPristineDeep(c));
    }
    control.markAsPristine();
  }

  public markAsSaved(index: number, updatedItem?: Partial<T>): void {
    const group = this.getGroup(index);
    if (!group) return;

    if (updatedItem) {
      group.patchValue(updatedItem, { emitEvent: false });
    }

    const newSnapshot = JSON.stringify(group.getRawValue());
    this.groupSnapshots.set(group, newSnapshot);
    this.markAsPristineDeep(group);
  }

  // --- Speichern & Status-Prüfungen ---

  isSaved(index: number): boolean {
    const group = this.getGroup(index);
    if (!group) return false;
    const id = this.config?.getIdFn(group);
    return this.hasValidId(id);
  }

  isNew(index: number): boolean {
    return !this.isSaved(index);
  }

  isModified(index: number): boolean {
    return this.isSaved(index) && this.getGroup(index)?.dirty;
  }

  isUnsaved(index: number): boolean {
    const group = this.getGroup(index);
    if (!group) return false;
    return this.isNew(index) || group.dirty;
  }

  get hasUnsavedItem(): boolean {
    return this.array?.controls.some((_, i) => this.isNew(i)) ?? false;
  }

  get hasAnyUnsavedChanges(): boolean {
    return this.array?.controls.some((_, i) => this.isUnsaved(i)) ?? false;
  }

  isSaveDisabled(index: number): boolean {
    const group = this.getGroup(index);
    if (!group || group.invalid) return true;

    return this.isSaved(index) ? !group.dirty : false;
  }

  // --- Lösch-Logik & Tooltips ---

  canDelete(index: number): boolean {
    const group = this.getGroup(index);
    if (!group) return false;

    const id = this.config.getIdFn(group);

    if (this.config.mainControlPath && this.hasValidId(id) && this.areIdsEqual(id, this.savedMainId)) {
      return false;
    }

    return true;
  }

  getDeleteTooltip(index: number): string {
    if (this.canDelete(index)) return 'Eintrag entfernen';

    const group = this.getGroup(index);
    const id = this.config.getIdFn(group);

    if (this.config.mainControlPath && this.hasValidId(id) && this.areIdsEqual(id, this.savedMainId)) {
      return 'Dieser Eintrag ist im Backend als Haupt-Eintrag gespeichert. Speichern Sie zuerst den neuen Haupt-Eintrag.';
    }

    return 'Löschen nicht möglich.';
  }

  // --- Haupt-Entität Logik (Radio Buttons) ---

  hasSavedMain(): boolean {
    if (!this.config.mainControlPath) return false;
    return this.array.controls.some((_, i) => {
      const group = this.getGroup(i);
      return this.isSaved(i) && Boolean(group.get(this.config.mainControlPath!)?.value);
    });
  }

  canSelectMain(index: number): boolean {
    const group = this.getGroup(index);
    if (!group) return false;
  
    return this.isSaved(index) || !this.hasSavedMain();
  }

  getMainRadioTooltip(index: number): string {
    if (this.canSelectMain(index)) return 'Als Haupt-Eintrag festlegen';
  
    if (!this.isSaved(index)) {
      return 'Speichern Sie diesen Eintrag zuerst ab, um ihn als Haupt-Eintrag festzulegen.';
    }
  
    return '';
  }

  setMain(selectedIndex: number): void {
    if (!this.config.mainControlPath || !this.canSelectMain(selectedIndex)) return;
  
    this.array.controls.forEach((ctrl, idx) => {
      const group = ctrl as FormGroup;
      const mainCtrl = group.get(this.config.mainControlPath!);
      const isTarget = idx === selectedIndex;
  
      if (!mainCtrl) return;
  
      if (isTarget) {
        // 1. NEUER HAUPTEINTRAG: Wird auf true gesetzt & als ungespeichert markiert
        if (!mainCtrl.value) {
          mainCtrl.setValue(true);
          mainCtrl.markAsDirty();
          this.checkPristineState(group);
        }
      } else {
        // 2. ALTER HAUPTEINTRAG: Wird abgewählt
        if (mainCtrl.value) {
          mainCtrl.setValue(false, { emitEvent: false });
          mainCtrl.markAsPristine(); // Control explizit auf pristine zurücksetzen!
  
          // Prüfen, ob in der Form-Gruppe SONST noch Felder geändert wurden
          const snapshotJson = this.groupSnapshots.get(group);
          if (snapshotJson) {
            const snapshot = JSON.parse(snapshotJson);
            const currentVal = group.getRawValue();
  
            // Vergleiche alle Felder AUSSER dem mainControlPath mit dem Snapshot
            const hasOtherChanges = Object.keys(currentVal).some(key => {
              if (key === this.config.mainControlPath) return false;
              return JSON.stringify(currentVal[key]) !== JSON.stringify(snapshot[key]);
            });
  
            if (!hasOtherChanges) {
              // Keine anderen Felder geändert -> Zeile bleibt / wird wieder sauber
              this.markAsPristineDeep(group);
            } else {
              this.checkPristineState(group);
            }
          } else {
            this.checkPristineState(group);
          }
        }
      }
    });
  }

  // --- Helper ---

  getGroup(index: number): FormGroup {
    return this.array.at(index) as FormGroup;
  }

  initFromList(items: T[]): void {
    if (!this.array || !this.config) return;

    if (this.config.mainControlPath) {
      const mainItem = items.find(item => (item as any)[this.config.mainControlPath!]);
      const tempGroup = mainItem ? this.config.createGroupFn(mainItem) : null;
      this.savedMainId = tempGroup ? this.config.getIdFn(tempGroup) : null;
    }

    const groups = items.map(item => this.config.createGroupFn(item));
    this.array.clear();
    groups.forEach(g => {
      this.array.push(g);
      this.registerPristineTracking(g);
    });
  }

  private hasValidId(id: any): boolean {
    if (id === null || id === undefined || id === '') return false;
    if (typeof id === 'object') {
      return Object.values(id).some(val => val !== null && val !== undefined && val !== '');
    }
    return true;
  }

  private areIdsEqual(id1: any, id2: any): boolean {
    if (id1 === id2) return true;
    if (!id1 || !id2) return false;
    if (typeof id1 === 'object' && typeof id2 === 'object') {
      return JSON.stringify(id1) === JSON.stringify(id2);
    }
    return false;
  }
}


/*

<!-- Radio-Button mit Sperre -->
<span class="d-inline-block" [ngbTooltip]="manager.getLockTooltip(i) || manager.getMainRadioTooltip(i)" container="body">
  <input 
    type="radio" 
    class="form-check-input" 
    [checked]="group.get('mainAddress')?.value"
    [disabled]="!manager.canSelectMain(i) || manager.isItemLocked(i)"
    (change)="manager.setMain(i)" />
</span>

<!-- Speichern-Button mit Sperre -->
<button 
  type="button" 
  class="btn btn-sm"
  [disabled]="manager.isSaveDisabled(i) || manager.isItemLocked(i)"
  (click)="saveSingleAddress(i)">
  {{ manager.isSaved(i) ? 'Aktualisieren' : 'Speichern' }}
</button>


<div 
  [formGroupName]="i" 
  class="card mb-3 transition-all"
  [ngClass]="{
    'opacity-50': manager.isItemLocked(i),
    'border-primary shadow-sm bg-white': group.get('mainAddress')?.value && !manager.isItemLocked(i),
    'border-warning bg-light': group.dirty && manager.isSaved(i)
  }">
  */