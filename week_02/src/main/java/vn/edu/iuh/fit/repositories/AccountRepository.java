package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.db.ConnectionDB;
import vn.edu.iuh.fit.entities.Login;
import vn.edu.iuh.fit.models.Account;
import vn.edu.iuh.fit.models.Order;

import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final EntityManagerFactory entityManagerFactory;

    public AccountRepository() {
        entityManagerFactory = ConnectionDB.getInstance().getEntityManagerFactory();
    }

    public boolean createAccount(Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(account);
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

    public boolean updateAccount(Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(account);
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

    public boolean deleteAccount(long accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Account account = entityManager.find(Account.class, accountId);
            entityManager.remove(account);
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

    public Account getAccountById(long accountId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Account account = null;

        try {
            account = entityManager.find(Account.class, accountId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return account;
    }

    public List<Account> getAllAccounts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Account> accounts = null;

        try {
            Query query = entityManager.createQuery("SELECT a FROM Account a");
            accounts = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return accounts;
    }

    public long getAccountId() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        long accountId = 0;
        try {
            Query query = entityManager.createNativeQuery("SELECT account_id FROM accounts ORDER BY account_id DESC LIMIT 1");
            Object result = query.getSingleResult();
            if (result != null) {
                accountId = ((Number) result).longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return accountId;
    }

    public boolean checkAccountExist(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM accounts WHERE email = ?", Account.class);
            query.setParameter(1, email);
            Account account = (Account) query.getSingleResult();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    public Account login(Login login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Account account = null;
        try {
            Query query = entityManager.createNativeQuery("SELECT account_id, email, `password`, role FROM accounts WHERE email = ? AND `password` = ?");
            query.setParameter(1, login.getEmail());
            query.setParameter(2, login.getPassword());
            Object[] object = (Object[]) query.getSingleResult();
            account = new Account((Long) object[0], object[1]+"", object[2]+"", object[3]+"");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return account;
    }

    public List<Account> getAccountsByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Account> accounts = null;
        try {
            if(email.equalsIgnoreCase("all") == true){
                accounts = getAllAccounts();
            }
            else{
                Query query = entityManager.createQuery("SELECT a FROM Account a WHERE a.email LIKE :email", Account.class);
                query.setParameter("email", "%" + email + "%");
                accounts =  query.getResultList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return accounts;
    }

    public long getQuantityAccount() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        long quantity = 0;
        try {
            Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM accounts");
            Object result = query.getSingleResult();
            if (result != null) {
                quantity = ((Number) result).longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return quantity;
    }
}