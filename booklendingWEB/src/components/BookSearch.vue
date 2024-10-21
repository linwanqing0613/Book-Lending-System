<template>
    <div class="container mt-4">
        <h2 class="text-center mb-4">書籍查詢</h2>
        
        <form @submit.prevent="getBooks" class="mb-4">
            <div class="input-group">
                <input v-model="isbn" type="text" class="form-control" placeholder="输入 ISBN"/>
                <div class="input-group-append">
                    <select v-model="status" class="form-control">
                        <option value=""></option>
                        <option value="READY">可借閱</option>
                        <option value="BORROWED">借閱中</option>
                        <option value="PREPARATION">準備中</option>
                        <option value="LOST">遺失</option>
                        <option value="DAMAGED">損毀</option>
                        <option value="ABANDONED">廢棄</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </div>
        </form>

        <div v-if="connect === 200">
            <ul class="list-group">
                <li v-for="(book, index) in books" :key="book.inventory_id" class="list-group-item">
                    <h5>{{ book.name }} <small class="text-muted"> {{ book.isbn }}</small></h5>
                    <p><strong>作者:</strong> {{ book.author }}</p>
                    <p><strong>狀態:</strong> {{ book.status }}</p>
                    <p><strong>介紹:</strong> {{ book.introduction }}</p>
                    <div class="d-flex justify-content-between">
                        <button @click="updateBook(book.inventory_id, book.status)" class="btn btn-success">借用</button>
                    </div>
                </li>
            </ul>
        </div>

        <div v-else class="alert alert-warning mt-4">
            <p><strong>未找到書籍 请檢查您输入的 ISBN。</strong></p>
        </div>
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