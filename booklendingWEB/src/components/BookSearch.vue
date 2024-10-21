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
                const response = await axios.put(`${localhost}/book/${inventory_id}/BORROWED`);
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
  </style>
  