<template>
  <div>
    <div class="main">
      <div class="title-content">
<!--        <img src="@/assets/title.svg" alt="头部图片">-->
      </div>
      <h3 style="margin-left: 48%;">登 录</h3>
      <h3 style="margin-left: 47%;">L o g i n</h3>
      <div class="centered-content">
<!--        <img src="@/assets/loading.svg" alt="加载图片">-->

      </div>
      <h3 style="margin-left: 47%;">请 稍 后</h3>
    </div>
  </div>
</template>

<script>
export default {
  data() {

  },
};
</script>

<style scoped>
/* 居中内容样式 */
.centered-content {
  display: flex;
  justify-content: center;
  align-items: center; /* 将 align-items 设置为 center，使子元素在垂直方向上居中对齐 */
  height: 45vh; /* 使父元素铺满整个视口高度 */
  flex-direction: column; /* 让子元素竖直排列 */
}
.title-content {
  display: flex;
  height: 25vh; /* 使父元素铺满整个视口高度 */
  justify-content: center; /* 水平居中 */
  align-items: flex-start; /* 顶部对齐 */
  flex-direction: column; /* 让子元素竖直排列 */
  margin-left: 36%;
}
/* 主区域 */
.main {
  position: absolute;
  top: 0px;
  bottom: 0px;
  left: 0px;
  right: 0px;
  padding: 10px;
  overflow-y: auto;
  background-color: #F0F2F4;
}
</style>
