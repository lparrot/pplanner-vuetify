package fr.lauparr.pplanner.server.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public interface BeanUtils {

  public static void copyProperties(Object source, Object target, boolean skipNull) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setSkipNullEnabled(skipNull).setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.map(source, target);
  }

}
