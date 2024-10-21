import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null,
    member_name: null
  }),
  actions: {
    // 設置 token 
    setToken(token, member_name) {
      this.token = token;
      this.member_name = member_name;
    },
    // 清除 token
    clearToken() {
      this.token = null;
      this.member_name = null;
    },
  },
  persist: true
});