import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { environment } from './environments/environment';
import { enableProdMode } from '@angular/core'; // ✅ esta es la buena

if (environment.production) {
  enableProdMode(); 
}

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
  throw new Error('Function not implemented.');


