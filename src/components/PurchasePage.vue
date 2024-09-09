<template>
  <div class="purchase-container">
    <div class="buy-title">购买页面</div>
    <form @submit.prevent="handlePurchase">
      <div class="form-group">
        <label for="address">选择收货地址:</label>
        <select v-model="selectedAddress" @change="updateAddressDetails" id="address" required>
          <option value="" disabled>请选择地址</option>
          <option v-for="address in addressList" :key="address.id" :value="address.id">{{ address.address }}</option>
        </select>
      </div>
      <div class="form-group">
        <label for="receiver">收货人:</label>
        <input type="text" v-model="receiver" id="receiver" required />
      </div>
      <div class="form-group">
        <label for="phone">联系电话:</label>
        <input type="tel" v-model="phone" id="phone" required />
      </div>
      <div class="form-group">
        <label for="purchaseMethod">选择购买方式:</label>
        <select v-model="purchaseMethod" id="purchaseMethod" required>
          <option value="" disabled>请选择购买方式</option>
          <option value="online">在线支付</option>
          <option value="cash">货到付款</option>
        </select>
      </div>
      <div class="total">
        <div class="total-title">实付款: </div>
        <div class="buy-total">{{total}}  元</div>
      </div>
      <div class="form-buttons">
        <button type="submit" class="btn btn-success">购买</button>
        <button type="button" class="btn btn-cancel" @click="handleCancel">取消</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      total:'999.99',
      selectedAddress: '',
      receiver: '',
      phone: '',
      purchaseMethod: '',
      addressList: [
        { id: '1', address: '山东省青岛市', receiver: '张三', phone: '1234567890' },
        { id: '2', address: '地址2', receiver: '李四', phone: '0987654321' },
        { id: '3', address: '地址3', receiver: '王五', phone: '1122334455' }
      ]
    };
  },
  methods: {
    updateAddressDetails() {
      const selected = this.addressList.find(addr => addr.id === this.selectedAddress);
      if (selected) {
        this.receiver = selected.receiver;
        this.phone = selected.phone;
      }
    },
    handlePurchase() {
      alert('购买成功');
    },
    handleCancel() {
      this.selectedAddress = '';
      this.receiver = '';
      this.phone = '';
      this.purchaseMethod = '';
    }
  }
};
</script>

<style scoped>
.purchase-container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.buy-title{
  text-align: center;
  color: #333;
  font-size: 24px;
  font-weight: bold;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input, select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-buttons {
  text-align: center;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  color: #fff;
  cursor: pointer;
  margin: 5px;
}

.btn-success {
  background-color: #28a745;
}

.btn-cancel {
  background-color: #dc3545;
}

#receiver {
  width: 590px;
}
#phone{
  width: 572px;
}
#address, #purchaseMethod {
  width: 590px;
}
.total{
  display: flex;
  font-size: 16px;
}
.total-title{
  margin-right: 20px;
}
</style>
