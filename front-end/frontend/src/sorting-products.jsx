/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from 'react';
import axios from 'axios';

// Base API configuration
const API_BASE_URL = 'http://localhost:8080/api/v1';


const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
  });
  
  // Interceptor to add Authorization header
  axiosInstance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('jwtToken'); // Replace with sessionStorage if required
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => Promise.reject(error)
  );

// Sorting Component
const SortingInterface = () => {
  const [sortType, setSortType] = useState('HEAP_SORT');
  const [inputValues, setInputValues] = useState('');
  const [sortedResult, setSortedResult] = useState(null);
  const [error, setError] = useState('');

  const sortingAlgorithms = [
    { value: 'HEAP_SORT', label: 'Heap Sort' },
    { value: 'MERGE_SORT', label: 'Merge Sort' },
    { value: 'QUICK_SORT', label: 'Quick Sort' },
    { value: 'RADIX_SORT', label: 'Radix Sort' },
    { value: 'BUCKET_SORT', label: 'Bucket Sort' }
  ];

  const handleSort = async () => {
    try {
      const response = await axiosInstance.post(`${API_BASE_URL}/sorting/sort`, {
        sortType,
        values: inputValues
      });
      setSortedResult(response.data);
      setError('');
    } catch (err) {
      setError('Sorting failed. Please check your input.');
      setSortedResult(null);
    }
  };


  return (
    <div className="container mx-auto p-6 bg-white shadow-md rounded-lg">
      <h2 className="text-2xl font-bold mb-6 text-center text-blue-600">Sorting Algorithms</h2>
      
      <div className="grid md:grid-cols-2 gap-6">
        {/* Sorting Controls */}
        <div className="space-y-4">
          <div>
            <label className="block text-gray-700 mb-2 font-semibold">Sort Algorithm</label>
            <select
              value={sortType}
              onChange={(e) => setSortType(e.target.value)}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              {sortingAlgorithms.map((algo) => (
                <option key={algo.value} value={algo.value}>
                  {algo.label}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-gray-700 mb-2 font-semibold">Input Values</label>
            <input
              type="text"
              value={inputValues}
              onChange={(e) => setInputValues(e.target.value)}
              placeholder="Enter comma-separated numbers"
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <p className="text-gray-500 text-sm mt-1">
              Example: 64, 34, 25, 12, 22, 11, 90
            </p>
          </div>

          <button
            onClick={handleSort}
            className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition duration-300"
          >
            Sort Values
          </button>

          {error && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative">
              {error}
            </div>
          )}
        </div>

        {/* Sorting Results */}
        <div>
          <h3 className="text-lg font-semibold mb-4 text-gray-700">Sorting Results</h3>
          {sortedResult ? (
            <div className="bg-gray-100 p-4 rounded-md overflow-auto max-h-64">
              <pre className="text-sm">
                {JSON.stringify(sortedResult, null, 2)}
              </pre>
            </div>
          ) : (
            <div className="bg-gray-100 p-4 rounded-md text-center text-gray-500">
              Sort results will appear here
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

// Products Management Component
const ProductsManagement = () => {
  const [products, setProducts] = useState([]);
  const [newProduct, setNewProduct] = useState({
    name: '',
    quantity: '',
    productId: ''
  });
  const [error, setError] = useState('');

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await axiosInstance.get(`${API_BASE_URL}/products/get-all`);
      console.log(response.data);
      
      setProducts(await response.data._embedded.productList);
      console.log(products);
      
    } catch (err) {
      setError('Failed to fetch products');
    }
  };

  const handleCreateProduct = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.post(`${API_BASE_URL}/products/create-product`, {
        ...newProduct,
        quantity: parseInt(newProduct.quantity)
      });
      fetchProducts();
      // Reset form
      setNewProduct({ name: '', quantity: '', productId: '' });
      setError('');
    } catch (err) {
      setError('Failed to create product');
    }
  };

  return (
    <div className="container mx-auto p-6 bg-white shadow-md rounded-lg">
      <h2 className="text-2xl font-bold mb-6 text-center text-green-600">
        Product Management
      </h2>

      <div className="grid md:grid-cols-2 gap-6">
        {/* Product Creation Form */}
        <div>
          <h3 className="text-lg font-semibold mb-4 text-gray-700">Add New Product</h3>
          <form onSubmit={handleCreateProduct} className="space-y-4">
            <div>
              <label className="block text-gray-700 mb-2 font-semibold">Product Name</label>
              <input
                type="text"
                value={newProduct.name}
                onChange={(e) => setNewProduct({...newProduct, name: e.target.value})}
                required
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
              />
            </div>

            <div>
              <label className="block text-gray-700 mb-2 font-semibold">Quantity</label>
              <input
                type="number"
                value={newProduct.quantity}
                onChange={(e) => setNewProduct({...newProduct, quantity: e.target.value})}
                required
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
              />
            </div>

            <div>
              <label className="block text-gray-700 mb-2 font-semibold">Product ID</label>
              <input
                type="text"
                value={newProduct.productId}
                onChange={(e) => setNewProduct({...newProduct, productId: e.target.value})}
                required
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
              />
            </div>

            {error && (
              <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative">
                {error}
              </div>
            )}

            <button
              type="submit"
              className="w-full bg-green-600 text-white py-2 rounded-md hover:bg-green-700 transition duration-300"
            >
              Create Product
            </button>
          </form>
        </div>

        {/* Product List */}
        <div>
          <h3 className="text-lg font-semibold mb-4 text-gray-700">
            Product List
          </h3>
          <div className="overflow-x-auto max-h-96 overflow-y-auto">
            <table className="w-full border-collapse">
              <thead className="bg-gray-200 sticky top-0">
                <tr>
                  <th className="p-3 text-left">Name</th>
                  <th className="p-3 text-left">Quantity</th>
                  <th className="p-3 text-left">Product ID</th>
                </tr>
              </thead>
              <tbody>
                {products.map((product) => (
                  <tr key={product.productId} className="border-b hover:bg-gray-100">
                    <td className="p-3">{product.name}</td>
                    <td className="p-3">{product.quantity}</td>
                    <td className="p-3">{product.productId}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

// Main App Component
function App() {
  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="space-y-6">
        <SortingInterface />
        <ProductsManagement />
      </div>
    </div>
  );
}

export default App;