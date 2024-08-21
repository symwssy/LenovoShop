<template>
  <div>
    <div class="header">
      <div class="centered-content">
        <img src="../../../Libra-Web/src/assets/title.svg" alt="头部图片">
      </div>
    </div>

    <div class="navbar">
      <div class="picture-content">
        <img src="../../../Libra-Web/src/assets/login1.png" alt="介绍图片"
             style="max-width: 40%; max-height: 40%; margin-left: 80px;margin-top: 10px">
      </div>
    </div>

    <div class="main">
      <h3>登录</h3>
      <h3>Login</h3>
      <div class="form-group">
        <label for="username">用户名 mobile</label>
        <input type="text" class="form-control" id="username" v-model="formData.mobile"
               placeholder="Enter your username">
      </div>
      <div class="form-group">
        <label for="password">密码 Password</label>
        <div class="password-container">
          <input :type="showPassword ? 'text' : 'password'" class="form-control" id="password"
                 v-model="formData.password" placeholder="Enter your password">
          <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
        </div>
      </div>

      <div class="text">
        <button class="btn custom-btn" type="button" @click="login">登录</button>
        <div>
          <span>第三方登录:</span>
          <a href="#"><img src="../../../Libra-Web/src/assets/weixin1.png" alt="Weixin"/></a>
          <!--    <a href="#"><img src="path/to/twitter-icon.png" alt="Twitter" /></a>-->
          <!--    <a href="#"><img src="path/to/google-icon.png" alt="Google" /></a>-->
        </div>

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

</template>

<script>
import axios from 'axios';
import {useStore} from 'vuex';

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
          // 提取用户 ID
          const userId = response.data.user_id;
          console.log("用户 ID:", userId);  // 输出用户 ID
          this.$store.commit('setUserId', userId); // 将用户 ID 存储在 Vuex 中
          this.$store.dispatch('login', true);
          localStorage.setItem('token', response.data.token);
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('userId', userId.toString()); // 将用户 ID 存储在 localStorage 中
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

/* 头部样式 */
.header {
  position: absolute;
  height: 100px;
  top: 0px;
  left: 0px;
  right: 0px;
  background-color: #F0F2F4;
}


/* 左侧样式 */
.navbar {
  position: absolute;
  left: 0px;
}

/* 右侧样式 */
.navbar-right {
  position: absolute;
  width: 50%;
  height: 100%;
  right: 0px;
  bottom: 0px;
}

/* 主区域 */
.main {
  position: absolute;
  top: 15vh;
  right: 0px;
  padding: 2vh;
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 水平居中 */
  display: flex; /* 使用flex布局来居中内容 */
  flex-direction: column; /* 子元素垂直排列 */
}

/* 居中内容样式 */
.centered-content {
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 展示图片样式 */
.picture-content {
  display: flex;
  top: 10px;
}


.form-group {
  margin-bottom: 5vh; /* 增加输入组之间的间隔 */
  margin-top: 5vh;
}

.custom-btn {
  padding: 10px 20px; /* 增加按钮的内边距 */
  margin: 0 auto; /* 水平居中*/
  width: 150px; /* 按钮占满整个容器宽度 */
  background-color: rgba(206, 206, 206, 0.89);
  border-radius: 20px;
}

.password-container {
  position: relative;
}

.form-control {
  border-radius: 20px; /* 自定义圆角半径 */
  width: 40vw;
  height: 40px;
}

.conceal {
  position: absolute;
  top: 10px; /* 根据需要调整 */
  right: 10px; /* 根据需要调整 */
  width: 20px; /* 调整图标大小 */
  height: 20px; /* 调整图标大小 */
  background-image: url(src/assets/closeeye.png);
  background-size: cover;
  cursor: pointer;
}

.conceal.yincang {
  background-image: url(src/assets/openeye.png);
}

.text a img {
  width: 24px; /* 或者您希望的尺寸 */
  height: 24px;
  margin: 0 5px; /* 添加一些空间在图标之间 */
}

.text {
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 水平居中 */
}
</style>
