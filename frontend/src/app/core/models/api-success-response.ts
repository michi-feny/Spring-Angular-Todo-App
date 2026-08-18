export interface ApiSuccessResponse<T> {
    success: boolean;
    message: string;
    data: T;
}
