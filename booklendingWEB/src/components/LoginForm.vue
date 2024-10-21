<template>
  <div class="login-container">
    <h2 class="text-center">會員登入</h2>
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
<style>
.login-container {
  max-width: 400px; /* 最大寬度 */
  margin: auto; /* 水平居中 */
  padding: 20px; /* 內邊距 */
  border: 1px solid #ddd; /* 邊框 */
  border-radius: 8px; /* 邊角圓弧 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 陰影效果 */
  background-color: #fff; /* 背景顏色 */
}

h2 {
  margin-bottom: 20px; /* 標題下方的邊距 */
  color: #333; /* 標題顏色 */
}

.form-label {
  font-weight: bold; /* 標籤字體加粗 */
}

.form-control {
  border-radius: 4px; /* 輸入框邊角圓弧 */
  border: 1px solid #ccc; /* 輸入框邊框 */
  transition: border-color 0.3s; /* 邊框顏色過渡效果 */
}

.form-control:focus {
  border-color: #007bff; /* 聚焦時的邊框顏色 */
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* 聚焦時的陰影效果 */
}

.btn-primary {
  background-color: #007bff; /* 按鈕背景顏色 */
  border-color: #007bff; /* 按鈕邊框顏色 */
}

.btn-primary:hover {
  background-color: #0056b3; /* 按鈕懸停時的顏色 */
  border-color: #0056b3; /* 按鈕懸停時的邊框顏色 */
}

.text-center {
  color: #555; /* 文字顏色 */
}
</style>