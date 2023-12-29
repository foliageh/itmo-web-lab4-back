package web.twillice.rest.http.resources;

import org.mapstruct.Mapper;
import web.twillice.rest.model.Shot;

import java.util.List;

@Mapper // (uses = UserMapper.class)
public interface ShotMapper {
    ShotResource toResource(Shot shot);

    List<ShotResource> toResourceList(List<Shot> shots);
}
