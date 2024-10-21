import {createRouter, createWebHistory} from 'vue-router'
import LoginForm from '@/components/LoginForm.vue';
import RegisterForm from '@/components/RegisterForm.vue';
import BookSearch from '@/components/BookSearch.vue';
import RecordForm from '@/components/RecordForm.vue';
import UpdateForm from '@/components/UpdateForm.vue';
import BookForm from '@/components/BookForm.vue';
import RecordList from '@/components/RecordList.vue';

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
            path: '/update',
            name: 'update',
            component: UpdateForm
        },
        {
            path: '/record',
            name: 'record',
            children: [{
                    path: 'list',
                    name: 'record-list',
                    component: RecordList
                },
                {
                    path: 'books',
                    name: 'record-books',
                    component: BookForm
                }
            ],
            component: RecordForm
        },
        {
            path: '/',
            name: 'home',
            component: BookSearch
        }
    ]
})

export default router