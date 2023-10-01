package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.db.ConnectionDB;
import vn.edu.iuh.fit.models.ProductPrice;
import java.util.List;

public class ProductPriceRepository {
    private final EntityManagerFactory entityManagerFactory;

    public ProductPriceRepository() {
        entityManagerFactory = ConnectionDB.getInstance().getEntityManagerFactory();
    }

    public boolean createProductPrice(ProductPrice productPrice) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(productPrice);
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

    public boolean updateProductPrice(ProductPrice productPrice) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(productPrice);
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

    public boolean deleteProductPrice(long productPriceId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            ProductPrice productPrice = entityManager.find(ProductPrice.class, productPriceId);
            entityManager.remove(productPrice);
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

    public ProductPrice getProductPriceById(long productPriceId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ProductPrice productPrice = null;

        try {
            productPrice = entityManager.find(ProductPrice.class, productPriceId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return productPrice;
    }

    public List<ProductPrice> getAllProductPrices() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ProductPrice> productPrices = null;

        try {
            Query query = entityManager.createQuery("SELECT pp FROM ProductPrice pp");
            productPrices = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return productPrices;
    }
}