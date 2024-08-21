import { createApp } from 'vue';
import router from './router'; // 导入路由配置
import store from './store';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'tailwindcss/tailwind.css';
import 'font-awesome/css/font-awesome.min.css';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

import App from './APP.vue'; // 主组件
// 在main.js或相似的入口文件中
import axios from 'axios';

const app = createApp(App);
// 设置axios的全局默认值
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('token')}`;
app.use(router);
app.component('VueDatePicker', VueDatePicker);
app.mount('#app');
app.use(store);
