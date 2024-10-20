import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null
  }),
  actions: {
    // 設置 token 
    setToken(token, user) {
      this.token = token;
    },
    // 清除 token
    clearToken() {
      this.token = null;
    },
  },
  persist: true
});