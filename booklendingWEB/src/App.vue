<template>
<div class="app-container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <router-link class="navbar-brand" to="/">圖書管理系統</router-link>
            <ul class="navbar-nav">
                <li class="nav-item dropdown" >
                    <a class="nav-link dropdown-toggle no-arrow" id="navbarScrollingDropdown" 
                       role="button" data-bs-toggle="dropdown" aria-expanded="false">
                       <img src="@/assets/user-icon-2.jpg" alt="User Icon" class="user-icon">
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">
                        <li v-if="isAuthenticated"><RouterLink class="dropdown-item" to="/login">登入</RouterLink></li>
                        <li v-if="isAuthenticated"><RouterLink class="dropdown-item" to="/register">註冊</RouterLink></li>
                        <li v-if="!isAuthenticated"><RouterLink class="dropdown-item dropdown-item-red" to="/" @click="logout">登出</RouterLink></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <div class="content-display">
        <!-- 內容展示區 -->
        <RouterView></RouterView> 
    </div>  
</div>   
</template>
<script setup>
import { RouterView, RouterLink, useRouter} from 'vue-router'
import { ref } from 'vue';
import { computed } from 'vue';
import { useAuthStore } from '@/store/auth';

const showRegister = ref(false);
const showMenu = ref(false);
const authStore = useAuthStore();
const router = useRouter();

const isAuthenticated = computed(() => !authStore.token);
const logout = () =>  authStore.clearToken();

</script>
<style>
.app-container {
    display: flex;
    flex-direction: column;
    height: 100vh; /* 讓容器高度為滿版 */
    width: 80%; /* 增加容器的寬度為80% */
    margin: 0 auto; /* 將容器居中 */
    padding: 20px;
}

.navbar {
    flex-shrink: 0; /* 防止導覽列收縮 */
}
.no-arrow::after {
    display: none; /* 隱藏箭頭圖標 */
}
.content-display {
    width: 100%; /* 使內容展示區寬度為100% */
    flex-grow: 1; /* 內容展示區佔據剩餘空間 */
    padding: 10px; /* 增加內邊距 */
    background-color: #f8f9fa; /* 可選的背景顏色 */
}
  
.user-icon {
    width: 70px;
    height: 70px;
    border-radius: 50%;
}
.dropdown-menu {
    position: absolute !important; /* 絕對定位 */
    background-color: #ffffff; /* 背景顏色 */
    min-width: 150px; /* 設定最小寬度 */
    z-index: 1; /* 確保下拉內容在上方 */
    left: -50px  !important; /* 左對齊 */
    display: none; /* 初始隱藏 */
}
.dropdown-item-red {
    color: red; /* 將字體顏色設置為紅色 */
}
.dropdown-menu.show {
    display: block; /* 顯示下拉內容 */
}
</style>
