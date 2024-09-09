<template>
  <div class="login-page">
    <div class="main">
      <div class="card-container">
        <h3>联想会员注册</h3>

        <!-- 用户名输入框 -->
        <div class="form-group">
          <label for="username">用户名</label>
          <input type="text" class="form-control" id="username" v-model="formData.username" placeholder="Enter your username">
        </div>

        <!-- 邮箱输入框 -->
        <div class="form-group">
          <label for="email">邮箱</label>
          <input type="text" class="form-control" id="email" v-model="formData.email" placeholder="Enter your email">
        </div>

        <!-- 验证码输入框和按钮 -->
        <div class="form-group code-group">
          <label for="code">验证码</label>
          <div class="d-flex">
            <input type="text" class="form-control code-input" id="code" v-model="formData.code" placeholder="Enter your code">
            <button type="button" class="btn custom-btn code-btn" @click="sendEmailCode">获取验证码</button>
          </div>
        </div>

        <!-- 密码输入框 -->
        <div class="form-group">
          <label for="password">密码</label>
          <div class="password-container">
            <input :type="showPassword ? 'text' : 'password'" class="form-control" id="password"
                   v-model="formData.password" placeholder="Enter your password">
            <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
          </div>
        </div>

        <!-- 确认密码输入框 -->
        <div class="form-group">
          <label for="confirm-password">确认密码</label>
          <div class="password-container">
            <input :type="showPassword ? 'text' : 'password'" class="form-control" id="confirm-password"
                   v-model="formData.confirmPassword" placeholder="Confirm your password">
            <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
          </div>
        </div>

        <!-- 注册按钮 -->
        <div class="text">
          <button class="btn custom-btn" type="button" @click="register">注册</button>
        </div>

        <!-- 已有账号链接 -->
        <div class="text">
          <router-link to="/login">已有帐号? 登录</router-link>
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
        email: '',
        code: '',
        password: '',
        confirmPassword: ''
      },
      showPassword: false,
      errorMessage: ''
    };
  },
  methods: {
    sendEmailCode() {
      const email_regex = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
      if (!email_regex.test(this.formData.email)) {
        this.errorMessage = "邮箱格式不正确";
        return;
      }
      axios.get("/api/v1/users/send?email=" + this.formData.email)
          .then((response) => {
            if (response.data.code == 1000) {
              this.$message.success("发送验证码成功");
              this.startEmailCodeTimer();
            } else {
              this.errorMessage = "发送验证码失败";
            }
          })
          .catch((error) => {
            console.error("请求失败：", error);
            this.errorMessage = "请求发送验证码失败";
          });
    },
    startEmailCodeTimer() {
      const btn = this.$el.querySelector('button.btn-outline-secondary');
      let count = 120;
      btn.disabled = true;
      const timer = setInterval(() => {
        count--;
        btn.textContent = `${count}秒后重新发送`;
        if (count === 0) {
          clearInterval(timer);
          btn.textContent = '发送验证码';
          btn.disabled = false;
        }
      }, 1000);
    },
    register() {
      // 注册逻辑
    },
    togglePassword() {
      this.showPassword = !this.showPassword;
    }
  }
};
</script>


<style scoped>
.login-page {
  background-image: url('../assets/Background_Login.jpg');
  background-size: cover;
  background-position: center;
  height: 100vh;
  display: flex;
  justify-content: flex-end;
  align-items: center;
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
  height: 80vh;
  width: 30vw;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}

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

.d-flex {
  display: flex;
  justify-content: space-between;
}

.code-input {
  width: 66.6%; /* 验证码输入框占 2/3 宽度 */
  height: 40px; /* 保持和其他输入框一致 */
}

.code-btn {
  width: 33.3%; /* 验证码按钮占 1/3 宽度 */
  height: 40px; /* 与输入框高度一致 */
}

.text {
  justify-content: center;
  align-items: center;
  text-align: center;
}
</style>

