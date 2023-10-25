package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.db.ConnectionDB;
import vn.edu.iuh.fit.models.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepository {
    private final EntityManagerFactory entityManagerFactory;

    public OrderDetailRepository() {
        entityManagerFactory = ConnectionDB.getInstance().getEntityManagerFactory();
    }

    public boolean createOrderDetail(OrderDetail orderDetail) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(orderDetail);
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

    public boolean updateOrderDetail(OrderDetail orderDetail) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(orderDetail);
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

    public boolean deleteOrderDetail(long orderDetailId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            OrderDetail orderDetail = entityManager.find(OrderDetail.class, orderDetailId);
            entityManager.remove(orderDetail);
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

    public OrderDetail getOrderDetailById(long orderDetailId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrderDetail orderDetail = null;

        try {
            orderDetail = entityManager.find(OrderDetail.class, orderDetailId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return orderDetail;
    }

    public List<OrderDetail> getAllOrderDetails() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<OrderDetail> orderDetails = null;

        try {
            Query query = entityManager.createQuery("SELECT od FROM OrderDetail od");
            orderDetails = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return orderDetails;
    }

    public long getOrderDetailId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        long orderDetailId = 0;
        try {
            Query query = entityManager.createNativeQuery("SELECT order_detail_id FROM order_detail ORDER BY order_detail_id DESC LIMIT 1");
            Object result = query.getSingleResult();
            if (result != null) {
                orderDetailId = ((Number) result).longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return orderDetailId;
    }

    public List<Object[]> statisticOrderDetailOfMonthAndYear() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryString = "SELECT od.order_detail_id, pr.name, SUM(od.price) AS price, SUM(od.quantity) AS quantity, o.order_date\n" +
                    "FROM order_detail AS od \n" +
                    "INNER JOIN orders AS o ON o.order_id = od.order_id\n" +
                    "INNER JOIN product AS pr ON pr.product_id = od.product_id\n" +
                    "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE()) \n" +
                    "GROUP BY pr.name, MONTH(o.order_date), YEAR(o.order_date)\n" +
                    "ORDER BY pr.name, YEAR(o.order_date), MONTH(o.order_date)";

            Query query = entityManager.createNativeQuery(queryString);
            List<Object[]> results = query.getResultList();
            return results;
        } finally {
            entityManager.close();
        }
    }

    public List<Object[]> statisticOrderDetailOfMonthAndYearByMonth(int month) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryString = "SELECT od.order_detail_id, pr.name, SUM(od.price) AS price, SUM(od.quantity) AS quantity, o.order_date\n" +
                    "FROM order_detail AS od \n" +
                    "INNER JOIN orders AS o ON o.order_id = od.order_id\n" +
                    "INNER JOIN product AS pr ON pr.product_id = od.product_id\n" +
                    "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE()) AND MONTH(o.order_date) = ?\n" +
                    "GROUP BY pr.name, MONTH(o.order_date), YEAR(o.order_date)\n" +
                    "ORDER BY pr.name, YEAR(o.order_date), MONTH(o.order_date)";

            Query query = entityManager.createNativeQuery(queryString);
            query.setParameter(1, month);
            List<Object[]> results = query.getResultList();
            return results;
        } finally {
            entityManager.close();
        }
    }

    public double getTotalPricesOfYear() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryString = "SELECT SUM(od.price) AS price\n" +
                    "FROM order_detail AS od \n" +
                    "INNER JOIN orders AS o ON o.order_id = od.order_id\n" +
                    "INNER JOIN product AS pr ON pr.product_id = od.product_id\n" +
                    "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE()) \n" +
                    "GROUP BY YEAR(o.order_date)";

            Query query = entityManager.createNativeQuery(queryString);
            Object results = query.getSingleResult();
            return (Double) results;
        } finally {
            entityManager.close();
        }
    }
}