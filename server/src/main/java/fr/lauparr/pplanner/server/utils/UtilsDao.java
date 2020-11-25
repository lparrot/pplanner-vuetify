package fr.lauparr.pplanner.server.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class UtilsDao {

  private static final String SPLIT_CHAR = "\\.";

  /**
   * Constructeur
   */
  private UtilsDao() {
  }

  public static <T> T convertToDto(Object data, Class<T> clazz) {
    return UtilsSpring.getBean(ProjectionFactory.class).createProjection(clazz, data);
  }

  public static <T> List<T> convertListDto(List<?> liste, Class<T> clazz) {
    return liste.stream().map(x -> UtilsDao.convertToDto(x, clazz)).collect(Collectors.toList());
  }

  public static <T> T findRandom(PagingAndSortingRepository<T, ?> repository) {
    long count = repository.count();
    int idx = new SecureRandom().nextInt((int) count);
    List<T> result = repository.findAll(PageRequest.of(idx, 1)).getContent();
    if (!result.isEmpty()) {
      return result.get(0);
    }
    return null;
  }

  /**
   * Récupère le Path à partir d'un Root
   *
   * @return Path
   */
  public static <T> Path<T> getPathFromRoot(Root<T> root, String field) {
    String principal;
    String[] fields = null;
    if (field != null) {
      String[] checks = field.split(UtilsDao.SPLIT_CHAR);
      principal = checks[0];
      if (checks.length > 1) {
        fields = Arrays.copyOfRange(checks, 1, checks.length);
      }
    } else {
      principal = null;
    }
    Path<T> p;
    if (UtilsDao.isSubPath(fields)) {
      p = UtilsDao.crossPathToPath(root.join(principal, JoinType.LEFT), fields);
    } else {
      p = root.get(principal);
    }
    return p;
  }

  /**
   * Vérifie qu'il ne s'agit pas du dernier Path "extractible"
   *
   * @return oui/non
   */
  private static boolean isSubPath(String[] fields) {
    return fields != null && fields.length > 0;
  }

  /**
   * Récupère un Path à partir du Path et de la liste de champs filtrés
   * (DataLazyModel)
   *
   * @return Path
   */
  private static <T> Path<T> crossPathToPath(Path<T> path, String[] fields) {
    Path<T> lastPath = path;
    if (fields != null) {
      for (String key : fields) {
        lastPath = lastPath.get(key);
      }
    }
    return lastPath;
  }

}
