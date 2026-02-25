import { Component, inject, Input } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTypesEnum } from '../../../../types/InputTypesEnum';
import { FormControl as BootstrapFormControl } from '../../../../shared/components/bootstrap/form-control/form-control';
import { CommonModule } from '@angular/common';
import { Todo } from '../../../../types/Todo';
import { CreateTodoRequest, UpdateTodoRequest } from '../../../../types/dto/request/todos.requests';

@Component({
  selector: 'app-todo-form',
  imports: [BootstrapFormControl, ReactiveFormsModule, CommonModule],
  templateUrl: './todo-form.html',
  styleUrl: './todo-form.css',
})
export class TodoForm {

    @Input() todo: Todo|null = null;

    public readonly InputTypesEnum = InputTypesEnum;
    public todoForm!: FormGroup;

    public readonly isDoneOptions:Array<{ value: boolean; label: string }> = new Array(
        { value: true, label: "Erledigt"},
        { value: false, label: "Offen"}
    );

    ngOnInit(): void {
        this.todoForm = new FormGroup({
            title: new FormControl(this.todo?.title ?? '', [Validators.required]),
            description: new FormControl(this.todo?.description ?? '', [Validators.required])
        });

        if(this.todo !== null) {
            this.todoForm.addControl('isDone', new FormControl(this.todo.isDone));
            this.todoForm.addControl('id', new FormControl(this.todo.id));
        }
    }

    get title():AbstractControl<FormControl> {
        return this.todoForm.get('title')!;
    }

    get description():AbstractControl<FormControl> {
        return this.todoForm.get('description')!;
    }

    get isDone():AbstractControl<FormControl> {
        return this.todoForm.get('isDone')!;
    }

    get id():AbstractControl<FormControl> {
        return this.todoForm.get('id')!;
    }

    get formValue(): CreateTodoRequest | UpdateTodoRequest | null {
        if (this.todoForm.invalid) {
            this.todoForm.markAllAsTouched();
            return null;
        }
        return this.todoForm.value; 
    }
}
