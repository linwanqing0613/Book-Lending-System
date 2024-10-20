<template>
  <div>
    <h2>會員登入</h2>
    <form @submit.prevent="login">
      <div  class="mb-3">
        <label class="form-label">電話號碼</label>
        <input v-model="phone_number" class="form-control" type="text" required />
      </div>
      <div class="mb-3">
        <label class="form-label">密碼</label>
        <input v-model="password" class="form-control" type="password" required />
      </div>
      <button type="submit" class="btn btn-primary w-100">登入</button>
      <p class="text-center mt-2">
        註冊帳號？ <a href="#" @click="toRegister">註冊</a>
      </p>
    </form>
  </div>
  </template>
  
  <script setup name="login">
  import { ref } from 'vue';
  import { useAuthStore } from '@/store/auth';
  import { localhost } from '@/config'
  import { useRouter } from 'vue-router';
  import axios from 'axios';

  const authStore = useAuthStore();
  const router = useRouter(); 
  const phone_number = ref('');
  const password = ref('');
  const loginData = {
    phone_number: phone_number.value,
    password: password.value
  };
  
  if (authStore.token) {
    router.push('/')
  }
  const login = async () => {
    try {
      const response = await axios.post(`${localhost}/member/login`,loginData);
      authStore.setToken(response.data.token);
      router.push('/')
    } catch (error) {
      alert('登入失敗');
    }
  };
  const toRegister = () =>{
    router.push('/register')
  }
  </script>
  