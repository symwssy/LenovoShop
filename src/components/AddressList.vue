<template>
  <div class="address-list">
    <h3 class="address-title">收货地址</h3>
    <div v-for="(address, index) in addresses" :key="index" class="address-item">
      <div class="address">
        <div class="address-recipient">收货人：{{ address.recipient }}</div>
        <div class="address-phone">手机号：{{ address.phone }}</div>
        <div class="address-detail">详细地址：{{ address.detail }}</div>
      </div>
      <div class="address-operator">
        <button @click="showDialog('edit', index)" class="address-change">修改</button>
        <button @click="removeAddress(index)" class="address-delete">删除</button>
      </div>
    </div>
    <div v-if="dialogVisible" class="dialog-overlay-edit">
      <div class="dialog-edit">
        <h4>{{ dialogType === 'edit' ? '修改地址' : '添加地址' }}</h4>
        <div class="edit-recipient">
          <div class="recipient-title">收货人：</div>
          <input v-model="dialogData.recipient" type="text" class="recipient-input" />
        </div>
        <div class="edit-phone">
          <div class="phone-title">手机号：</div>
          <input v-model="dialogData.phone" type="text" class="phone-input" />
        </div>
        <div class="edit-detail">
          <div class="detail-title">详细地址：</div>
          <input v-model="dialogData.detail" type="text" class="detail-input" />
        </div>
        <button @click="saveAddress" class="save-btn">保存地址</button>
        <button @click="closeDialog" class="quit-btn">取消</button>
      </div>
    </div>
  </div>
  <button @click="showDialog('add')" class="address-add">新增收货地址</button>
</template>
<script setup>
import { ref, reactive, defineProps, defineEmits } from 'vue'

// 组件属性
const props = defineProps({
  addresses: Array
})

// 组件事件
const emit = defineEmits(['remove-address', 'edit-address', 'add-address'])

const dialogVisible = ref(false)
const dialogType = ref('add')
const currentEditIndex = ref(null)
const dialogData = reactive({
  recipient: '',
  phone: '',
  detail: ''
})

const showDialog = (type, index = null) => {
  dialogType.value = type
  currentEditIndex.value = index
  if (type === 'edit' && index !== null) {
    const address = props.addresses[index]
    dialogData.recipient = address.recipient
    dialogData.phone = address.phone
    dialogData.detail = address.detail
  } else {
    dialogData.recipient = ''
    dialogData.phone = ''
    dialogData.detail = ''
  }
  dialogVisible.value = true
}

const closeDialog = () => {
  dialogVisible.value = false
}

const saveAddress = () => {
  if (!dialogData.recipient || !dialogData.phone || !dialogData.detail) {
    alert('信息填写不完整！')
    return
  }

  const address = {
    recipient: dialogData.recipient,
    phone: dialogData.phone,
    detail: dialogData.detail
  }

  if (dialogType.value === 'edit' && currentEditIndex.value !== null) {
    emit('edit-address', { index: currentEditIndex.value, address })
  } else {
    emit('add-address', address)
  }

  closeDialog()
}

const removeAddress = (index) => {
  emit('remove-address', index)
}
</script>

<style scoped>
.address-list {
  margin-top: 10px;
}

.address-item {
  width: 100%;
  height: 90px;
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 8px;
  justify-content: space-between;
}
.address {
  width: 50%;
  font-size: 15px;
}
.address-recipient,.address-phone,.address-detail{
  width: 600px;
  margin-bottom: 10px;
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
  margin-bottom: 10px;
}
.address-operator {
  width: 130px;
  margin-right: 10px;
  display: flex;
  justify-content: space-between;
}

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
  height: 256px;
  width: 610px;
}

.edit-recipient,.edit-phone,.edit-detail{
  display: flex;
  font-size: 13px;
  align-items: center;
}
.recipient-title,.phone-title,.detail-title{
  height: 28px;
  line-height: 28px;
  width: 65px;
}
.recipient-input,.phone-input{
  border: 1px solid #d2d2d2;
  height: 28px;
  line-height: 28px;
  padding: 0 5px;
  width: 147px;
}
.detail-input{
  border: 1px solid #d2d2d2;
  height: 28px;
  line-height: 28px;
  padding: 0 5px;
  width: 404px;
}
.save-btn,.quit-btn{
  border: 1px solid #d9d8d6;
  border-radius: 5px;
  color: #000;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
  margin: 10px 0 0 66px;
  padding: 0;
  text-align: center;
  width: 80px;
}
</style>

