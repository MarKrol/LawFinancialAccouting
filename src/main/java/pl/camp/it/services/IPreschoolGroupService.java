package pl.camp.it.services;

import org.springframework.stereotype.Service;
import pl.camp.it.model.preschoolGroup.PreschoolGroup;

@Service
public interface IPreschoolGroupService {
    void persistPreschoolGroup(PreschoolGroup preschoolGroup);
}
