package website.fedulov.testrepo;

import website.fedulov.testmodel.SomeData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SomeDataRepository extends JpaRepository<SomeData, Integer>{
    List<SomeData> findByName(String name);
}
