package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.db.ConnectionDB;
import vn.edu.iuh.fit.entities.InformationProduct;
import vn.edu.iuh.fit.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final EntityManagerFactory entityManagerFactory;

    public ProductRepository() {
        entityManagerFactory = ConnectionDB.getInstance().getEntityManagerFactory();
    }

    public boolean createProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(product);
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

    public boolean updateProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(product);
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

    public boolean deleteProduct(long productId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Product product = entityManager.find(Product.class, productId);
            entityManager.remove(product);
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

    public Product getProductById(long productId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Product product = null;

        try {
            product = entityManager.find(Product.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return product;
    }

    public List<Product> getAllProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Product> products = null;

        try {
            Query query = entityManager.createQuery("SELECT p FROM Product p");
            products = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return products;
    }

    public List<InformationProduct> getInfoProduct() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryString = "SELECT p.product_id, p.name, pm.path, pr.price FROM product AS p \n" +
                    "INNER JOIN product_image AS pm ON p.product_id = pm.product_id \n" +
                    "INNER JOIN (\n" +
                    "\tSELECT price_id, note, MAX(price) AS price, MAX(price_date_time) AS price_date_time, product_id FROM product_price GROUP BY product_id\n" +
                    ")AS pr ON p.product_id = pr.product_id\n" +
                    "GROUP BY p.name, YEAR(o.order_date), MONTH(o.order_date)";

            Query query = entityManager.createNativeQuery(queryString);
            List<Object[]> results = query.getResultList();

            List<InformationProduct> informationProducts = new ArrayList<>();
            for (Object[] result : results) {
                long productId = (long) result[0];
                String productName = (String) result[1];
                String imagePath = (String) result[2];
                double price = (double) result[3];

                InformationProduct informationProduct = new InformationProduct(productId, productName, imagePath, price);
                informationProducts.add(informationProduct);
            }

            return informationProducts;
        } finally {
            entityManager.close();
        }
    }

    public List<Object[]> getAllNameOfProductOrdered() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String queryString = "SELECT pr.name FROM order_detail AS od INNER JOIN product AS pr ON od.product_id = pr.product_id GROUP BY pr.name ORDER BY pr.name";

            Query query = entityManager.createNativeQuery(queryString);
            List<Object[]> results = query.getResultList();

            return results;
        } finally {
            entityManager.close();
        }
    }
}