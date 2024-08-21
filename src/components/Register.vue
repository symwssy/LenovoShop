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
            <h3 style="margin-left: 45%;">注册</h3>
            <h3 style="margin-left: 44%;">Register</h3>

            <!--      &lt;!&ndash; 添加用户名输入框 &ndash;&gt;-->
            <!--      <div class="input-group mb-3">-->
            <!--        <input type="text" class="form-control" v-model="formData.username" placeholder="用户名">-->
            <!--      </div>-->

            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">+86</span>
                <input type="text" class="form-control" v-model="formData.mobile" placeholder="Tel"
                       aria-label="Telephone"
                       aria-describedby="basic-addon1">
            </div>
            <div class="input-group mb-3">
                <input type="text" class="form-control" id="captcha" v-model="captcha" placeholder="Enter captcha">
                <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="getCaptcha">
                    获取验证码
                </button>
            </div>
            <div class="form-group">
                <!--        <label for="password">密码 Password</label>-->
                <div class="password-container">
                    <input :type="showPassword ? 'text' : 'password'" class="form-control" id="password"
                           v-model="formData.password" placeholder="Enter your password">
                    <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
                </div>
            </div>
            <div class="form-group">
                <!--        <label for="password">密码 Password</label>-->
                <div class="password-container">
                    <input :type="showPassword ? 'text' : 'password'" class="form-control" id="password"
                           v-model="formData.password" placeholder="Confirm your password">
                    <div class="conceal" :class="{ yincang: showPassword }" @click="togglePassword"></div>
                </div>
            </div>


<!--            <div class="input-group mb-3">-->
<!--                <input type="text" class="form-control" v-model="formData.captcha_id" placeholder="验证 ID">-->
<!--            </div>-->


            <div class="identify">
                <img :src="captchaImage" alt="验证码" v-if="captchaImage">
            </div>

            <div v-if="errorMessage" class="alert alert-danger">
                {{ errorMessage }}
            </div>

            <div class="d-grid gap-2 col-6 mx-auto">
                <button class="btn custom-btn" type="button" @click="register">注册</button>
                <div style="margin-left: 130px;">
                    <span>第三方登录:</span>
                    <a href="#"><img src="../../../Libra-Web/src/assets/weixin1.png" alt="Weixin"/></a>
                    <!--    <a href="#"><img src="path/to/twitter-icon.png" alt="Twitter" /></a>-->
                    <!--    <a href="#"><img src="path/to/google-icon.png" alt="Google" /></a>-->
                </div>
            </div>

            <div class="d-grid gap-2 col-6 mx-auto">
                <div style="margin-left:170px;">
                    <a href="/Login">已有帐号</a>
                    <!--    <a href="找回密码的URL">找回密码</a>-->
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
                mobile: '',
              captcha_id: ''
            },
            showPassword: false,
            captcha: '', // 用于保存验证码
            captchaImage: '', // 用于存储验证码图片
            errorMessage: '', // 用于保存错误信息

        };
    },
    created() {
        this.getCaptcha(); // 在组件创建时获取验证码
    },
    methods: {
        register() {
            // 在注册请求中包含验证码数据
            const requestData = {
                ...this.formData,
                captcha_id: this.captcha_id,
                captcha: this.captcha
            };

            axios.post('http://120.26.54.144:8000/api/user/register/', requestData)
                .then(response => {
                    console.log(response.data);
                    // 处理成功的响应
                })
                .catch(error => {
                    console.error(error);
                    // 处理错误
                });
        },

        getCaptcha() {
            axios.get('http://120.26.54.144:8000/api/user/captcha/')
                .then(response => {
                    this.captcha_id = response.data.id;
                    this.captchaImage = response.data.image_base; // 将验证码图片的 Base64 编码保存
                    console.log("验证码 ID:", this.captcha_id);
                })
                .catch(error => {
                    console.error('获取验证码失败:', error);
                });
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
