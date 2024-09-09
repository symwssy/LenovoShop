<template>
  <div class="user-profile">
    <div class="avatar-section">
      <div class="user-img">
        <img :src="avatarUrl" class="avatar" @click="triggerFileInput" />
        <input type="file" ref="fileInput" @change="handleFileChange" style="display: none;" />
      </div>
      <div class="user-id">
        <div v-if="!editingUserId" class="UserId-show" @click="startEditing('userId')">
          <div class="id-title">用户名：</div>
          <div class="id">{{ user.userId }}</div>
        </div>
        <div v-else class="UserId-edit">
          <input v-model="editFields.userId" class="edit-input" @blur="confirmEdit('userId')" />
        </div>
      </div>
    </div>
    <div class="personal-info">
      <div class="info-field">
        <div class="user-name">
          <span class="name">姓名：</span>
          <span v-if="!editingName" @click="startEditing('name')">{{ user.name }}</span>
          <input v-else v-model="editFields.name" class="name-input" @blur="confirmEdit('name')" />
        </div>
      </div>
      <div class="info-field">
        <div class="user-tel">
          <span class="tel">电话：</span>
          <span v-if="!editingPhone" @click="startEditing('phone')">{{ user.phone }}</span>
          <input v-else v-model="editFields.phone" class="tel-input" @blur="confirmEdit('phone')" />
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
            <input v-model.number="amount" type="number" placeholder="请输入金额" />
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
    <div class="orders-section">
      <OrderList
          :orders="orders"
          @order-click="handleOrderClick"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AddressList from './AddressList.vue'
import OrderList from './OrderList.vue'
import avatarImage from '@/assets/100001.png'

// 用户信息对象
const user = ref({
  userId: 'User123456',
  name: '张三',
  phone: '13246276472',
  save: 1000 // 初始余额
})

const editFields = ref({ ...user.value })
const editingUserId = ref(false)
const editingName = ref(false)
const editingPhone = ref(false)

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

const avatarUrl = ref(avatarImage)
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
const addresses = ref([
  {
    recipient: '张三',
    phone: '1380000870',
    detail: '北京市朝阳区建国门外大街1号'
  },
  {
    recipient: '李四',
    phone: '13900000000',
    detail: '上海市浦东新区陆家嘴2号'
  }
])
const handleAddAddress = (address) => {
  addresses.value.push(address)
}
const handleEditAddress = ({ index, address }) => {
  addresses.value[index] = address
}
const handleRemoveAddress = (index) => {
  addresses.value.splice(index, 1)
}
const dialogVisible = ref(false)
const dialogType = ref('')
const amount = ref(0)

const showDialog = (type) => {
  dialogType.value = type
  dialogVisible.value = true
}

const closeDialog = () => {
  dialogVisible.value = false
  amount.value = 0 // 重置金额输入框
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
</script>
<style scoped>
.user-profile {
  width: 90%;
  height: 100%;
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
.UserId-show,.UserId-edit{
  display: flex;
  width: 530px;
  height: 80px;
  justify-content: center;
}
.id-title{
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
.name-input,.tel-input{
  padding: 5px;
  margin-right: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  height: 20px;
  font-size: 14px;
}
.personal-info {
  margin-top: 20px;
}

.info-field {
  margin-bottom: 10px;
  display: flex;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  align-items: center;
  height: 30px;
}
.user-name,.user-tel,.user-save{
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
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  width: 200px;
  height: 150px;
}
.save-operator {
  width: 155px;
  display: flex;
  justify-content: space-between;
}
.withdraw,.recharge{
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #000;
  font-size: 13px;
  height: 24px;
  line-height: 24px;
  padding: 0 12px;
  cursor: pointer;
}
.save-dealing{
  width: 120px;
  display: flex;
  margin-left: 40px;
  margin-top: 20px;
  justify-content: space-between;
}
.yes,.no{
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #000;
  font-size: 13px;
  height: 24px;
  line-height: 24px;
  padding: 0 12px;
  cursor: pointer;
}
</style>
