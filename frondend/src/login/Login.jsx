import { useState } from "react"
import "../login/login.scss"
import 'react-toastify/dist/ReactToastify.css';
import axios from "axios"
import { Link, useNavigate } from "react-router-dom"
import { ToastContainer, toast } from "react-toastify"

const Login = () => {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    let navigate = useNavigate()
    let handleClickLogin = async() => {
        let datas = await axios.post("http://localhost:8080/api/accounts/login", {
            email : email,
            password : password
        })
        if(datas.data.role === "user"){
            let dataGetCust = await axios.get(`http://localhost:8080/api/customers/CustomerByEmail/${email}`)
            navigate("/home", {state : {customer : {
                ...dataGetCust.data, password : password, accountId : datas.data.accountId
            }}})
        }
        else if(datas.data.role === "admin"){
            navigate("/dashboard")
        }
        else{
            toast.error('🦄 Email Hoặc Password Không Đúng!', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
        }
    }
  return (
    <div className="container-login">
        <span className="text-login">ĐĂNG NHẬP</span>
        <div className="form-login-content">
            <label htmlFor="login-email" className="login-label">Email : </label>
            <input type="email" value={email} id="login-email" className="login-input" 
            onChange={(e) => setEmail(e.target.value)}
            />
        </div>
        <div className="form-login-content">
            <label htmlFor="login-password" className="login-label">Password : </label>
            <input type="password" value={password} id="login-password" className="login-input" 
            onChange={(e) => setPassword(e.target.value)}
            />
        </div>
        <button className="btn-login" onClick={handleClickLogin}>Đăng Nhập</button>
        <Link style={{marginTop : '10px'}} to={"/sign-up"}>Đăng ký tài khoản</Link>
        <ToastContainer />
    </div>
  )
}

export default Login