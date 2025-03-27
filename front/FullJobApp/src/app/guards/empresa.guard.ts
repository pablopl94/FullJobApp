import { CanActivateChildFn } from '@angular/router';

export const empresaGuard: CanActivateChildFn = (childRoute, state) => {
  return true;
};
