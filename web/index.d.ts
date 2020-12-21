import Vue from "vue";
import {CookieAttributes} from "js-cookie";
import {accessorType} from '~/store'
import {Framework} from 'vuetify'

declare module 'vue/types/vue' {
  // this
  interface Vue {
    $accessor: typeof accessorType
    $auth: Auth
    $vuetify: Framework
  }
}

declare module "vue/types/options" {
  // option dans le composant
  interface ComponentOptions<V extends Vue> {
    auth?: boolean;
  }
}

declare module '@nuxt/types' {
  // ctx.app
  interface NuxtAppOptions {
    $accessor: typeof accessorType
  }

  // ctx
  interface Context {
    $auth: Auth
    $accessor: typeof accessorType
  }
}

// @nuxtjs/auth

interface StorageCookieOptions extends CookieAttributes {
  cookie: {
    prefix: string;
    options?: StorageCookieOptions;
  };
}

interface Storage {
  setUniversal(key: string, value: any, isJson?: boolean): string;

  getUniversal(key: string, isJson?: boolean): any;

  syncUniversal(key: string, defaultValue: any, isJson?: boolean): any;

  // Local State
  setState(key: string, val: any): string;

  getState(key: string): string;

  watchState(key: string, handler: (newValue: any) => void);

  // Cookies
  setCookie(key: string, val: any, options?: StorageCookieOptions);

  getCookie(key: string, isJson?: boolean): any;

  // Local Storage
  setLocalStorage(key: string, val: any, isJson?: boolean);

  getLocalStorage(key: string, isJson?: boolean): any;
}

interface Auth<T = any> {
  ctx: any;
  $state: any; // todo: type this
  options: AuthOptions;
  $storage: Storage;
  user: Partial<T>;
  loggedIn: boolean;

  loginWith(strategyName: string, ...args): Promise<never>;

  login(...args): Promise<never>;

  logout(): Promise<never>;

  fetchUser(): Promise<never>;

  fetchUserOnce(): Promise<never>;

  hasScope(scopeName: string): boolean;

  setToken(strategyName: string, token?: string): string;

  syncToken(strategyName: string): string;

  onError(handler: (error: Error, name: string, endpoint: any) => void);

  setUser(user?: Partial<T>);

  reset(): Promise<never>;

  redirect(name: string);
}

interface AuthOptions {
  redirect: any;
}
