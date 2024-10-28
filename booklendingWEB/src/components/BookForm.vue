<template>
<div v-if="books && books.length">
            <!-- 標頭區域 -->
    <div class="list-header">
    <span class="header-item">ISBN</span>
    <span class="header-item">書籍</span>
    <span class="header-item">作者</span>
    <span class="header-item">借用時間</span>
    <span class="header-button">狀態</span>
    </div>
    <!-- 書籍列表 -->
    <ul class="list-group">
    <li
        v-for="book in books"
        :key="book.inventory_id"
        class="list-group-item"
    >
    <span class="book-text">{{ book.isbn }}</span>
    <span class="book-title">{{ book.name }}</span>
    <span class="book-author">{{ book.author }}</span>
    <span class="book-text">{{ book.borrowing_time }}</span>
    <span class="book-button">
    <button @click="updateBook(book.inventory_id)">
        歸還
    </button>
    </span>
    </li>
    </ul>
</div>
<div v-else class="alert alert-info text-center mt-4">
  未有書籍。
</div>
</template>
<script setup>
import { ref, onBeforeMount } from 'vue';
import { localhost } from '@/config'
import { useAuthStore } from '@/store/auth';
import axios from 'axios';

const books = ref([]);
const authStore = useAuthStore();

async function getBorrowingRecord(){
    try {
      const response = await axios.get(
          `${localhost}/record/books`,{
          headers:{
              'Authorization':  `Bearer ${authStore.token}`
          }
      });
      books.value = Array.isArray(response.data) ? response.data : [response.data];
    } catch (error) {
      books.value = [];
    }
}

async function updateBook(inventory_id){
    try {
        if (authStore.token) {
          const response = await axios.put(`${localhost}/book/${inventory_id}/PREPARATION`,
          null, {
          headers: {
              'Authorization':  `Bearer ${authStore.token}`
          }
          });
          getBorrowingRecord();
        }else{
            alert("請先登入後再進行借閱");
            router.push('/login')
        }
    } catch (error) {
        
    }
}
// 在組件掛載之前執行
onBeforeMount(() => {
    getBorrowingRecord();
});
</script>
<style scoped>
.list-header {
    display: flex;
    justify-content: space-between;
    padding: 10px;
    background-color: #f1f1f1;
    font-weight: bold;
    border-bottom: 2px solid #ddd;
  }
  .list-group {
    list-style-type: none; /* 移除列表點 */
    padding: 0; /* 移除內邊距 */
    margin: 0; /* 移除外邊距 */
  }
  .list-group-item {
    display: flex; /* 使用 Flexbox 對齊內容 */
    justify-content: space-between; /* 每個項目間隔均勻 */
    align-items: center; /* 垂直居中 */
    padding: 15px 10px; /* 內邊距 */
    border-bottom: 1px solid #ddd; /* 下邊框 */
    transition: background-color 0.3s; /* 平滑過渡效果 */
  }
  .header-item {
    flex: 2;
    text-align: center;
  }
  .header-button {
    flex: 1;
    text-align: center;
  }
  .book-title {
    flex: 2; /* 書名佔更多空間 */
    color: #007bff; /* 藍色文字 */
  }
  .book-text {
    flex: 2; /* 作者佔少量空間 */
    text-align: center; /* 居中 */
    color: #555; /* 深灰色文字 */
  }
  .book-author {
    flex: 2; /* 作者佔少量空間 */
    text-align: left; /* 居中 */
    color: #555; /* 深灰色文字 */
  }
  
  .book-button {
    flex: 1; /* 作者佔少量空間 */
    text-align: center; /* 居中 */
  }
  
</style>