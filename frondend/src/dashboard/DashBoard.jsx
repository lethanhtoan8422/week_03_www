import { useState } from 'react'
import "../dashboard/dashboard.scss"
import { Link, Outlet } from 'react-router-dom'

const DashBoard = () => {
    const [navBar, setNavBar] = useState([
        {
            id : "",
            nav : "Home"
        },
        {
            id : "account",
            nav : "Account"
        },
        {
            id : "customer",
            nav : "Customer"
        },
        {
            id : "employee",
            nav : "Employee"
        },
        {
            id : "order",
            nav : "Order"
        },
    ])
  return (
    <div className='dashboard'>
        <div className='nav-bar-dashboard'>
            <div className='image-admin'>
                <img src="/avarta-admin.png" alt="" />
            </div>
            <div className='nav-bar'>
                {
                    navBar.map(nav => (
                        <Link to={nav.id} key={nav.id} className='nav'>{nav.nav}</Link>
                    ))
                }
            </div>
        </div>
        <div className='container-dashboard'>
            <Outlet/>
        </div>
    </div>
  )
}

export default DashBoard