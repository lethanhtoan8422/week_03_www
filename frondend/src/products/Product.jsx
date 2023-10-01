import "../products/product.scss";

const Product = ({ key, product, chooseProduct }) => {
  return (
    <div className="container-product" key={key}>
      <div className="image-product">
        <img src={`/${product.path}`} alt="" className="image" />
      </div>
      <span className="name-product">{product.name}</span>
      <span className="price-product">
        {new Intl.NumberFormat("vi-VN", {
          style: "currency",
          currency: "VND",
        }).format(product.price)}
      </span>
      <button className="btn-insert-into-cart" onClick={() => chooseProduct(product)}>Thêm Vào Giỏ Hàng</button>
    </div>
  );
};

export default Product;
