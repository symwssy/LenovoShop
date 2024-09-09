<template>
  <div>
    <div class="header">
      <div class="centered-content">
        <img src="../../../Libra-Web/src/assets/title.svg" alt="头部图片">
      </div>
    </div>
    <div class="main">
      <h3 style="margin-left: 45%;">注册</h3>
      <h3 style="margin-left: 44%;">Register</h3>

      <div class="input-group mb-3">
        <span class="input-group-text" id="basic-addon1">邮箱</span>
        <input type="text" class="form-control" v-model="formData.email" placeholder="Enter your email"
               aria-label="Email"
               aria-describedby="basic-addon1">
      </div>
      <button type="button" class="btn btn-outline-secondary" @click="sendEmailCode">发送验证码</button>

      <div class="input-group mb-3">
        <span class="input-group-text" id="basic-addon2">验证码</span>
        <input type="text" class="form-control" v-model="formData.code" placeholder="Enter your code"
               aria-label="Code"
               aria-describedby="basic-addon2">
      </div>

      <div class="form-group">
        <div class="password-container">
          <input :type="showPassword ? 'text' : 'password'" class="form-control" id="password" v-model="formData.password" placeholder="Enter your password">
          <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="password-container">
          <input :type="showPassword ? 'text' : 'password'" class="form-control" id="confirm-password" v-model="formData.confirmPassword" placeholder="Confirm your password">
          <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
        </div>
      </div>

      <div v-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div class="d-grid gap-2 col-6 mx-auto">
        <button class="btn custom-btn" type="button" @click="register">注册</button>
        <div style="margin-left: 130px;">
          <span>第三方登录:</span>
          <a href="#"><img src="../../../Libra-Web/src/assets/weixin1.png" alt="Weixin"/></a>
        </div>
      </div>

      <div class="d-grid gap-2 col-6 mx-auto">
        <div style="margin-left:170px;">
          <a href="/Login">已有帐号</a>
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
  top: 100px;
  left: 0px; /* 设置左边距为50% */
  bottom: 0px;
  right: 0px;
  padding: 10px;
  overflow-y: auto;
  background-color: #F0F2F4;
}

/* 主区域 */
.main {
  position: absolute;
  top: 200px;
  left: 50%; /* 设置左边距为50% */
  bottom: 0px;
  right: 0px;
  padding: 10px;
  overflow-y: auto;
  background-color: #F0F2F4;
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
  left: 50%;
}

.login-form {
  width: 60%; /* 增加表单宽度 */
  margin: 0 auto; /* 水平居中表单 */
  padding: 20px; /* 增加内边距 */
}

.form-group {
  margin-bottom: 40px; /* 增加输入组之间的间隔 */
  margin-top: 50px;
}

.form-floating {
  width: 100%; /* 输入框占满整个容器宽度 */
  padding: 10px; /* 增加输入框的内边距 */
  margin-left: 0%;
}

.custom-btn {
  padding: 10px 20px; /* 增加按钮的内边距 */
  margin: 0 auto; /* 水平居中*/
  width: 150px; /* 按钮占满整个容器宽度 */
  background-color: rgba(206, 206, 206, 0.89);
  margin-bottom: 10px;
  margin-top: 20px;
  border-radius: 20px;
}

.password-container {
  position: relative;
}

.form-control {
  border-radius: 20px; /* 自定义圆角半径 */
  height: 40px;
  padding-right: 40px; /* 增加右侧内边距以适应图标 */
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

.d-grid a img {
  width: 24px; /* 或者您希望的尺寸 */
  height: 24px;
  margin: 0 5px; /* 添加一些空间在图标之间 */
}


</style>
