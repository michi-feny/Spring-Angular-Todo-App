import { AnimationCallbackEvent, Component, inject, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { FlashMessageState, FlashMessage as FlashMessageModel } from '../../../../store/fleshMessage/fleshMessage.models';
import { removeFlashMessage } from '../../../../store/fleshMessage/fleshMessage.actions';
import { getMessages } from '../../../../store/fleshMessage/fleshMessage.selectors';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-flash-message',
  imports: [NgbAlertModule, AsyncPipe],
  templateUrl: './flash-message.html',
  styleUrl: './flash-message.css'
})
export class FlashMessage implements OnInit {
    messages$!: Observable<FlashMessageModel[]>;
    private store: Store<FlashMessageState> = inject(Store<FlashMessageState>);
    
    public ngOnInit(): void {
        this.messages$ = this.store.select(getMessages);
    }

    close(id: number) {
        this.store.dispatch(removeFlashMessage({ id: id }));
    }
}
