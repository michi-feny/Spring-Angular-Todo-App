export interface DecodedUser {
    exp: number
    iat: number
    id: number
    name: string
    roles: Array<string>
    sub: string
    tokenType: string
}

export default (token: string): DecodedUser | null => {
    try {
        const payloadBase64 = token.split('.')[1];

        if (!payloadBase64) return null;

        const jsonPayload = atob(payloadBase64);
        const user = JSON.parse(jsonPayload);
        return user;
    } catch (e) {
        console.error("Fehler beim Dekodieren des Tokens:", e);
        return null;
    }
};