import { environment } from '../../../../environments/environment';


export abstract class BaseService {


    protected readonly apiUrl: string;


    protected constructor() {

        this.apiUrl =
            environment.apiUrl.endsWith('/')
                ? environment.apiUrl.slice(0, -1)
                : environment.apiUrl;

    }


    protected buildUrl(
        resource: string
    ): string {

        return `${this.apiUrl}/${resource}`;

    }

}