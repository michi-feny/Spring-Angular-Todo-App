export function getCookieValue(name: string): string | null {

    const cookieString = document.cookie;

    if (!cookieString) return null;
  
    const cookies = cookieString.split(';');
    
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();
      

        if (cookie.startsWith(name + '=')) return cookie.substring(name.length + 1);
    }
  
    return null;
  }