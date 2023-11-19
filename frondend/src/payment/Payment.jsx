import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../payment/payment.scss";
import axios from "axios";
import { add, format } from "date-fns";

const Payment = () => {
  let location = useLocation();
  let navigate = useNavigate();
  const [productsChosen, setProductsChosen] = useState([]);
  const [employees, setemployees] = useState([]);
  const [customer, setCustomer] = useState()
  const [payments, setPayments] = useState([
    {
      id: 1,
      type: "Thanh Toán Khi Nhận Hàng",
    },
    {
      id: 2,
      type: "Thanh Toán Bằng MoMo",
    },
  ]);
  const [typePaymentChosen, setTypePaymentChosen] = useState();
  const [employeeChosen, setemployeeChosen] = useState(1);
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");

  useEffect(() => {
    setProductsChosen(location.state.productsChosen);
    setCustomer({
      custId : location.state.customer.custId,
      custName : location.state.customer.custName,
      email : location.state.customer.email,
      phone : location.state.customer.phone,
      address : location.state.customer.address
    });
    setName(location.state.customer.custName)
    setEmail(location.state.customer.email)
    setPhone(location.state.customer.phone)
    setAddress(location.state.customer.address)
    console.log(location.state);
    let getApiEmployees = async () => {
      let datas = await axios.get("http://localhost:8080/api/employees");
      setemployees(datas.data);
    };
    getApiEmployees();
  }, [JSON.stringify(location.state)]);

  let handleChangeTypePayment = (payment) => {
    setTypePaymentChosen(payment);
  };

  let handleClickFinishPayment = async () => {
    if (!typePaymentChosen) {
      alert("Vui Lòng Chọn Hình Thức Thanh Toán");
    } else {
      let dataOrder = await axios.get("http://localhost:8080/api/orders/getID");
      await axios.post("http://localhost:8080/api/orders", {
        orderId: dataOrder.data + 1,
        orderDate: format(new Date(), "yyyy-MM-dd"),
        employee: employees.find((e) => (e.empId = employeeChosen)),
        customer: customer,
      });
      let datasGetOrder = await axios.get(
        `http://localhost:8080/api/orders/${dataOrder.data + 1}`
      );
        let isPaymentSuccess = true
      for (let i = 0; i < productsChosen.length; i++) {
        let dataOrderDetail = await axios.get(
          "http://localhost:8080/api/orderdetails/getID"
        );
        let dataPr = await axios.get(
          `http://localhost:8080/api/products/${productsChosen[i].product_id}`
        );
        let datas = await axios.post("http://localhost:8080/api/orderdetails", {
          orderDetailId: dataOrderDetail.data + 1,
          note: "note",
          price: productsChosen[i].price,
          quantity: productsChosen[i].quantity,
          order: datasGetOrder.data,
          product: dataPr.data,
        });
        isPaymentSuccess = datas.data === false ? false : true
      }
      if(isPaymentSuccess){
        alert("Mua Thành Công!!!")
        navigate("/home", {state : {customer : location.state.customer}});
      }
    }
  };

  return (
    <div className="container-payment">
      <button className="btn-back" onClick={() => navigate(-1)}>
        <i className="fa-solid fa-arrow-left icon-back"></i>
      </button>
      <h2 className="text-info-cust">Nhập Thông Tin Khách Hàng</h2>
      <div className="form-customer">
        <div className="content-form">
          <label className="lblForm" htmlFor="name">
            Tên Khách Hàng :{" "}
          </label>
          <input
          value={name}
            className="inputForm"
            type="text"
            name=""
            id="name"
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <div className="content-form">
          <label className="lblForm" htmlFor="address">
            Địa Chỉ :{" "}
          </label>
          <input
          value={address}
            className="inputForm"
            type="text"
            name=""
            id="address"
            onChange={(e) => setAddress(e.target.value)}
          />
        </div>
        <div className="content-form">
          <label className="lblForm" htmlFor="email">
            Email :{" "}
          </label>
          <input
          value={email}
            className="inputForm"
            type="email"
            name=""
            id="email"
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div className="content-form">
          <label className="lblForm" htmlFor="phone">
            Số Điện Thoại :{" "}
          </label>
          <input
          value={phone}
            className="inputForm"
            type="tel"
            name=""
            id="phone"
            onChange={(e) => setPhone(e.target.value)}
          />
        </div>
        <div className="content-form">
          <label className="lblForm" htmlFor="employee">
            Nhân Viên :{" "}
          </label>
          <select
            className="inputForm"
            name=""
            id="employee"
            onChange={(e) => setemployeeChosen(e.target.value)}
          >
            {employees.map((e) => (
              <option key={e.empId} value={e.empId}>
                {e.fullName}
              </option>
            ))}
          </select>
        </div>

        <div className="product-payment">
          {productsChosen.map((pro) => (
            <div className="content-cart" key={pro.product_id}>
              <img
                src={`/${pro.path}`}
                alt=""
                className="image-product-chosen"
              />
              <div className="infor">
                <span className="name-product-chosen">{pro.name}</span>
                <span className="price-product-chosen">
                  {new Intl.NumberFormat("vi-VN", {
                    style: "currency",
                    currency: "VND",
                  }).format(pro.price)}
                </span>
                <input
                  value={pro.quantity}
                  type="number"
                  name=""
                  id=""
                  className="quantity-product-chosen"
                  min={0}
                  disabled
                />
              </div>
            </div>
          ))}
        </div>
        <span className="txt-type-payment">Phương Thức Thanh Toán : </span>
        <div className="type-payments">
          {payments.map((payment) => (
            <div key={payment.id} className="type">
              <input
                type="radio"
                name="type"
                id={payment.id}
                className="input-type"
                onChange={() => handleChangeTypePayment(payment)}
              />
              <label htmlFor={payment.id} className="lbl-type">
                {payment.type}
              </label>
            </div>
          ))}
        </div>
        <div className="finish-payment">
          <button
            className="btn-finish-payment"
            onClick={handleClickFinishPayment}
          >
            Thanh Toán
          </button>
        </div>
      </div>
    </div>
  );
};

export default Payment;
