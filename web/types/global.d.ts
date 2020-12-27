type Nullable<T> = T | undefined | null

declare module '*.vue' {
  import Vue from 'vue';
  export default Vue;
}
