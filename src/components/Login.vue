<template>
  <div class="login-page">
    <div class="main">
      <div class="card-container">
        <h3>联想会员登录</h3>
        <div class="form-group">
          <label for="username">用户名</label>
          <input type="text" class="form-control" id="username" v-model="formData.username"
                 placeholder="Enter your username">
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <div class="password-container">
            <input :type="showPassword ? 'text' : 'password'" class="form-control" id="password"
                   v-model="formData.password" placeholder="Enter your password">
            <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
          </div>
        </div>

        <div class="text">
          <button class="btn custom-btn" type="button" @click="login">登录</button>
        </div>

        <div class="text">
          <div>
            <router-link to="/register">注册</router-link>
            <a> | </a>
            <a href="找回密码的URL">找回密码</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      formData: {
        username: '',
        password: '',
      },
      showPassword: false,
    };
  },
  methods: {
    login() {
      this.performLogin();
    },

    async performLogin() {
      try {
        // 注意：这里使用了 await 来等待 axios.post 请求完成
        const response = await axios.post('/api/user/login', {
          username: this.formData.username,
          password: this.formData.password
        });

        // 检查请求是否成功，以及响应数据是否有效
        console.log('完整的响应数据:', response);

        if (response.status === 200 && response.data) {
          const responseData = response.data;

          if (responseData.code === 0) {  // 登录成功
            console.log('登录成功');
            const token = responseData.data;  // JWT Token

            if (token) {
              console.log("Token:", token);
              this.$store.commit('setUserToken', token);  // 假设你在 Vuex 中有一个设置 token 的 mutation
              this.$store.dispatch('login', true);
              localStorage.setItem('token', token);
              localStorage.setItem('isLoggedIn', 'true');
              this.$router.push('/index');  // 登录成功后跳转
            } else {
              console.error('未返回有效的 token');
            }
          } else {
            console.error('登录失败：', responseData.message);  // 处理错误消息
          }
        } else {
          console.error('登录失败：', response.statusText);
        }
      } catch (error) {
        console.error('登录失败：', error);
      }
    },

    togglePassword() {
      this.showPassword = !this.showPassword;
    },
  },
};
</script>



<style scoped>
/* 设置背景图片 */
.login-page {
  background-image: url('../assets/Background_Login.jpg');
  background-size: cover;
  background-position: center;
  height: 100vh;
  display: flex;
  justify-content: flex-end; /* 使卡片位于右侧 */
  align-items: center
}

.main h3 {
  margin: 2vh 0 2vh 0;
  font-weight: bold;
}

/* 卡片样式 */
.card-container {
  background-color: rgba(255, 255, 255, 0.8); /* 半透明白色背景 */
  border-radius: 15px;
  left: 50%;
  margin-right: 10vw;
  padding: 30px;
  height: 50vh;
  width: 30vw;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}


/* 输入框样式 */
.form-group {
  margin-bottom: 20px;
}

.custom-btn {
  padding: 10px 20px;
  width: 100%;
  background-color: #f22d18;
  border-radius: 5px;
  color: white;
  transition: transform 0.3s ease, opacity 0.3s ease, box-shadow 0.2s ease; /* 添加平滑过渡 */
}

.custom-btn:hover {
  background-color: #f22d18;
  color: white;
  opacity: 0.9;
  transform: scale(1.05); /* 鼠标悬停时放大 */
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2); /* 悬停时阴影效果 */
}

.custom-btn:active {
    background-color: #f22d18;
  color: white;
  transform: scale(0.95); /* 按下时缩小 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 按下时减少阴影 */
}


.password-container {
  position: relative;
}

.form-control {
  border-radius: 5px;
  width: 100%;
  height: 40px;
}

.text a img {
  width: 24px;
  height: 24px;
  margin: 0 5px;
}

.text {
  justify-content: center;
  align-items: center;
  text-align: center;
}
</style>
