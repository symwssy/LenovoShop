<template>
  <div class="background-page">
    <div class="user-profile">
      <div class="avatar-section">
        <div class="user-img">
          <img :src="avatarUrl" class="avatar" @click="triggerFileInput"/>
          <input type="file" ref="fileInput" @change="handleFileChange" style="display: none;"/>
        </div>
      </div>
      <div class="personal-info">
        <div class="info-field">
          <div class="user-name">
            <span class="name">用户名：</span>
            <span v-if="!editingName" @click="startEditing('name')">{{ user.name }}</span>
            <input v-else v-model="editFields.name" class="name-input" @blur="confirmEdit('name')"/>
          </div>
        </div>
        <div class="info-field">
          <div class="user-tel">
            <span class="tel">电话：</span>
            <span v-if="!editingPhone" @click="startEditing('phone')">{{ user.phone }}</span>
            <input v-else v-model="editFields.phone" class="tel-input" @blur="confirmEdit('phone')"/>
          </div>
        </div>
        <div class="info-field">
          <div class="user-save">
            <span class="save-title">余额：</span>
            <span class="save">{{ user.save }}元</span>
          </div>
          <div class="save-operator">
            <button @click="showDialog('withdraw')" class="withdraw">提现</button>
            <button @click="showDialog('deposit')" class="recharge">充值</button>
          </div>
          <div v-if="dialogVisible" class="dialog-overlay">
            <div class="dialog">
              <h3>{{ dialogType === 'withdraw' ? '提现' : '充值' }}</h3>
              <input v-model.number="amount" type="number" placeholder="请输入金额"/>
              <div class="save-dealing">
                <button @click="handleConfirm" class="yes">确定</button>
                <button @click="closeDialog" class="no">取消</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="address-section">
        <AddressList
            :addresses="addresses"
            @add-address="handleAddAddress"
            @edit-address="handleEditAddress"
            @remove-address="handleRemoveAddress"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import AddressList from './AddressList.vue'
import OrderList from './OrderList备份.vue'
import avatarImage from '../assets/default.jpg'

const user = ref({
  userId: '',
  name: '',
  phone: '',
  save: 0
})

const editFields = ref({ ...user.value })
const editingUserId = ref(false)
const editingName = ref(false)
const editingPhone = ref(false)

const avatarUrl = ref(avatarImage)
const dialogVisible = ref(false)
const dialogType = ref('')
const amount = ref(0)
const addresses = ref([])
const orders = ref([]) // 订单数据也可以从 API 获取

const token = localStorage.getItem('token')

const startEditing = (field) => {
  if (field === 'userId') {
    editingUserId.value = true
  } else if (field === 'name') {
    editingName.value = true
  } else if (field === 'phone') {
    editingPhone.value = true
  }
}

const confirmEdit = (field) => {
  const value = editFields.value[field]
  if (value.trim() === '') {
    alert('输入不能为空')
    return
  }
  user.value[field] = value
  editingUserId.value = editingName.value = editingPhone.value = false
}

const triggerFileInput = () => {
  const fileInput = document.querySelector('input[type="file"]')
  fileInput.click()
}

const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      avatarUrl.value = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const showDialog = (type) => {
  dialogType.value = type
  dialogVisible.value = true
}

const closeDialog = () => {
  dialogVisible.value = false
  amount.value = 0
}

const handleConfirm = () => {
  if (dialogType.value === 'withdraw') {
    if (amount.value > 0 && amount.value <= user.value.save) {
      user.value.save -= amount.value
    } else {
      alert('提现金额无效')
    }
  } else if (dialogType.value === 'deposit') {
    if (amount.value > 0) {
      user.value.save += amount.value
    } else {
      alert('充值金额无效')
    }
  }
  closeDialog()
}

onMounted(async () => {
  console.log(token);

  try {
    const response = await axios.get('/api/user/userInfo', {
      headers: {
        'Authorization': `${token}` // 确保 token 正确传递
      }
    })

    if (response.data.code === 0) {
      const userInfo = response.data.data
      user.value = {
        userId: userInfo.username || '',
        name: userInfo.username || '', // 根据需要调整
        phone: userInfo.phoneNumber || '',
        save: userInfo.balance || 0
      }
      editFields.value = { ...user.value }
    } else {
      console.error('获取用户信息失败:', response.data.message)
    }
  } catch (error) {
    console.error('获取用户信息失败:', error.response ? error.response.data : error.message)
  }
})

</script>

<style scoped>
.background-page {
}

.user-profile {
  max-width: 800px;
  margin: auto;
  border: 1px solid #ddd;
  padding: 20px;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.avatar-section {
  text-align: center;
  position: relative;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
}

.UserId-show, .UserId-edit {
  display: flex;
  width: 530px;
  height: 80px;
  justify-content: center;
}

.id-title {
  width: 80px;
  line-height: 80px;
  font-size: 18px;
}

.user-id {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
}

.id {
  width: 180px;
  font-size: 20px;
  line-height: 80px;
  margin-right: 15%;
}

.edit-input {
  padding: 5px;
  margin-right: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  height: 30px;
  font-size: 16px;
  margin-top: 20px;
}

.name-input, .tel-input {
  padding: 5px;
  margin-right: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  height: 20px;
  font-size: 14px;
}

.personal-info {
  margin-top: 20px;
  text-align: center; /* 确保 .info-field 元素水平居中 */
}

.info-field {
  display: inline-flex; /* 使用 inline-flex 使其宽度适应内容，同时允许水平居中 */
  width: 60%; /* 设置宽度 */
  margin: 0 auto 10px; /* 上下居中，底部留白 */
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  align-items: center;
  height: 30px;
  text-align: left; /* 保持内部文字左对齐 */
}

.info-field input {
  width: 100%; /* 输入框宽度填满 .info-field */
  border: none;
  outline: none;
  height: 100%; /* 输入框高度填满 .info-field */
}


.user-name, .user-tel, .user-save {
  width: 100%;
  font-size: 16px;
}

.address-section {
  margin-top: 20px;
}

.add-address input {
  padding: 8px;
  margin-right: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.add-address button {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

.add-address button:hover {
  background-color: #0056b3;
}
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.dialog {
  background: white;
  padding: 30px;
  border-radius: 8px;
  text-align: center;
  width: 300px; /* 弹窗宽度 */
  max-width: 90%; /* 确保在小屏幕上也能显示 */
}

.dialog h3 {
  margin-bottom: 20px;
  font-size: 18px;
}

.dialog input[type="number"] {
  width: 100%;
  padding: 8px;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

.save-dealing, .save-operator {
  display: flex;
  justify-content: center;
  gap: 10px; /* 按钮间距 */
}

.save-operator button {
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #000;
  font-size: 16px;
  height: 30px; /* 按钮高度 */
  line-height: 30px;
  padding: 0 20px; /* 按钮左右内边距 */
  cursor: pointer;
  white-space: nowrap; /* 确保按钮文字不换行 */
}

.withdraw, .recharge, .yes, .no {
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #000;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
  padding: 0 15px;
  cursor: pointer;
  white-space: nowrap; /* 确保按钮文字不换行 */
}

.save-dealing button:hover, .save-operator button:hover {
  background-color: #f0f0f0;
}

</style>
