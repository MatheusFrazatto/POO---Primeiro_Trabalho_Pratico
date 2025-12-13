package utilitario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe utilitária para gerenciar a EntityManagerFactory e fornecer acesso
 * ao EntityManager.
 * Utiliza o padrão Singleton para garantir que a EntityManagerFactory seja
 * criada apenas uma vez.
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "Trabalho_POO_PU";
    private static EntityManagerFactory factory;

    /**
     * Construtor privado para impedir a instanciação.
     */
    private JPAUtil() {
    }

    /**
     * Retorna uma instância do EntityManager.
     * A EntityManagerFactory é criada de forma lazy na primeira chamada.
     *
     * @return uma instância de EntityManager.
     */
    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory.createEntityManager();
    }

    /**
     * Fecha a EntityManagerFactory para liberar os recursos.
     * Deve ser chamado ao final da aplicação.
     */
    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
