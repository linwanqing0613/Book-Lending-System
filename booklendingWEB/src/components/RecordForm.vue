<template>
  <div class="main-container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow">
      <div class="container-fluid">
          <div class="navbar-nav">
            <RouterLink class="nav-link" to="/record/books">我的書庫</RouterLink>
            <RouterLink class="nav-link" to="/record/list">書庫紀錄</RouterLink>
          </div>
      </div>
    </nav>
    <div class="content-container">
      <RouterView></RouterView>
    </div>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router';
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column; /* 垂直排列容器的子元素 */
  border-radius: 8px; /* 圓角效果 */
  height: 100vh; /* 填滿整個視口高度 */
}

.navbar {
  background-color: #f8f9fa; /* 導航欄的淺色背景 */
  padding: 1rem; /* 導航欄的內邊距 */
}

.nav-link {
  color: #007bff; /* 自訂連結顏色 */
  margin-right: 20px; /* 連結之間的間距 */
}

.content-container {
  flex: 1; /* 填滿剩餘空間 */
  padding: 20px; /* 內容區域的內邊距 */
  background-color: #ffffff; /* 內容區域的白色背景 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 微妙的陰影效果 */
  border-radius: 8px; /* 圓角效果 */
  overflow-y: auto; /* 內容溢出時允許滾動 */
  margin-top: 10px; 
}
</style>
