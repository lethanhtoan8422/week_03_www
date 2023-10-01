package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.db.ConnectionDB;
import vn.edu.iuh.fit.models.Account;
import vn.edu.iuh.fit.models.Customer;
import java.util.List;

public class CustomerRepository {
    private final EntityManagerFactory entityManagerFactory;

    public CustomerRepository() {
        entityManagerFactory = ConnectionDB.getInstance().getEntityManagerFactory();
    }

    public boolean createCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(customer);
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

    public boolean updateCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(customer);
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

    public boolean deleteCustomer(long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            entityManager.remove(customer);
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

    public Customer getCustomerById(long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer customer = null;

        try {
            customer = entityManager.find(Customer.class, customerId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return customer;
    }

    public List<Customer> getAllCustomers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Customer> customers = null;

        try {
            Query query = entityManager.createQuery("SELECT c FROM Customer c");
            customers = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return customers;
    }

    public Customer getCustomerByCust(Customer cust) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Customer customer = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM customer WHERE cust_name = ? AND email = ? AND phone = ? AND address = ?", Customer.class);
            query.setParameter(1, cust.getCustName());
            query.setParameter(2, cust.getEmail());
            query.setParameter(3, cust.getPhone());
            query.setParameter(4, cust.getAddress());

            customer = (Customer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return customer;
    }

    public long getCustomerId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        long custId = 0;
        try {
            Query query = entityManager.createNativeQuery("SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1");
            Object result = query.getSingleResult();
            if (result != null) {
                custId = ((Number) result).longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return custId;
    }

    public Customer getCustomerByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer customer = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM customer WHERE email = ?", Customer.class);
            query.setParameter(1, email);
            customer = (Customer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return customer;
    }

    public List<Object[]> getProductByCustId(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Object[]> objects = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT p.name, prm.path, od.price, od.quantity, o.order_date FROM customer AS c INNER JOIN orders AS o ON c.cust_id = o.cust_id INNER JOIN order_detail AS od ON o.order_id = od.order_id INNER JOIN product AS p ON od.product_id = p.product_id INNER JOIN product_image AS prm ON p.product_id = prm.product_id WHERE c.cust_id = ?");
            query.setParameter(1, id);
            objects = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return objects;
    }

    public List<Customer> getCustomersHaveNotAccount() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Customer> customers = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM customer WHERE cust_id NOT IN (SELECT cust_id FROM accounts)", Customer.class);
            customers = (List<Customer>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return customers;
    }
}