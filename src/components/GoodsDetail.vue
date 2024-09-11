<template>
  <div class="computer-detail">
    <div class="carousel">
      <button @click="prevImage" class="arrow arrow-left">&lt;</button>
      <div class="image-section">
        <img class="computer-img" :src="images[currentIndex]" alt="Computer Image"/>
      </div>
      <button @click="nextImage" class="arrow arrow-right">&gt;</button>
    </div>
    <div class="details-section">
      <h1 class="name">{{ computer.goodsName }}</h1>
      <p class="type">{{ computer.briefIntro }}</p>
      <div class="discount">【加价购服务满200赠话费】【咨询客服有好礼】
        <a class="more">更多</a>
      </div>
      <div class="computer-price">
        <div class="price-desc">商城价</div>
        <div class="price-group">
          <span class="price-sign">¥</span>
          <span class="price-show">{{ computer.goodsPrice }}</span>
        </div>
      </div>
      <div class="location-selector">
        <label class="place-title" for="province">配送至</label>
        <select v-model="selectedProvince" @change="updateCities">
          <option value="">选择省</option>
          <option v-for="province in provinceList" :key="province" :value="province">
            {{ province }}
          </option>
        </select>
        <label for="city"></label>
        <select v-model="selectedCity" @change="updateAreas">
          <option value="">选择市</option>
          <option v-for="city in cityList" :key="city" :value="city">
            {{ city }}
          </option>
        </select>
        <label for="area"></label>
        <select v-model="selectedArea">
          <option value="">选择县或区</option>
          <option v-for="area in areaList" :key="area" :value="area">
            {{ area }}
          </option>
        </select>
      </div>
      <div class="computer-consist">
        <div class="os-selection">
          <div class="os-title">操作系统</div>
          <span class="os">{{ computer.os || 'Windows 11 家庭中文版' }}</span>
        </div>
        <div class="dealing-selection">
          <div class="dealing-title">处理器</div>
          <span class="dealing">{{ computer.processor || 'i9-14900HX' }}</span>
        </div>
        <div class="store-selection">
          <div class="store-title">存储</div>
          <span class="store">{{ computer.storage || '1T SSD' }}</span>
        </div>
        <div class="GPU-selection">
          <div class="GPU-title">显卡</div>
          <span class="GPU">{{ computer.gpu || 'RTX 4060Ti' }}</span>
        </div>
      </div>
      <div class="buy-count">
        <div class="buy-title">购买数量</div>
        <div class="count-container">
          <button @click="decrement">-</button>
          <span class="total-count">{{ count }}</span>
          <button @click="increment">+</button>
        </div>
      </div>
      <div class="buy">
        <button class="buy-button" @click="buyComputer">立即购买</button>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

import product1 from '../assets/products/product1.jpg';
import product2 from '../assets/products/product2.jpg';

export default {
  name: 'ComputerDetail',
  data() {
    return {
      images: [product1, product2],
      currentIndex: 0,
      computer: {
        goodsName: '',
        briefIntro: '',
        goodsPrice: '',
        os: '',
        processor: '',
        storage: '',
        gpu: '',
        image: ''
      },
      provinceList: ['山东省', '江苏省'],
      cityList: [],
      areaList: [],
      selectedProvince: '',
      selectedCity: '',
      selectedArea: '',
      data: {
        '山东省': {
          '青岛市': ['黄岛区', '崂山区'],
          '济南市': ['历下区', '市中区']
        },
        '江苏省': {
          '南京市': ['鼓楼区', '建邺区'],
          '苏州市': ['工业园区', '虎丘区']
        }
      },
      count: 0,
    }
  },
  methods: {
    async fetchComputerDetail(id) {
      try {
        const response = await axios.get(`/api/index/detail/${id}`);
        if (response.data.code === 0) {
          const data = response.data.data;
          this.computer = {
            goodsName: data.goodsName,
            briefIntro: data.briefIntro,
            goodsPrice: data.goodsPrice,
            image: data.goodsPic
          };
          // Set images for carousel
          this.images = [data.goodsPic]; // Assuming only one image for now
        } else {
          console.error('API 返回错误:', response.data.message);
        }
      } catch (error) {
        console.error('请求失败:', error);
      }
    },
    nextImage() {
      if (this.currentIndex < this.images.length - 1) {
        this.currentIndex++;
      } else {
        this.currentIndex = 0;
      }
    },
    prevImage() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
      } else {
        this.currentIndex = this.images.length - 1;
      }
    },
    buyComputer() {
      this.$router.push('/purchase'); // 跳转到购买页面
    },
    updateCities() {
      this.cityList = [];
      this.areaList = [];
      this.selectedCity = '';
      this.selectedArea = '';
      if (this.selectedProvince && this.data[this.selectedProvince]) {
        this.cityList = Object.keys(this.data[this.selectedProvince]);
      }
    },
    updateAreas() {
      this.areaList = [];
      this.selectedArea = '';
      if (this.selectedCity && this.selectedProvince && this.data[this.selectedProvince][this.selectedCity]) {
        this.areaList = this.data[this.selectedProvince][this.selectedCity];
      }
    },
    increment() {
      this.count += 1;
    },
    decrement() {
      if (this.count > 0) {
        this.count -= 1;
      }
    },
  },
  mounted() {
    const route = useRoute();
    const id = route.params.id; // 获取 URL 中的 id 参数
    this.fetchComputerDetail(id);
  }
}
</script>


<style scoped>
.computer-detail {
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.carousel {
  display: flex;
  align-items: center;
  position: relative;
  width: 40%;
  height: 100%;
  margin-left: 5%;
}

.image-section {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.computer-img {
  width: 720px;
  height: 720px;
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  font-size: 18px;
}

.arrow-left {
  left: 10px;
}

.arrow-right {
  right: 10px;
}

.details-section {
  flex: 1;
  width: 50%;
  height: 100%;
  margin-left: 5%;
  margin-top: 5%;
}

.name {
  font-size: 22px;
  font-weight: normal;
}

.type {
  color: #A2A2A2;
  font-size: 14px;
  margin-top: 8px;
  line-height: 22px;
  word-break: break-all;
}

.discount {
  height: 22px;
  margin-top: 20px;
  margin-bottom: 16px;
  font-size: 13px;
  color: #E1140A;
  font-weight: 400;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.more {
  font-size: 12px;
  color: #3E8DDD;
  text-align: right;
  font-weight: 400;
  cursor: pointer;
  border-bottom: 1px solid #3E8DDD;
  margin-top: 2px;
  margin-left: 5px;
}

.computer-price {
  display: flex;
  width: 95%;
  height: 80px;
  align-items: center;
  background: #F9F9F9;
  border: 1px solid #F0F0F0;
  margin-bottom: 20px;
}

.computer-consist {
  width: 95%;
  height: 130px;
  margin-bottom: 20px;
  margin-top: 20px;
}

.price-desc {
  display: inline-block;
  font-size: 16px;
  color: #434242;
  letter-spacing: 0.8px;
  margin-right: 35px;
  margin-top: 10px;
  margin-left: 20px;
}

.price-group {
  display: inline-block;
}

.price-sign {
  font-size: 20px;
  color: #E1140A;
  font-weight: bold;
  margin-right: 6px;
}

.price-show {
  font-weight: bold;
  font-size: 26px;
  color: #E1140A;
}

.location-selector {
  display: flex;
  width: 100%;
  height: 20%;
  margin-bottom: 5px;
}

.place-title {
  float: left;
  width: 10%;
  line-height: 30px;
  margin-right: 30px;
  font-size: 14px;
}

select {
  width: 20%;
  height: 40%;
  padding: 5px;
  font-size: 12px;
}

.os-selection, .dealing-selection, .store-selection, .GPU-selection {
  display: flex;
  font-size: 14px;
  margin: 20px 0;
}

.os-title {
  font-size: 14px;
  margin-right: 44px;
}

.dealing-title {
  font-size: 14px;
  margin-right: 58px;
}

.store-title, .GPU-title {
  font-size: 14px;
  margin-right: 72px;
}

.os, .GPU, .store, .dealing {
  border: 1px solid black;
  color: black;
  cursor: pointer;
  height: 20px;
  width: 180px;
  text-align: center;
  line-height: 20px;
}

.buy-count {
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
}

.buy-title {
  font-size: 14px;
  margin-top: 30px;
  margin-right: 30px;
  width: 10%;
  height: 32px;
}

.count-container {
  display: flex;
  width: 50%;
  height: 70%;
  margin-top: 20px;
}

.count-container button {
  width: 10%;
  height: 85%;
  font-size: 14px;
  cursor: pointer;
  border: 1px solid #DADADA;
  background: white;
  margin-right: -1px; /*消除边框叠加*/
}

.total-count {
  font-size: 12px;
  width: 20%;
  height: 80%;
  text-align: center;
  line-height: 32px;
  border: 1px solid #DADADA;
  margin-right: -1px;
}

.buy {
  width: 100%;
  height: 60px;
  display: flex;
  margin-top: 10px;
  justify-content: space-between;
}

.buy-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 14px;
  border-radius: 5px;
  cursor: pointer;
  width: 20%;
  height: 65%;
}

.buy-button:hover {
  background-color: #0056b3;
}
</style>
