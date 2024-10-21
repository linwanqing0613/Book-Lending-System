<template>
  <div class="registration-container">
    <h2 class="text-center">會員註冊</h2>
    <form @submit.prevent="register">
      <div class="mb-3">
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
<style>
.registration-container {
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