<template>
  <div>
    <h2>會員註冊</h2>
    <form @submit.prevent="register">
      <div class="mb-3 content-display">
        <label class="form-label">使用者名稱</label>
        <input v-model="member_name" class="form-control" type="text" required />
      </div>
      <div class="mb-3">
        <label class="form-label">電話號碼</label>
        <input v-model="phone_number" class="form-control" type="text" required />
      </div>
      <div class="mb-3">
        <label class="form-label">密碼</label>
        <input v-model="password" class="form-control" type="password" required />
      </div>
      <div class="mb-3">
        <label class="form-label">確認密碼</label>
        <input v-model="confirmPassword" class="form-control" type="password" required />
      </div>
      <button type="submit" class="btn btn-primary w-100">註冊</button>
      <p class="text-center mt-2">
        已有會員？ <a href="#" @click="tologin">登入</a>
      </p>
    </form>
  </div>
  </template>
  
  <script setup name="register">
  import { ref , defineEmits } from 'vue';
  import { localhost } from '@/config'
  import { useRouter } from 'vue-router';
  import axios from 'axios';

  const emit = defineEmits();
  const router = useRouter(); 
  const member_name = ref('');
  const phone_number = ref('');
  const password = ref('');
  const confirmPassword = ref('');
  
  const register = async () => {
    if (password.value !== confirmPassword.value) {
      alert('Passwords do not match!');
      return;
    }
    const registerData = {
      member_name: member_name.value,
      phone_number: phone_number.value,
      password: password.value
    };

    try {
      const response = await axios.post(`${localhost}/member/register`, registerData); 
      alert('註冊成功'); 
      router.push('/login')
    } catch (error) {
      console.error('註冊失败:', error.response.data);
      alert('註冊失敗。'); 
    }
  };
  const tologin = () =>{
    router.push('/login')
  }
  </script>
