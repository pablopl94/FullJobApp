import { CanActivateChildFn } from '@angular/router';

export const candidatoGuard: CanActivateChildFn = (childRoute, state) => {
  return true;
};
