package fr.lauparr.pplanner.server.repositories.customs;

import fr.lauparr.pplanner.server.entities.abstracts.ModifiableEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AppRepository<T extends ModifiableEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  default Optional<T> findById(ID id, Specification<T> spec) {
    return this.findAll(spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(root.getModel().getId(id.getClass())), id))).stream().findFirst();
  }
}
