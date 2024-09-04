<template>
  <div class="login-page">

    <div class="main">
      <div class="card-container">
        <h3>联想会员登录</h3>
        <div class="form-group">
          <label for="username">用户名</label>
          <input type="text" class="form-control" id="username" v-model="formData.mobile"
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
        mobile: '',
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
        const response = await axios.post('http://120.26.54.144:8000/api/user/login/', this.formData);
        if (response.status === 200) {
          console.log('登录成功');
          const userId = response.data.user_id;
          console.log("用户 ID:", userId);
          this.$store.commit('setUserId', userId);
          this.$store.dispatch('login', true);
          localStorage.setItem('token', response.data.token);
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('userId', userId.toString());
          this.$router.push('/community');
        } else {
          console.error('登录失败:', response.statusText);
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
}

/* 卡片样式 */
.card-container {
  background-color: rgba(255, 255, 255, 0.8); /* 半透明白色背景 */
  border-radius: 15px;
  left: 50%;
  margin-right: 10vh;
  padding: 30px;
  height: 70vh;
  width: 50vh;
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
