const ProductsManagement = () => {
    const [products, setProducts] = useState([]);
    const [newProduct, setNewProduct] = useState({
      name: '',
      quantity: '',
      productId: ''
    });
    const [editingProduct, setEditingProduct] = useState(null); // Track the product being edited
    const [error, setError] = useState('');
  
    useEffect(() => {
      fetchProducts();
    }, []);
  
    const fetchProducts = async () => {
      try {
        const response = await axiosInstance.get(`${API_BASE_URL}/products/get-all`);
        setProducts(response.data._embedded.productList);
        setError('');
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
        setNewProduct({ name: '', quantity: '', productId: '' });
        setError('');
      } catch (err) {
        setError('Failed to create product');
      }
    };
  
    const handleEditProduct = (product) => {
      setEditingProduct(product); // Set the product to edit
    };
  
    const handleUpdateProduct = async () => {
      try {
        await axiosInstance.put(`${API_BASE_URL}/products/update-product/${editingProduct.productId}`, editingProduct);
        fetchProducts();
        setEditingProduct(null); // Clear the editing state
        setError('');
      } catch (err) {
        setError('Failed to update product');
      }
    };
  
    const handleDeleteProduct = async (productId) => {
      try {
        await axiosInstance.delete(`${API_BASE_URL}/products/delete-product/${productId}`);
        fetchProducts();
        setError('');
      } catch (err) {
        setError('Failed to delete product');
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
            <h3 className="text-lg font-semibold mb-4 text-gray-700">{editingProduct ? 'Edit Product' : 'Add New Product'}</h3>
            <form
              onSubmit={editingProduct ? (e) => { e.preventDefault(); handleUpdateProduct(); } : handleCreateProduct}
              className="space-y-4"
            >
              <div>
                <label className="block text-gray-700 mb-2 font-semibold">Product Name</label>
                <input
                  type="text"
                  value={editingProduct ? editingProduct.name : newProduct.name}
                  onChange={(e) =>
                    editingProduct
                      ? setEditingProduct({ ...editingProduct, name: e.target.value })
                      : setNewProduct({ ...newProduct, name: e.target.value })
                  }
                  required
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                />
              </div>
  
              <div>
                <label className="block text-gray-700 mb-2 font-semibold">Quantity</label>
                <input
                  type="number"
                  value={editingProduct ? editingProduct.quantity : newProduct.quantity}
                  onChange={(e) =>
                    editingProduct
                      ? setEditingProduct({ ...editingProduct, quantity: e.target.value })
                      : setNewProduct({ ...newProduct, quantity: e.target.value })
                  }
                  required
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
                />
              </div>
  
              <div>
                <label className="block text-gray-700 mb-2 font-semibold">Product ID</label>
                <input
                  type="text"
                  value={editingProduct ? editingProduct.productId : newProduct.productId}
                  onChange={(e) =>
                    editingProduct
                      ? setEditingProduct({ ...editingProduct, productId: e.target.value })
                      : setNewProduct({ ...newProduct, productId: e.target.value })
                  }
                  required
                  disabled={!!editingProduct} // Disable Product ID input when editing
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
                {editingProduct ? 'Update Product' : 'Create Product'}
              </button>
  
              {editingProduct && (
                <button
                  type="button"
                  onClick={() => setEditingProduct(null)} // Cancel edit
                  className="w-full bg-gray-600 text-white py-2 rounded-md hover:bg-gray-700 transition duration-300 mt-2"
                >
                  Cancel
                </button>
              )}
            </form>
          </div>
  
          {/* Product List */}
          <div>
            <h3 className="text-lg font-semibold mb-4 text-gray-700">Product List</h3>
            <div className="overflow-x-auto max-h-96 overflow-y-auto">
              <table className="w-full border-collapse">
                <thead className="bg-gray-200 sticky top-0">
                  <tr>
                    <th className="p-3 text-left">Name</th>
                    <th className="p-3 text-left">Quantity</th>
                    <th className="p-3 text-left">Product ID</th>
                    <th className="p-3 text-left">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {products.map((product) => (
                    <tr key={product.productId} className="border-b hover:bg-gray-100">
                      <td className="p-3">{product.name}</td>
                      <td className="p-3">{product.quantity}</td>
                      <td className="p-3">{product.productId}</td>
                      <td className="p-3">
                        <button
                          onClick={() => handleEditProduct(product)}
                          className="bg-blue-600 text-white px-3 py-1 rounded-md hover:bg-blue-700 transition duration-300 mr-2"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDeleteProduct(product.productId)}
                          className="bg-red-600 text-white px-3 py-1 rounded-md hover:bg-red-700 transition duration-300"
                        >
                          Delete
                        </button>
                      </td>
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
  