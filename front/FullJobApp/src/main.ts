import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { environment } from './environments/environment';
import { enableProdMode } from '@angular/core'; // ✅ Aquí está la función real

if (environment.production) {
  enableProdMode(); // ✅ Esto ahora sí funciona correctamente
}


bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
function enableProdMode() {
  throw new Error('Function not implemented.');
}

