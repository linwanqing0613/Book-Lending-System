<template>
    <div class="registration-container">
      <h2 class="text-center">修改會員資料</h2>
      <form @submit.prevent="update">
        <div class="mb-3">
          <label class="form-label">使用者名稱</label>
          <input v-model="member_name" class="form-control" type="text" required />
        </div>
        <div class="mb-3">
          <label class="form-label">密碼</label>
          <input v-model="password" class="form-control" type="password" required />
        </div>
        <div class="mb-3">
          <label class="form-label">確認密碼</label>
          <input v-model="confirmPassword" class="form-control" type="password" required />
        </div>
        <button type="submit" class="btn btn-primary w-100">儲存</button>
      </form>
    </div>
</template>
<script setup>
  import { ref } from 'vue';
  import { localhost } from '@/config'
  import { useRouter } from 'vue-router';
  import axios from 'axios';

  const router = useRouter(); 
  const member_name = ref('');
  const password = ref('');
  const confirmPassword = ref('');
  
  const update = async () => {

    if (password.value !== confirmPassword.value) {
      alert('Passwords do not match!');
      return;
    }
    const updaterData = {
      member_name: member_name.value,
      password: password.value
    };

    try {
      const response = await axios.post(`${localhost}/member/update`, updaterData); 
      alert('更新成功'); 
      router.push('/')
    } catch (error) {
      console.error('註冊失败:', error.response.data);
      alert('註冊失敗。'); 
    }
  };
</script>
<style>

</style>
  