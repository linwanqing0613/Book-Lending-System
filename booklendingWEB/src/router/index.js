import {createRouter, createWebHistory} from 'vue-router'
import LoginForm from '@/components/LoginForm.vue';
import RegisterForm from '@/components/RegisterForm.vue';
import BookSearch from '@/components/BookSearch.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/login',
            name: 'login',
            component: LoginForm
        },
        {
            path: '/register',
            name: 'register',
            component: RegisterForm
        },
        {
            path: '/',
            name: 'home',
            component: BookSearch
        }
    ]
})

export default router