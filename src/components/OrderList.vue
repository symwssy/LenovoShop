<template>
  <div class="container">
    <h3 class="order-title">我的订单</h3>
    <div class="controls">
      <select v-model="selectedStatus" class="status-select">
        <option value="all">全部订单</option>
        <option value="pending">待付款</option>
        <option value="shipped">待发货</option>
        <option value="delivered">待收货</option>
      </select>
      <input v-model="searchQuery" placeholder="搜索商品名称" class="search-input" />
    </div>
    <div class="order-table-container">
      <table class="order-table">
        <thead>
        <tr>
          <th>创建时间</th>
          <th>订单号</th>
          <th>商品名称</th>
          <th>单价</th>
          <th>数量</th>
          <th>收货人</th>
          <th>总金额</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-if="filteredOrders.length === 0">
          <td colspan="9" class="no-data">暂无订单</td>
        </tr>
        <tr v-for="order in filteredOrders" :key="order.orderNumber">
          <td>{{ order.creationDate }}</td>
          <td>{{ order.orderNumber }}</td>
          <td>{{ order.productName }}</td>
          <td>{{ order.unitPrice }}</td>
          <td>{{ order.quantity }}</td>
          <td>{{ order.receiver }}</td>
          <td>{{ order.totalAmount }}</td>
          <td>{{ order.status }}</td>
          <td>
            <button v-if="order.status === '待付款'" @click="handlePayment(order)" class="action-btn payment-btn">付款</button>
            <button v-if="order.status === '待发货'" @click="handleRefund(order)" class="action-btn refund-btn">退款</button>
            <button v-if="order.status === '待收货'" @click="handleReceipt(order)" class="action-btn receipt-btn">确认收货</button>
            <button @click="handleDelete(order)" class="action-btn delete-btn">删除订单</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// 订单数据示例
const orders = ref([
  {
    creationDate: '2024-09-01',
    orderNumber: 'ORD001',
    productName: '商品A',
    unitPrice: 100,
    quantity: 2,
    receiver: '张三',
    totalAmount: 200,
    status: '待付款'
  },
  {
    creationDate: '2024-09-02',
    orderNumber: 'ORD002',
    productName: '商品B',
    unitPrice: 150,
    quantity: 1,
    receiver: '李四',
    totalAmount: 150,
    status: '待发货'
  },
  {
    creationDate: '2024-09-09',
    orderNumber: 'ORD004',
    productName: '商品B',
    unitPrice: 150,
    quantity: 1,
    receiver: '李四',
    totalAmount: 10,
    status: '待收货'
  }
])

// 状态和搜索查询的响应式变量
const selectedStatus = ref('all')
const searchQuery = ref('')

// 计算属性：根据状态和搜索条件过滤订单
const filteredOrders = computed(() => {
  return orders.value.filter(order => {
    // 确保状态匹配
    const matchesStatus = selectedStatus.value === 'all' || order.status === getStatusLabel(selectedStatus.value)
    // 确保搜索匹配
    const matchesSearch = order.productName.includes(searchQuery.value)
    return matchesStatus && matchesSearch
  })
})

// 将选择的状态转换为数据中的状态值
const getStatusLabel = (status) => {
  const statusMap = {
    'pending': '待付款',
    'shipped': '待发货',
    'delivered': '待收货',
    'received': '已收货'
  }
  return statusMap[status] || status
}

// 删除订单
const handleDelete = (order) => {
  orders.value = orders.value.filter(o => o.orderNumber !== order.orderNumber)
}

// 处理付款、退款等
const handlePayment = (order) => { /* 处理付款 */ }
const handleRefund = (order) => { /* 处理退款 */ }

// 确认收货函数：将订单状态改为已收货
const handleReceipt = (order) => {
  const index = orders.value.findIndex(o => o.orderNumber === order.orderNumber)
  if (index !== -1) {
    orders.value[index].status = '已收货'
  }
}
</script>

<style scoped>
.container {
  width: 100%;
}

.controls {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
}

.status-select, .search-input {
  padding: 8px;
  font-size: 15px;
}

.status-select {
  width: 200px;
}

.search-input {
  flex-grow: 1;
  margin-left: 20px;
}

.order-table-container {
  width: 100%;
  height: 400px;
  overflow-y: auto;
}

.order-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 15px;
}

.order-table th, .order-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: center;
}

.order-table th {
  background-color: #f4f4f4;
}

.order-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.order-table tr:hover {
  background-color: #f1f1f1;
}

.order-table .no-data {
  text-align: center;
  font-style: italic;
  color: #888;
}

.action-btn {
  border: none;
  padding: 8px 16px;
  font-size: 12px;
  cursor: pointer;
  margin: 2px;
  width: 80px;
}

.payment-btn {
  background-color: #28a745;
  color: white;
}

.refund-btn {
  background-color: #dc3545;
  color: white;
}

.receipt-btn {
  background-color: #007bff;
  color: white;
}

.delete-btn {
  background-color: #6c757d;
  color: white;
}
</style>
