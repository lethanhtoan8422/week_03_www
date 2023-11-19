package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.db.ConnectionDB;
import vn.edu.iuh.fit.entities.RequestOrderDate;
import vn.edu.iuh.fit.entities.ResponseOrderByDateBetween;
import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.models.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderRepository {
    private final EntityManagerFactory entityManagerFactory;

    public OrderRepository() {
        entityManagerFactory = ConnectionDB.getInstance().getEntityManagerFactory();
    }

    public boolean createOrder(Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(order);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    public boolean updateOrder(Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(order);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    public boolean deleteOrder(long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Order order = entityManager.find(Order.class, orderId);
            entityManager.remove(order);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    public Order getOrderById(long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Order order = null;

        try {
            order = entityManager.find(Order.class, orderId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return order;
    }

    public List<Order> getAllOrders() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Order> orders = null;

        try {
            Query query = entityManager.createQuery("SELECT o FROM Order o");
            orders = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return orders;
    }

    public Order getOrderByOrder(Order or) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Order order = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM orders WHERE order_date = ? AND cust_id = ? AND emp_id = ?", Order.class);
            query.setParameter(1, or.getOrderDate());
            query.setParameter(2, or.getCustomer().getCustId());
            query.setParameter(3, or.getEmployee().getEmpId());

            order = (Order) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return order;
    }

    public long getOrderId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        long orderId = 0;
        try {
            Query query = entityManager.createNativeQuery("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
            Object result = query.getSingleResult();
            if (result != null) {
                orderId = ((Number) result).longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return orderId;
    }

    public List<ResponseOrderByDateBetween> getOrderByDateBetWeen(RequestOrderDate requestOrderDate){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ResponseOrderByDateBetween> responseOrderByDateBetweens = new ArrayList<>();
        List<Object[]> objects = null;
        try {
            if(requestOrderDate.getEmpID() == 0){
                Query query = entityManager.createNativeQuery("SELECT od.order_detail_id, p.name, o.order_date, SUM(od.price) AS prices, SUM(od.quantity) AS quantity FROM orders AS o \n" +
                        "INNER JOIN order_detail AS od ON o.order_id = od.order_id\n" +
                        "INNER JOIN product AS p ON p.product_id = od.product_id\n" +
                        "WHERE order_date BETWEEN ? AND ?\n" +
                        "GROUP BY p.name, o.order_date ORDER BY o.order_date");
                query.setParameter(1, requestOrderDate.getFromDate());
                query.setParameter(2, requestOrderDate.getToDate());
                objects = query.getResultList();
                for (Object[] o : objects){
                    ResponseOrderByDateBetween responseOrderByDateBetween = new ResponseOrderByDateBetween((long)o[0],o[1]+"",o[2]+"",(double)o[3],(BigDecimal) o[4]);
                    responseOrderByDateBetweens.add(responseOrderByDateBetween);
                }
            }
            else{
                Query query = entityManager.createNativeQuery("SELECT od.order_detail_id, p.name, o.order_date, SUM(od.price) AS prices, SUM(od.quantity) AS quantity, e.emp_id FROM orders AS o \n" +
                        "INNER JOIN order_detail AS od ON o.order_id = od.order_id\n" +
                        "INNER JOIN product AS p ON p.product_id = od.product_id\n" +
                        "INNER JOIN employee AS e ON o.emp_id = e.emp_id\n" +
                        "WHERE e.emp_id = ? AND order_date BETWEEN ? AND ?\n" +
                        "GROUP BY p.name, o.order_date\n" +
                        "ORDER BY o.order_date");
                query.setParameter(1, requestOrderDate.getEmpID());
                query.setParameter(2, requestOrderDate.getFromDate());
                query.setParameter(3, requestOrderDate.getToDate());
                objects = query.getResultList();

                for (Object[] o : objects){
                    ResponseOrderByDateBetween responseOrderByDateBetween = new ResponseOrderByDateBetween((long)o[0],o[1]+"",o[2]+"",(double)o[3],(BigDecimal) o[4]);
                    responseOrderByDateBetweens.add(responseOrderByDateBetween);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return responseOrderByDateBetweens;
    }
}