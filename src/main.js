import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';


import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'tailwindcss/tailwind.css';
import 'font-awesome/css/font-awesome.min.css';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

import axios from 'axios';

const app = createApp(App);

// 设置 axios 的全局默认值
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('token')}`;

// 注册 Vue Router 和 Vuex Store
app.use(router);
app.use(store); // store 应该在 app.mount 之前调用
app.component('VueDatePicker', VueDatePicker);

// 最后挂载应用
app.mount('#app');
