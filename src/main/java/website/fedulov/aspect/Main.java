package website.fedulov.aspect;

import org.springframework.transaction.annotation.Transactional;
import website.fedulov.routing.DbContextHolder;
import website.fedulov.testmodel.SomeData;
import website.fedulov.testrepo.SomeDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Autowired
    private SomeDataRepository repo;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring.xml");
        Main main = context.getBean(Main.class);
        main.testReplica();
        main.testMaster();

        main.testMasterUpdate();
        main.testReplica();  // not touched
        main.testMaster();   // updated

    }

    @Transactional
    @ReadOnlyConnection
    public void testReplica(){
        log.info(">>> DbContextHolder.getDbType {}", DbContextHolder.getDbType());
        log.info(">>> Replica: {}", repo.findAll());
    }

    @Transactional
    @WriteConnection
    public void testMaster(){
        log.info(">>> DbContextHolder.getDbType {}", DbContextHolder.getDbType());
        log.info(">>> Master: {}", repo.findAll());
    }

    @Transactional
    @WriteConnection
    public void testMasterUpdate(){
        log.info(">>> DbContextHolder.getDbType {}", DbContextHolder.getDbType());
        log.info(">>> Master: {}", repo.findAll());

        SomeData someData = new SomeData();
        someData.setId(2);
        someData.setName("name");
        someData.setValue("newData");
        repo.save(someData);
        log.info(">>> Master updated:{}", repo.findAll());
    }

}
