<template>
  <h3 class="address-title">收货地址</h3>
  <button @click="showDialog('add')" class="address-add">新增收货地址</button>
  <div class="address-list">
    <div v-for="(address, index) in addresses" :key="index" class="address-item">
      <div class="address">
        <div class="address-detail">{{ address.recLocation }}</div>
        <div class="address-recipient-phone">
          <span class="address-recipient">{{ address.consignee }}</span>
          <span class="address-phone">{{ address.phoneNumber }}</span>
        </div>
      </div>
      <div class="address-operator">
        <button @click="showDialog('edit', index)" class="address-change">修改</button>
        <button @click="removeAddress(index)" class="address-delete">删除</button>
      </div>
    </div>

    <!-- 弹窗逻辑 -->
    <div v-if="dialogVisible" class="dialog-overlay-edit">
      <div class="dialog-edit">
        <h4>{{ dialogType === 'edit' ? '修改地址' : '添加地址' }}</h4>
        <div class="edit-recipient">
          <div class="recipient-title">收货人：</div>
          <input v-model="dialogData.consignee" type="text" class="recipient-input" />
        </div>
        <div class="edit-phone">
          <div class="phone-title">手机号：</div>
          <input v-model="dialogData.phoneNumber" type="text" class="phone-input" />
        </div>
        <div class="edit-detail">
          <div class="detail-title">详细地址：</div>
          <input v-model="dialogData.recLocation" type="text" class="detail-input" />
        </div>
        <div class="dialog-buttons">
          <button @click="closeDialog" class="quit-btn">取消</button>
          <button @click="saveAddress" class="save-btn">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

const addresses = ref([])  // 存储地址信息

const dialogVisible = ref(false)
const dialogType = ref('add')
const currentEditIndex = ref(null)
const dialogData = reactive({
  consignee: '',
  phoneNumber: '',
  recLocation: ''
})

const token = localStorage.getItem('token')

// 获取地址列表
onMounted(async () => {
  try {
    const response = await axios.get('/api/receive/list', {
      headers: {
        'Authorization': `${token}`
      }
    })

    if (response.data.code === 0) {
      addresses.value = response.data.data  // 将API返回的数据赋值给 addresses
    } else {
      console.error('获取地址列表失败:', response.data.message)
    }
  } catch (error) {
    console.error('获取地址列表失败:', error)
  }
})

const showDialog = (type, index = null) => {
  dialogType.value = type
  currentEditIndex.value = index
  if (type === 'edit' && index !== null) {
    const address = addresses.value[index]
    dialogData.consignee = address.consignee
    dialogData.phoneNumber = address.phoneNumber
    dialogData.recLocation = address.recLocation
  } else {
    dialogData.consignee = ''
    dialogData.phoneNumber = ''
    dialogData.recLocation = ''
  }
  dialogVisible.value = true
}

const closeDialog = () => {
  dialogVisible.value = false
}

const saveAddress = () => {
  if (!dialogData.consignee || !dialogData.phoneNumber || !dialogData.recLocation) {
    alert('信息填写不完整！')
    return
  }

  const address = {
    consignee: dialogData.consignee,
    phoneNumber: dialogData.phoneNumber,
    recLocation: dialogData.recLocation
  }

  if (dialogType.value === 'edit' && currentEditIndex.value !== null) {
    addresses.value[currentEditIndex.value] = address
  } else {
    addresses.value.push(address)
  }

  closeDialog()
}

const removeAddress = (index) => {
  addresses.value.splice(index, 1)
}
</script>

<style scoped>
.address-list {
  margin-top: 10px;
  text-align: left; /* 地址列表左对齐 */
}

.address-item {
  width: 100%;
  padding: 10px;
  border-bottom: 1px solid #ddd;
  display: flex;
  justify-content: space-between;
}

.address {
  flex: 1;
  font-size: 15px;
}

.address-detail {
  margin-bottom: 5px;
}

.address-recipient-phone {
  display: flex;
  justify-content: flex-start;
}

.address-recipient {
  margin-right: 20px;
}

.address-operator {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: 10px;
}

.address-delete, .address-change, .address-add {
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #000;
  font-size: 13px;
  height: 24px;
  line-height: 24px;
  padding: 0 12px;
  cursor: pointer;
}

.address-add {
  margin-top: 10px;
  display: block;
  text-align: center;
  margin: 10px auto;
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #fff;
  background-color: #ff2600;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
  padding: 0 15px;
}

.address-add:hover {
  background-color: #d80000;
}

.address-title {
  text-align: center;
}

/* 弹窗样式 */
.dialog-overlay-edit {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.dialog-edit {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
}

.edit-recipient, .edit-phone, .edit-detail {
  margin-bottom: 10px;
}

.dialog-buttons {
  display: flex;
  justify-content: space-between;
}

.save-btn, .quit-btn {
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #000;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
  padding: 0 15px;
  cursor: pointer;
  width: 100px;
}

.save-btn {
  background-color: #4CAF50;
  color: white;
}

.quit-btn {
  background-color: #f44336;
  color: white;
}
</style>
