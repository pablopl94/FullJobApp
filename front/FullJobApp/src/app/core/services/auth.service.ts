import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
// import { HttpClient } from '@angular/common/http'; // DESCOMENTAR: LOGIN REAL.

@Injectable({ providedIn: 'root' })
export class AuthService {

  private currentUserSubject = new BehaviorSubject<any>(null);
  private baseUrl = 'http://localhost:9007/auth'; // CAMBIAR SI HAY MODIFICACIONES EN LA URL
  // private http = inject(HttpClient); // DESCOMENTAR: LOGIN REAL.

  constructor(private router: Router) {}

  // LOGIN PROVISIONAL:
  login(email: string, password: string): boolean {
    if (email === 'admin@demo.com') {
      this.setSession({ email, role: 'ADMON' });
      return true;
    } else if (email === 'empresa@demo.com') {
      this.setSession({ email, role: 'EMPRESA' });
      return true;
    } else if (email === 'candidato@demo.com') {
      this.setSession({ email, role: 'CANDIDATO' });
      return true;
    }

    return false;
  }

  /*
  // LOGIN REAL:
  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post(`${this.baseUrl}/login`, body);
  }
  */

  private setSession(user: any) {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  logout() {
    localStorage.removeItem('user');
    this.currentUserSubject.next(null);
    this.router.navigate(['/auth/login']);
  }

  getCurrentUser() {
    const local = localStorage.getItem('user');
    return local ? JSON.parse(local) : null;
  }

  getRole(): string | null {
    const user = this.getCurrentUser();
    return user?.role || null;
  }

  isLoggedIn(): boolean {
    return !!this.getCurrentUser();
  }
}
