<template>
    <div class="container mt-4 book-search-container">
      <h2 class="text-center mb-4 heading">書籍查詢</h2>
      <form @submit.prevent="getBooks" class="mb-4 search-form">
        <div class="input-group search-bar">
          <input v-model="isbn" type="text" class="form-control search-input" placeholder="輸入 ISBN"/>
          <select v-model="status" class="form-control search-select">
            <option value=""></option>
            <option value="READY">可借閱</option>
            <option value="BORROWED">借閱中</option>
            <option value="PREPARATION">準備中</option>
            <option value="LOST">遺失</option>
            <option value="DAMAGED">損毀</option>
            <option value="ABANDONED">廢棄</option>
          </select>
          <button type="submit" class="btn btn-primary search-btn">搜索</button>
        </div>
      </form>
    </div>
    <div v-if="books && books.length">
      <!-- 標頭區域 -->
      <div class="list-header">
        <span class="header-item">書名</span>
        <span class="header-item">作者</span>
        <span class="header-button">狀態</span>
      </div>
  
      <!-- 書籍列表 -->
      <ul class="list-group">
        <li
          v-for="book in books"
          :key="book.inventory_id"
          class="list-group-item"
        >
          <span class="book-title">{{ book.name }}</span>
          <span class="book-author">{{ book.author }}</span>
          <span class="book-button">
            <button class="badge-status" :class="statusClass(book.status)" @click="updateBook(book.inventory_id, book.status)">
              {{ book.status }}
            </button>
          </span>
        </li>
      </ul>
    </div>
    <div v-else class="alert alert-info text-center mt-4">
      未找到書籍，請重新搜尋。
    </div>
</template>
<script setup>
import { ref } from 'vue';
import { localhost } from '@/config'
import { useAuthStore } from '@/store/auth';
import { useRouter } from 'vue-router';
import axios from 'axios';

const authStore = useAuthStore();
const router = useRouter(); 
let books = ref([])
let isbn = ref('')
let status = ref('')
let connect = ref()


// 依據書籍狀態設定不同的 badge 類別
const statusClasses = {
  READY: "badge-success",
  BORROWED: "badge-warning",
  PREPARATION: "badge-info",
  LOST: "badge-danger",
  DAMAGED: "badge-danger",
  ABANDONED: "badge-secondary",
};

const statusClass = (status) => {
  return statusClasses[status] || "badge-secondary"; // 若無符合的狀態，則回傳預設值
}

async function getBooks(){
    try {
        const response = await axios.get(`${localhost}/books?isbn=${isbn.value}&status=${status.value}`);
        books.value = Array.isArray(response.data) ? response.data : [response.data];
        connect.value = response.status;
    } catch (error) {
        alert("未找到書籍 请檢查您输入的 ISBN。");
    }
}
async function updateBook(inventory_id, status){
    try {
        if (authStore.token) {
            if(status === 'READY'){
                const response = await axios.put(`${localhost}/book/${inventory_id}/BORROWED`,
                null, {
                headers: {
                    'Authorization':  `Bearer ${authStore.token}`
                }
            });
                books.value = Array.isArray(response.data) ? response.data : [response.data];
            }else{
                alert("該書籍無法借閱");
            }
        }else{
            alert("請先登入後再進行借閱");
            router.push('/login')
        }
    } catch (error) {
        
    }
}
</script>
  <style>
  .search-form {
    display: flex;
    justify-content: center;
  }
  
  .search-bar {
    width: 100%; /* 使搜索欄佔滿寬度 */
    max-width: 600px; /* 設置搜索欄最大寬度為 600px，防止過寬 */
    display: flex; /* 使用 flex 排版，將輸入框、選擇框和按鈕排列在一行 */
  }
  
  .search-input {
    flex: 0 0 70% !important; /* 基礎寬度佔 50%，不擴展或收縮 */
    border-top-left-radius: 20px; /* 左上角邊框圓角 */
    border-bottom-left-radius: 20px; /* 左下角邊框圓角 */
    border-right: 0; /* 移除右邊的邊框，使與選擇框連接 */
    padding: 10px; /* 內邊距 10px，增加填充空間 */
    font-size: 16px; /* 設置字體大小 */
    box-shadow: none; /* 移除框影 */
  }
  
  .search-select {
    flex: 0 0 15% !important; /* 基礎寬度佔 30%，不擴展或收縮 */
    border: 1px solid #ced4da; /* 增加一個淺灰色邊框 */
    padding: 10px; /* 內邊距 10px */
    font-size: 16px; /* 設置字體大小 */
    border-radius: 0; /* 移除圓角，保持選擇框直角 */
    background-color: white; /* 背景設置為白色 */
  }
  
  .search-btn {
    flex: 0 0 15% !important; /* 基礎寬度佔 20%，不擴展或收縮 */
    border-top-right-radius: 20px; /* 右上角邊框圓角 */
    border-bottom-right-radius: 20px; /* 右下角邊框圓角 */
    padding: 10px 20px; /* 上下內邊距 10px，左右內邊距 20px */
    font-size: 16px; /* 設置字體大小 */
    background-color: #007bff; /* 按鈕背景色設置為藍色 */
    border: none; /* 移除邊框 */
    box-shadow: none; /* 移除按鈕的框影 */
  }
  .search-btn:hover {
    background-color: #0056b3;
  }
  
  .input-group input:focus, 
  .input-group select:focus, 
  .input-group button:focus {
    box-shadow: none;
    outline: none;
  }
  
  .book-search-container {
    background-color: #f8f9fa;
    padding: 20px;
    border-radius: 8px;
  }
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
  
  .book-author {
    flex: 2; /* 作者佔少量空間 */
    text-align: left; /* 居中 */
    color: #555; /* 深灰色文字 */
  }
  .book-button {
    flex: 1; /* 作者佔少量空間 */
    text-align: center; /* 居中 */
  }
  
  .badge-status {
    background-color: #555; /* 藍色背景 */
    font-size: 0.9rem; /* 字體大小 */
    padding: 5px 10px; /* 內邊距 */
    border-radius: 12px; /* 圓角 */
    border: none; /* 移除邊框 */
  }
  /* 可借閱狀態的徽章樣式 */
.badge-success {
  background-color: #28a745; /* 綠色背景 */
}

/* 借閱中狀態的徽章樣式 */
.badge-warning {
  background-color: #ffc107; /* 黃色背景 */
}

/* 準備中狀態的徽章樣式 */
.badge-info {
  background-color: #17a2b8; /* 藍色背景 */
}

/* 遺失和損毀狀態的徽章樣式 */
.badge-danger {
  background-color: #dc3545; /* 紅色背景 */
}

/* 廢棄狀態的徽章樣式 */
.badge-secondary {
  background-color: #6c757d; /* 灰色背景 */
}
  </style>
  