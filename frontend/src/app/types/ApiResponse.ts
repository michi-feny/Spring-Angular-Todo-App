import Link from "./Link";

export interface ApiResponse<T> {
    data?: T;
    message?: string;
    links?: Link[];
}

export interface ApiResponseWithMessage<T> extends ApiResponse<T> {
    message: string;
}