<script setup>
import { RouterView, RouterLink, useRouter} from 'vue-router'
import { ref } from 'vue';
import { computed } from 'vue';
import { useAuthStore } from '@/store/auth';

const showRegister = ref(false);
const authStore = useAuthStore();
const router = useRouter();
const isAuthenticated = computed(() => !!authStore.token);

const logout = () => {
  authStore.clearToken(); 
  router.push('/login');
};
</script>

<template>
    <div class="app-container">
        <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <div class="container-fluid">
            <RouterLink class="navbar-brand" to="/">圖書管理系統</RouterLink>
            <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
            >
            <span class="navbar-toggler-icon"></span>
            </button>
    
            <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item" v-if="isAuthenticated">
                    <RouterLink class="btn btn-success me-2" to="/my-book">我的書庫</RouterLink>
                </li>
                <li class="nav-item" v-if="isAuthenticated">
                    <RouterLink class="btn btn-outline-warning me-2" to="/change-password">修改資料</RouterLink>
                </li>
                <li class="nav-item" v-if="isAuthenticated">
                    <button class="btn btn-outline-danger me-2" @click="logout">登出</button>
                </li>
                <li class="nav-item" v-else>
                <RouterLink class="btn btn-outline-primary me-2" to="/login">登錄</RouterLink>
                </li>
            </ul>
            </div>
        </div>
        </nav>
        <div class="content-display">
            <RouterView></RouterView> 
        </div>
    </div>
</template>
<style>
.navbar {
  margin-bottom: 0px;
}
.content-display {
    margin-top: 100px; 
    background-color: #f8f9fa;
    flex: 1; 
    overflow-y: auto; 
}
.app-container {
    display: flex;
    flex-direction: column;
    height: 100vh; 
}
</style>
