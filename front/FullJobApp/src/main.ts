import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { environment } from './environments/environment';
import { enableProdMode } from '@angular/core'; // ✅ Esta es la correcta

// Solo activamos modo producción si está en entorno de producción
if (environment.production) {
  enableProdMode(); // ✅ Aquí se llama correctamente
}

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
function enableProdMode() {
  throw new Error('Function not implemented.');
}

