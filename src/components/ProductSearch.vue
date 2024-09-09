<template>
  <div id="product-search">
    <input v-model="searchQuery" @input="filterProducts" placeholder="搜索商品..." />
    <select v-model="sortOrder" @change="sortProducts">
      <option value="default">默认排序</option>
      <option value="asc">价格升序</option>
      <option value="desc">价格降序</option>
    </select>
    <div v-if="filteredProducts.length">
      <div v-for="product in paginatedProducts" :key="product.id" class="product">
        <h2>{{ product.name }}</h2>
        <p>型号: {{ product.model }}</p>
        <p>价格: {{ product.price }}</p>
        <a :href="product.link" target="_blank">立即购买</a>
      </div>
      <div class="pagination">
        <button @click="goToPage(1)" :disabled="currentPage === 1">首页</button>
        <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
        <span v-for="page in totalPages" :key="page">
    <button @click="goToPage(page)" :class="{ active: page === currentPage }">{{ page }}</button>
  </span>
        <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
        <button @click="goToPage(totalPages)" :disabled="currentPage === totalPages">末页</button>
        <span>当前页: {{ currentPage }} / {{ totalPages }}</span>
        <input type="number" v-model.number="pageInput" @change="goToPage(pageInput)" min="1" :max="totalPages" />
      </div>

    </div>
    <div v-else>没有找到商品。</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: [
        {id: 1, name: '商品A', model: 'A1', price: 100, link: '#'},
        {id: 2, name: '商品B', model: 'B1', price: 200, link: '#'},
        {id: 3, name: '商品C', model: 'C1', price: 150, link: '#'},
        {id: 4, name: '商品D', model: 'D1', price: 50, link: '#'},
        {id: 5, name: '商品E', model: 'E1', price: 300, link: '#'},
        {id: 6, name: '商品F', model: 'F1', price: 250, link: '#'},
        {id: 7, name: '商品G', model: 'G1', price: 120, link: '#'},
        {id: 8, name: '商品H', model: 'H1', price: 180, link: '#'},
        {id: 9, name: '商品I', model: 'I1', price: 90, link: '#'},
        {id: 10, name: '商品J', model: 'J1', price: 220, link: '#'}
      ],
      searchQuery: '',
      sortOrder: 'default',
      currentPage: 1,
      pageSize: 5,
      pageInput: 1
    };
  },
  computed: {
    filteredProducts() {
      const query = this.searchQuery.toLowerCase();
      return this.products.filter(product =>
          product.name.toLowerCase().includes(query) ||
          product.model.toLowerCase().includes(query)
      );
    },
    sortedProducts() {
      let sorted = [...this.filteredProducts];
      if (this.sortOrder === 'asc') {
        sorted.sort((a, b) => a.price - b.price);
      } else if (this.sortOrder === 'desc') {
        sorted.sort((a, b) => b.price - a.price);
      }
      return sorted;
    },
    paginatedProducts() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.sortedProducts.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.sortedProducts.length / this.pageSize);
    }
  },
  methods: {
    filterProducts() {
      this.currentPage = 1; // Reset to the first page on search
      this.pageInput = 1;
    },
    sortProducts() {
      this.currentPage = 1; // Reset to the first page on sort
      this.pageInput = 1;
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.pageInput = this.currentPage;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.pageInput = this.currentPage;
      }
    },
    goToPage(page) {
      const pageNumber = Math.max(1, Math.min(page, this.totalPages));
      this.currentPage = pageNumber;
      this.pageInput = pageNumber;
    }
  }
};
</script>


<style>
#product-search {
  width: 80%;
  max-width: 1200px;
  margin: 0 auto; /* Center the search container horizontally */
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Add shadow for better visual separation */
  border-radius: 8px; /* Rounded corners for modern look */
  background-color: #f9f9f9; /* Light background color for better readability */
}

input[type="text"], select {
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 100%; /* Ensure inputs and select take full width */
  box-sizing: border-box; /* Include padding and border in width calculation */
}

input[type="number"] {
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 60px; /* Adjust width for number input */
  text-align: center;
}

.product {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff; /* White background for product items */
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1); /* Light shadow for better separation */
}

.product h2 {
  margin: 0 0 10px 0;
  font-size: 1.2em; /* Slightly larger font size for product names */
}

.product p {
  margin: 5px 0;
}

.product a {
  color: #007bff; /* Primary link color */
  text-decoration: none;
}

.product a:hover {
  text-decoration: underline; /* Underline link on hover */
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  flex-wrap: wrap; /* Allow wrapping of pagination buttons if necessary */
}

.pagination button {
  padding: 10px 15px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #fff; /* White background for buttons */
  margin: 0 5px;
  cursor: pointer;
}

.pagination button:disabled {
  cursor: not-allowed;
  opacity: 0.6;
}

.pagination button.active {
  background-color: #007bff;
  color: #fff;
}

.pagination span {
  margin: 0 5px;
  display: inline-block;
}

.pagination input {
  margin-left: 10px;
}

</style>
